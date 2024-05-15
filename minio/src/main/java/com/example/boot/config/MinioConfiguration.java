package com.example.boot.config;

import io.minio.MinioClient;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.*;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:MinioConfiguration
 * Package:com.example.boot.config
 * Description:
 *
 * @Date:2024/4/16 10:54
 * @Author:qs@1.com
 */
@Configuration
@EnableConfigurationProperties(MinioProperties.class)
@ConditionalOnProperty(value = "oss.name", havingValue = "minio")
public class MinioConfiguration {
    @Autowired
    private MinioProperties ossProperties;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(ossProperties.getEndpoint())
                .credentials(ossProperties.getAccessKey(), ossProperties.getSecretKey())
                /**当 minio server 使用了 https，那么将该代码放出来，不然会报 ssl trust 相关的错误哦*/
                // .httpClient(getUnsafeOkHttpClient())
                .build();
    }

    public static OkHttpClient getUnsafeOkHttpClient() {
        return new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .sslSocketFactory(getSSLSocketFactory(), getX509TrustManager())
                .hostnameVerifier(getHostnameVerifier())
                .build();
    }


    public static SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, getTrustManager(), new java.security.SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static TrustManager[] getTrustManager() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };
    }
    public static X509TrustManager getX509TrustManager() {
        X509TrustManager trustManager = null;
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            trustManager = (X509TrustManager) trustManagers[0];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return trustManager;
    }


    public static HostnameVerifier getHostnameVerifier() {
        return (s, sslSession) -> true;
    }
}
