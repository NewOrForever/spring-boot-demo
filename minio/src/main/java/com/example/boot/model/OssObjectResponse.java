package com.example.boot.model;

import lombok.Data;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * ClassName:OssObjectResponse
 * Package:com.example.boot
 * Description:
 *
 * @Date:2024/4/16 11:13
 * @Author:qs@1.com
 */
@Data
public class OssObjectResponse extends OssResponse {
    private String etag;
    private long size;
    private long lastModified;
    private String contentType;
    private Map<String, List<String>> headers;
    private String bucket;
    private String region;
    private String object;

    private InputStream inputStream;
}
