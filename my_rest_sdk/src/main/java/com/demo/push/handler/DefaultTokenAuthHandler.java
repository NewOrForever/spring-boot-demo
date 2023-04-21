package com.demo.push.handler;

import com.demo.push.ApiConfiguration;
import com.demo.push.api.AuthApi;
import com.demo.push.common.ApiException;
import com.demo.push.common.ApiResult;
import com.demo.push.common.Assert;
import com.demo.push.core.ApiProxyFactory;
import com.demo.push.dto.TokenDTO;
import com.demo.push.dto.req.AuthDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.EventRecodingLogger;

import java.util.Map;

/**
 * ClassName:TokenAuthHandler
 * Package:com.demo.push.handler
 * Description:
 *
 * @Date:2023/4/7 15:16
 * @Author:qs@1.com
 */
public class DefaultTokenAuthHandler implements AuthHandler {
    private static final Logger log = LoggerFactory.getLogger(DefaultTokenAuthHandler.class);

    private TokenDTO token;
    private AuthApi authApi;

    @Override
    public void handle(ApiConfiguration configuration, Map<String, Object> header, ApiProxyFactory.ApiParam apiParam) {
        check(configuration);

        if (apiParam.getNeedToken()) {
            if (token == null) {
                header.put("token", refreshTokenAndGet(token));
            } else {
                header.put("token", token.getToken());
            }
        }
    }

    /**
     * 刷新token
     *
     * @param usedToken 上次请求使用的token, usedToken==null时一定会刷新
     * @return
     */
    private synchronized String refreshTokenAndGet(TokenDTO usedToken) {
        TokenDTO token = this.token;
        if (usedToken != token && token != null && usedToken != null && !token.expired()) {
            return token.getToken();
        }
        AuthDTO authDTO = AuthDTO.build(apiConfiguration.getAppKey(), apiConfiguration.getMasterSecret());
        final ApiResult<TokenDTO> apiResult = authApi.auth(authDTO);
        if (apiResult.isSuccess()) {
            this.token = apiResult.getData();
        } else {
            log.error("get auth token failed. code: {}, msg: {}, param: {}.", apiResult.getCode(), apiResult.getMsg(), authDTO);
            throw new ApiException("获取token失败: " + apiResult.getMsg(), true);
        }
        if (this.token == null) {
            throw new ApiException("获取token失败: " + apiResult.getMsg(), true);
        }
        return this.token.getToken();
    }

    private void check(ApiConfiguration configuration) {
        Assert.notBlank(configuration.getAppId(), true);
        Assert.notBlank(configuration.getAppKey(), true);
        Assert.notBlank(configuration.getAppSecret(), true);
    }

}
