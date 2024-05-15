package com.example.boot.controller;

import com.example.boot.service.OssTemplate;
import com.example.boot.model.OssObjectResponse;
import com.example.boot.model.OssUploadResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpHeaders.ETAG;
import static org.springframework.http.HttpHeaders.LAST_MODIFIED;

/**
 * ClassName:OssController
 * Package:com.example.boot.controller
 * Description:
 *
 * @Date:2024/4/16 11:22
 * @Author:qs@1.com
 */
@Api(tags = "文件oss服务")
@RestController
@RequestMapping("/oss")
@Slf4j
public class OssController {
    @Autowired
    private OssTemplate ossTemplate;

    @PostMapping("/upload")
    @ApiOperation("上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "MultipartFile"),
            @ApiImplicitParam(name = "bizDir", value = "业务目录", required = false, dataType = "String"),
            @ApiImplicitParam(name = "bucketName", value = "bucket名称", required = true, dataType = "String"),
    })
    public R<OssUploadResponse> upload(MultipartFile file,
                                       @RequestParam(required = false) String bizDir,
                                       @RequestParam(required = true) String bucketName) {
        OssUploadResponse uploadResponse = (OssUploadResponse) ossTemplate.upLoadFile(bucketName, bizDir, file);
        return R.ok(uploadResponse);
    }

    @GetMapping("/download")
    @ApiOperation("下载文件")
    public void download(HttpServletResponse response, String bucketName, String objectName) {
        ossTemplate.downloadFile(response, bucketName, objectName);
    }

    @GetMapping("/view/{bucketName}/**")
    @ApiOperation("预览 - 主要用于桶，需要带token")
    public void view(HttpServletResponse response, HttpServletRequest request, @PathVariable String bucketName) {
        InputStream in = null;
        String objectName = RequestUtils.extractPathFromPattern(request);
        try {
            OssObjectResponse objectResponse =
                    (OssObjectResponse) ossTemplate.getOssInfo(bucketName, objectName);
            Assert.notNull(objectResponse, "获取文件流失败");

            in = objectResponse.getInputStream();
            response.setContentType(objectResponse.getContentType());
            // cache - 图片浏览器缓存
            response.setDateHeader(LAST_MODIFIED, objectResponse.getLastModified());
            response.setHeader(ETAG, objectResponse.getEtag());

            IOUtils.copy(in, response.getOutputStream());
        } catch (Exception e) {
            log.error("文件流获取失败" + e.getMessage(), e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("文件流关闭失败" + e.getMessage());
                }
            }
        }
    }

    @DeleteMapping("/removeObject")
    @ApiOperation("删除文件")
    public R removeObject(String bucketName, String objectName) {
        ossTemplate.removeFile(bucketName, objectName);
        return R.ok();
    }

    @DeleteMapping("/removeBatch")
    @ApiOperation("批量删除文件")
    public R removeBatch(String bucketName, String[] objectNames) {
        List<String> objectNameList = Arrays.asList(objectNames);
        ossTemplate.removeFiles(bucketName, objectNameList);
        return R.ok();
    }

    @GetMapping("/getPresignedObjectUrl")
    @ApiOperation("获取文件外链")
    public R<String> getPresignedObjectUrl(String bucketName, String objectName) {
        String url = ossTemplate.getPresignedObjectUrl(bucketName, objectName, -1);
        return R.ok(url);
    }
}
