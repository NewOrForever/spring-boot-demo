package com.demo.push.api;

import com.demo.push.annotation.RestApi;
import com.demo.push.annotation.method.MyDelete;
import com.demo.push.annotation.method.MyPost;
import com.demo.push.annotation.param.BodyParam;
import com.demo.push.annotation.param.PathParam;
import com.demo.push.common.ApiResult;
import com.demo.push.dto.TokenDTO;
import com.demo.push.dto.req.AuthDTO;

/**
 * ClassName:AuthApi
 * Package:com.demo.push.annotation.api
 * Description:
 *
 * @Date:2023/4/3 17:09
 * @Author:qs@1.com
 */
@RestApi
public interface AuthApi {
    /**
     * 获取鉴权token接口
     *
     * @param authDTO
     * @return
     */
    @MyPost(uri = "/auth", needToken = false)
    ApiResult<TokenDTO> auth(@BodyParam AuthDTO authDTO);

    /**
     * 关闭鉴权token
     *
     * @param token
     * @return
     */
    @MyDelete(uri = "/auth")
    ApiResult<Void> close(@PathParam String token);
}
