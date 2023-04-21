package com.demo.push.dto;

import com.demo.push.common.ApiException;

/**
 * ClassName:BaseDTO
 * Package:com.demo.push.dto.req
 * Description:
 *
 * @Date:2023/4/4 17:25
 * @Author:qs@1.com
 */
public interface BaseDTO {

    /**
     * 参数校验, 当参数有问题时，直接抛出异常{@link ApiException}
     *
     * @throws ApiException
     */
    void check() throws ApiException;
}
