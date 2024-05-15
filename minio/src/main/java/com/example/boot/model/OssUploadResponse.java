package com.example.boot.model;

import lombok.Data;

/**
 * ClassName:OssUploadResponse
 * Package:com.example.boot
 * Description:
 *
 * @Date:2024/4/16 11:12
 * @Author:qs@1.com
 */
@Data
public class OssUploadResponse extends OssResponse {
    private String url;
    private String objectName;
    private String accessPolicy;
    private String bucketName;
    private String fileName;
}
