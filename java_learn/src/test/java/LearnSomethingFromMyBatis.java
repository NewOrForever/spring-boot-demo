import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.decorators.LruCache;
import org.apache.ibatis.cache.decorators.SynchronizedCache;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.apache.ibatis.io.Resources;
import org.junit.Test;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * ClassName:LearnSomethingFromMyBatis
 * Package:PACKAGE_NAME
 * Description:
 *
 * @Date:2022/3/14 14:28
 * @Author:qs@1.com
 */
public class LearnSomethingFromMyBatis {
    @Test
    public void testDate() {
        System.out.println(new Date());
        System.out.println(new Timestamp(System.currentTimeMillis()));
    }

    @Test
    public void testTryResource() throws IOException {

        Properties props = new Properties();

        // io流原本要try-catch-finally，finally中要去close资源，现在只需要try带参数的简易写法
        try (InputStream is = Resources.getResourceAsStream("mybatis.properties")) {
            // do something
            props.load(is);
        }

        Set<Map.Entry<Object, Object>> entries = props.entrySet();
        for (Map.Entry<Object, Object> entry : entries) {
            System.out.println(entry.getKey() + "->" + entry.getValue());
        }
    }

    @Test
    public void testUnmodifyList() {
        List<String> originList = new LinkedList<>();
        originList.add("a");
        originList.add("b");
        originList.add("c");
        /**
         * 用法：一个类维护了一个List属性（有get方法），拿到这个List后为了不让该list被修改只能是只读，所以让该get方法里面返回的是只读视图
         */

        // 返回的是originList的只读视图
        List<String> unmodList = Collections.unmodifiableList(originList);
        //unmodList.add("d"); // 会报错
    }

    @Test
    public void testMapnull() {
        Map<String, Object> map = new HashMap<>();
        map.put(null, "hahah");
    }

    @Test
    public void testMybatisCache() {
        // mybatis二级缓存的设计模式：装饰 + 责任链
        // 不断的向下委托，最终cache就包了很多层
        Cache cache = new PerpetualCache("id");
        cache = new LruCache(cache);
        cache = new SynchronizedCache(cache);
    }

    @Test
    public void testOptionalNullable() {
        Map variablesContext = new HashMap();
        Map configurationVariables = new HashMap();
        configurationVariables.put("a", "aa");

        // 如果configurationVariables不为空，则执行variablesContext.putAll(configurationVariables)
        Optional.ofNullable(configurationVariables).ifPresent(variablesContext::putAll);
    }

    @Test
    public void MapComputeIfAbsent() {
//        List<Object> list = new ArrayList<>(new HashSet<Object>());

        String[] arr = {"a", "b", "c"};
        String[] valueArr = {"1", "2", "3"};

        Map<String, List<String>> map = new HashMap<>();
        int i = 0;
        for (String s : arr) {
            List<String> strings = map.computeIfAbsent(s, k -> new LinkedList<>());
            strings.add(valueArr[i]);

            i++;
        }

        map.forEach(new BiConsumer<String, List<String>>() {
            @Override
            public void accept(String key, List<String> stringValues) {
                System.out.println(key);
            }
        });
    }

    @Test
    public void TestDoWhileGetInterfaces() {
        Class<?> a = A.class;
        Set<Class<?>> interfaceSet = new HashSet<>();
        do {
            Collections.addAll(interfaceSet, a.getInterfaces());
            a = a.getSuperclass();
        } while (a != null);

        Class[] classes = interfaceSet.toArray(new Class[interfaceSet.size()]);
        for (Class aClass : classes) {
            System.out.println(aClass);
        }
    }

    public class A extends B implements C {
    }

    public class B implements D {
    }

    public interface C {
    }

    public interface D {
    }

    @Test
    public void testStringBuilderAndBuffAndJoin() {
//        StringBuffer stringBuffer = new StringBuffer();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("username=");
        stringBuilder.append("hello");
        stringBuilder.insert(0, " ");
        stringBuilder.insert(0, "where");
        System.out.println(stringBuilder);

        StringJoiner stringJoiner = new StringJoiner(":", "[", "]");
        stringJoiner.add("库里");
        stringJoiner.add("哈登");
        stringJoiner.add("杜兰特");
        System.out.println(stringJoiner);


    }


}
