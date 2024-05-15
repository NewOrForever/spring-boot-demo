package com.example.boot.service;

import com.example.boot.config.MinioProperties;
import com.example.boot.model.OssObjectResponse;
import com.example.boot.model.OssResponse;
import com.example.boot.model.OssUploadResponse;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ClassName:MinioTemplate
 * Package:com.example.boot
 * Description:
 *
 * @Date:2024/4/16 11:08
 * @Author:qs@1.com
 */
@Slf4j
@Component
public class MinioTemplate implements OssTemplate {
    /**
     * MinIO客户端
     */
    @Autowired
    private MinioClient minioClient;
    @Autowired
    private MinioProperties minioProperties;

    @Override
    public boolean bucketExists(String bucketName) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            log.error("minio bucketExists Exception: ", e);
        }
        return false;
    }

    public void makeBucket(String bucketName) {
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                log.info("minio makeBucket success bucketName:{}", bucketName);
            }
        } catch (Exception e) {
            log.error("minio makeBucket Exception: ", e);
        }
    }


    @Override
    public OssResponse getOssInfo(String bucketName, String objectName) {
        try {
            // 获取对象信息
            StatObjectResponse stat = minioClient.statObject(
                    StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
            // 获取文件流
            InputStream in = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build());

            Headers headers = stat.headers();
            // headers 转 map
            Map<String, List<String>> headersMap = headers.toMultimap();
            OssObjectResponse ossObjectResponse = new OssObjectResponse();
            ossObjectResponse.setEtag(stat.etag());
            ossObjectResponse.setSize(stat.size());
            ossObjectResponse.setLastModified(stat.lastModified().toInstant().toEpochMilli());
            ossObjectResponse.setContentType(stat.contentType());
            ossObjectResponse.setHeaders(headersMap);
            ossObjectResponse.setBucket(bucketName);
            ossObjectResponse.setRegion(stat.region());
            ossObjectResponse.setObject(objectName);
            ossObjectResponse.setInputStream(in);
            return ossObjectResponse;
        } catch (Exception e) {
            log.error("minio getOssInfo Exception:{}", e.getMessage());
        }
        return null;
    }


    @Override
    public OssResponse upLoadFile(String bucketName, String folderName, MultipartFile multipartFile) {
        // TODO 校验文件大小、文件类型、文件是否为空、bucket、最终文件名
        String fileName = multipartFile.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        // 对象名
        String objectName = folderName + "/" + fileName;
        try {
            //文件上传
            InputStream in = multipartFile.getInputStream();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(in, multipartFile.getSize(), -1)
                            .contentType(multipartFile.getContentType())
                            .build());
            in.close();
        } catch (Exception e) {
            log.error("minio upLoadFile Exception:{}", e);

        }
        OssUploadResponse response = new OssUploadResponse();
        // TODO 返回自己需要的数据
        response.setObjectName(objectName);
        response.setBucketName(bucketName);
        response.setFileName(fileName);
        response.setUrl(minioProperties.getEndpoint() + "/" + bucketName + "/" + objectName);
        return response;
    }


    @Override
    public boolean removeFile(String bucketName, String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
            log.info("minio removeFile success, fileName:{}", objectName);
            return true;
        } catch (Exception e) {
            log.error("minio removeFile fail, fileName:{}, Exception:{}", objectName, e.getMessage());
        }
        return false;
    }


    @Override
    public boolean removeFiles(String bucketName, List<String> objectNames) {
        try {
            List<DeleteObject> list = objectNames.stream().map(DeleteObject::new).collect(Collectors.toList());
            /**
             * lazy -  不是立即删除，而是异步删除
             */
            Iterable<Result<DeleteError>> results = minioClient.removeObjects(RemoveObjectsArgs.builder().bucket(bucketName)
                    .objects(list).build());
            log.info("minio removeFiles success, fileNames:{}", objectNames);
            return true;
        } catch (Exception e) {
            log.error("minio removeFiles fail, fileNames:{}, Exception:{}", objectNames, e.getMessage());
        }
        return false;
    }

    @Override
    public void downloadFile(HttpServletResponse response, String bucketName, String objectName) {
        InputStream in = null;
        try {
            OssObjectResponse downloadObjectResponse = (OssObjectResponse) getOssInfo(bucketName, objectName);
            Assert.notNull(downloadObjectResponse, "文件下载获取文件流失败");

            in = downloadObjectResponse.getInputStream();
            response.setContentType(downloadObjectResponse.getContentType());
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    URLEncoder.encode(objectName, "UTF-8"));
            IOUtils.copy(in, response.getOutputStream());
        } catch (Exception e) {
            log.error("文件下载失败" + e.getMessage(), e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("文件下载文件流关闭失败" + e.getMessage());
                }
            }
        }
    }

    @Override
    public String getPresignedObjectUrl(String bucketName, String objectName, int expires) {
        expires = expires == -1 ? 2 * 60 * 60 : expires;
        try {
            GetPresignedObjectUrlArgs objectArgs = GetPresignedObjectUrlArgs.builder().object(objectName)
                    .bucket(bucketName)
                    .expiry(expires).method(Method.GET).build();
            String url = minioClient.getPresignedObjectUrl(objectArgs);
            return URLDecoder.decode(url, "UTF-8");
        } catch (Exception e) {
            log.info("文件路径获取失败" + e.getMessage());
        }
        return null;
    }
}
