package com.example.boot.service;

import com.example.boot.model.OssResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ClassName:OssTemplate
 * Package:com.example.boot
 * Description:
 *
 * @Date:2024/4/16 11:04
 * @Author:qs@1.com
 */
public interface OssTemplate {

    /**
     * 存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @return boolean
     */
    boolean bucketExists(String bucketName);


    /**
     * 获取文件信息
     *
     * @param objectName 存储桶对象名称
     * @param bucketName 存储桶名称
     * @return OssResponse
     */
    OssResponse getOssInfo(String bucketName, String objectName);

    /**
     * 上传文件
     *
     * @param folderName 上传的文件夹名称
     * @param file       上传的文件
     * @return OssResponse
     */
    OssResponse upLoadFile(String bucketName, String folderName, MultipartFile file);


    /**
     * 删除文件
     *
     * @param objectName 存储桶对象名称
     * @param bucketName 存储桶名称
     */
    boolean removeFile(String bucketName, String objectName);

    /**
     * 批量删除文件
     *
     * @param objectNames 存储桶对象名称集合
     */
    boolean removeFiles(String bucketName, List<String> objectNames);

    /**
     * @Description: 下载文件
     * @Param response: 响应
     * @Param fileName: 文件名
     * @Param filePath: 文件路径
     * @return: void
     */
    void downloadFile(HttpServletResponse response, String bucketName, String objectName);

    /**
     * 获取文件外链 - 默认过期时间为2小时
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶对象名称
     * @param expires    过期时间，-1 则时默认2小时
     * @return String
     */
    String getPresignedObjectUrl(String bucketName, String objectName, int expires);
}