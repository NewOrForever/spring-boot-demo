import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

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

    @Test
    public void test03() {
        HashMap<String, Object> hashMap = null;
        testMap(hashMap);
        System.out.println(hashMap);
    }

    private void testMap(HashMap<String, Object> hashMap) {
        hashMap = new HashMap<>();
        hashMap.put("test", 111);
    }

    @Test
    public void testListCopy() {
        A test = new A("test");
        List<A> list = new ArrayList<>();
        list.add(test);
        System.out.println(list);

        ArrayList<A> copyList = new ArrayList<>(list);
        System.out.println(copyList);
        copyList.get(0).setName("copy");
        System.out.println();
    }

    @Test
    public void testLinkedHashMapInstanceOf() {
        Map map = new HashMap<>();
        System.out.println(map instanceof LinkedHashMap);
    }

    @Test
    public void testTreeMap() {
        // 无序
        Map map = new HashMap<>();
        map.put("key001", "val001");
        map.put("key003", "val003");
        map.put("key002", "val002");
        map.put("key005", "val005");
        map.put("key004", "val004");

        // 按照key有序
        Map treeMap = new TreeMap(map);

        // 无序
        Map linkedHashMap = new LinkedHashMap(map);

        // 按照添加顺序
        Map linkedHashMap2 = new LinkedHashMap();
        linkedHashMap2.put("key001", "val001");
        linkedHashMap2.put("key003", "val003");
        linkedHashMap2.put("key002", "val002");
        linkedHashMap2.put("key005", "val005");
        linkedHashMap2.put("key004", "val004");
    }

    /**
     * map 复制
     * 结论：stream 也是浅拷贝，map中的对象没变
     */
    @Test
    public void testMapCopy() {
        Map<String, Object> orderMap = new LinkedHashMap<>();
        orderMap.put("key001", "val001");
        orderMap.put("key003", "val003");
        orderMap.put("key002", "val002");
        orderMap.put("key005", "val005");
        orderMap.put("key004", "val004");
        HashMap<String, Object> res = orderMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, HashMap::new));

        Map<String, Object> orderMap2 = new LinkedHashMap<>();
        orderMap2.put("key001", new A("key001"));
        orderMap2.put("key003", new A("key003"));
        orderMap2.put("key002", new A("key002"));

        HashMap<String, Object> res2 = orderMap2.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, HashMap::new));

    }

    class A {
        private String name;

        public A(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
