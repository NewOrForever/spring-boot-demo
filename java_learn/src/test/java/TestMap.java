import org.junit.Test;

import java.util.HashMap;
import java.util.UUID;

/**
 * ClassName:TestMap
 * Package:PACKAGE_NAME
 * Description:
 *
 * @Date:2022/5/24 10:29
 * @Author:qs@1.com
 */
public class TestMap {
    @Test
    public void test01() {
        HashMap<String, Object> hashMap = new HashMap<>();
        Object obj = new Object();
        System.out.println(obj);
        // 返回的是该key的previous value，没有则返回null
        Object putReturnObj = hashMap.put(UUID.randomUUID().toString().replace("-", ""), obj);
        System.out.println(putReturnObj);
    }

    @Test
    public void test02() {
        HashMap<String, Object> hashMap = new HashMap<>();
        Object obj = new Object();
        System.out.println(obj);
        // Map包含了该key -> get
        // Map不包含该key -> put
        Object keyObj = hashMap.putIfAbsent("key", obj);
        System.out.println(keyObj);
    }
}
