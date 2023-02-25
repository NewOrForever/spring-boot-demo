package com.learn;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.net.*;

/**
 * ClassName:FileTest
 * Package:com.learn
 * Description:
 *
 * @Date:2023/1/30 15:41
 * @Author:qs@1.com
 */
public class FileTest {
    public static void main(String[] args) throws IOException {
//        File file = new File("D:/freecode/uploadPath/upload/2022/12/15/房屋模型图片_20221215092141A009.jpg");
//        System.out.println(file);
//        System.out.println(file.getPath());
//        System.out.println(file.toURI());
//        System.out.println(file.toURI().getPath());
//        System.out.println(new FileUrlResource("D:/freecode/uploadPath/upload/2022/12/15/房屋模型图片_20221215092141A009.jpg").getURL() );

        /**********取网络图片, httpcurlconnection 请求带中文链接会报错，需要将中文部分转码（只需要转中文就行了）************/
        String url = "https://res.browser.qq.com/pcqb/navigate/assets/QQ三国doodle1.10.png";
        int split = url.lastIndexOf("/");
        String fileName = url.substring(split + 1);
        String uri = url.substring(0, split + 1);
        String encodeUrl = uri + URLEncoder.encode(fileName, "utf-8");
        InputStream inputStream = resolveAndOpenStream(encodeUrl);


//        URL url2 = new URL("https://res.browser.qq.com/pcqb/navigate/assets/QQ三国doodle1.10.png");
//        URLConnection urlConnection = (HttpURLConnection) url2.openConnection();
//        urlConnection.getInputStream();

        String origin = "https://res.browser.qq.com/pcqb/navigate/assets/QQ三国doodle1.10.png";
        int lastslat = url.lastIndexOf("/");
        String fileName02 = url.substring(lastslat + 1);
        String uri02 = url.substring(0, split + 1);
        // 将最后的文件名进行 url 编码
        String encodeUrl2 = uri02 + URLEncoder.encode(fileName, "utf-8");
        URL url02 = new URL(encodeUrl2);
        URLConnection urlConnection2 = (HttpURLConnection) url02.openConnection();
        urlConnection2.getInputStream();
    }

    public static InputStream resolveAndOpenStream(final String uri) {
        java.io.InputStream is = null;
        try {
            URLConnection connection = new URL(uri).openConnection();
            if (connection instanceof HttpURLConnection) {
                connection = onHttpConnection((HttpURLConnection) connection);
            }
                is = connection.getInputStream();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return is;
    }

    protected static URLConnection onHttpConnection(HttpURLConnection origin) throws MalformedURLException, IOException {
        URLConnection connection = origin;
        int status = origin.getResponseCode();

        if (needsRedirect(status)) {
            // get redirect url from "location" header field
            String newUrl = origin.getHeaderField("Location");

            if (origin.getInstanceFollowRedirects()) {
                System.out.println("Connection is redirected to: " + newUrl);
                // open the new connnection again
                connection = new URL(newUrl).openConnection();
            } else {
                System.out.println("Redirect is required but not allowed to: " + newUrl);
            }
        }
        return connection;
    }

    protected static final boolean needsRedirect(int status) {
        return
                status != HttpURLConnection.HTTP_OK
                        && (
                        status == HttpURLConnection.HTTP_MOVED_TEMP
                                || status == HttpURLConnection.HTTP_MOVED_PERM
                                || status == HttpURLConnection.HTTP_SEE_OTHER
                );
    }
}
