import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * ClassName:TestBinary
 * Package:PACKAGE_NAME
 * Description:
 *
 * @Date:2022/11/21 9:45
 * @Author:qs@1.com
 */
public class TestBinary {

    @Test
    public void convertToByte() {
        String str = "10";
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        String s = "";
        for (byte aByte : bytes) {
             String binStr = Integer.toBinaryString(aByte & 0xff);
            s += binStr;
        }
        System.out.println(s);

        int x = 805310851;
        String s1 = Integer.toBinaryString(x);
        System.out.println(s1);
    }
}
