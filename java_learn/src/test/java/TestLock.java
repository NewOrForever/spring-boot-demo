import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:TestLock
 * Package:PACKAGE_NAME
 * Description:
 *
 * @Date:2023/2/28 15:10
 * @Author:qs@1.com
 */
public class TestLock {
    private static ConcurrentHashMap<String, Holder<Object>> cachedMap = new ConcurrentHashMap<>();
    private static Map<String, String> map = new HashMap<>();

    static {
        cachedMap.put("132", new Holder<>());
    }


    @Test
    public void testSynchronizedForString() throws InterruptedException {
        Long a = 132L;
        Long b = 132L;
        Long c = 132L;

        String aa = a.toString();
        String bb = b.toString();
        String cc = c.toString();

        /**
         * String 变量来锁即使字符串内容同也是锁不住的
         */

        new Thread(() -> {
            Holder<Object> holder = cachedMap.get(aa);
            m(holder);
        }, "t1").start();

        new Thread(() -> {
            Holder<Object> holder = cachedMap.get(bb);
            m(holder);
        }, "t2").start();

        new Thread(() -> {
            Holder<Object> holder = cachedMap.get("c");
            if (holder == null) {
                holder = new Holder<>();
            }
            m(holder);
        }, "t3").start();

        TimeUnit.SECONDS.sleep(100);
    }

    /**
     * static 数据高于对象在class 层面，每个对象添加数据后，map 包含所有数据
     */
    @Test
    public void testStaticData() {
        TestLock t1 = new TestLock();
        t1.add("test001", "test001");
        TestLock t2 = new TestLock();
        t2.add("test002", "test002");
        map.forEach((k, v) -> System.out.println(k + "&" + v));
    }
    public void add(String key, String value) {
        map.put(key, value);
    }

    public void m(Holder<Object> holder) {
        synchronized (holder) {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
