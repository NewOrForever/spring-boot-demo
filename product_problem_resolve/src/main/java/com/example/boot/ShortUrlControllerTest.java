package com.example.boot;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import redis.clients.jedis.Jedis;

/**
 * ClassName:ShortUrlControllerTest
 * Package:com.example.boot
 * Description:
 *
 * @Date:2025/2/28 14:40
 * @Author:qs@1.com
 */
@Controller
public class ShortUrlControllerTest {
    @GetMapping("/s/{shortCode}")
    public RedirectView redirectToOriginalUrl(@PathVariable String shortCode) {
        // shortUrl 与 longUrl 的映射关系存储到 Redis
        Jedis jedis = new Jedis("192.168.50.8", 6379);
        String key = "shortKey:" + shortCode;
        String originalUrl = jedis.get(key);
        jedis.close();

        /**
         * 如果找到原始URL，返回302重定向
         * 重定向的过程：
         *   1. 浏览器会收到 302 状态码
         *   2. 浏览器会自动发起第二次请求到目标 URL
         *   3. 地址栏会显示最终的 URL
         */
        if (originalUrl != null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl(originalUrl);
            redirectView.setStatusCode(HttpStatus.FOUND); // 302
            return redirectView;
        }

        /**
         * 如果找到原始URL且是同一个域名，使用转发
          */
        /*if (originalUrl != null) {
            return new ModelAndView("forward:" + originalUrl);
        }*/

        // 3. 如果短链不存在，返回404
        throw new RuntimeException("Short URL not found");
    }


    @GetMapping("/generateShortUrl")
    @ResponseBody
    public String generate() {
        String longUrl = "http://192.168.50.67:9000/temp-test/2024/05/20/bbdef7ffc0984851a6f4a2a7cd3e13a2.jpg";

        String shortKey = ShortUrlGenerate.generateShortUrl(longUrl);

        // shortUrl 与 longUrl 的映射关系存储到 Redis
        Jedis jedis = new Jedis("192.168.50.8", 6379);
        String key = "shortKey:" + shortKey;
        jedis.set(key, longUrl);
        jedis.expire(key, 3600 * 24);
        jedis.close();

        /**
         * 短链前缀（可以是专门用于短链的二级域名，也可以是自定义的短链前缀）
         * 例如：http://short.domain.com、http://www.domain.com/s/
         */
        String shortUrlPrefix = "http://localhost:8080/s/";
        // 拼接短链
        String shortUrl = shortUrlPrefix + shortKey;

        return shortUrl;
    }

}
