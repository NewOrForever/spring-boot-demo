import org.junit.Test;
import sun.reflect.generics.tree.Tree;

import javax.xml.ws.RequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * ClassName:LearnSomethingFromDubbo
 * Package:PACKAGE_NAME
 * Description:
 *
 * @Date:2022/6/1 14:53
 * @Author:qs@1.com
 */
public class LearnSomethingFromDubbo {
    // dubbo解析文件获取扩展类
    @Test
    public void testResource() {
        Map<String, Class<?>> extensionClasses = new HashMap<>();
        try {
            String fileName = "META-INF/services/com.example.dubbo.spi.Person";
            Enumeration<URL> urls;
            ClassLoader classLoader = findClassLoader(LearnSomethingFromDubbo.class);
            if (classLoader != null) {
                urls = classLoader.getResources(fileName);
            } else {
                urls = ClassLoader.getSystemResources(fileName);
            }
            if (urls != null) {
                while (urls.hasMoreElements()) {
                    URL resourceURL = urls.nextElement();
                    // 遍历url进行加载,把扩展类添加到extensionClasses中
                    loadResource(extensionClasses, classLoader, resourceURL);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTreeSet() {
        Set<String> treeSet = new TreeSet<>();
        treeSet.add("a");
        treeSet.add("c");
        treeSet.add("b");
        treeSet.add("e");
        treeSet.add("d");
        // 测试可发现：有序
        System.out.println(treeSet);
        treeSet.forEach(System.out::println);
    }

    @Test
    public void testStream() {
        Class type = Person.class;
        // 这个注解随便找的
        boolean result = Arrays.stream(type.getMethods()).anyMatch(m -> m.isAnnotationPresent(RequestWrapper.class));
        System.out.println(result);
    }

    @Test
    public void testClassName() {
        System.out.println("simplename: =============> " + Person.class.getSimpleName());
        System.out.println("canonicalname: =============> " + Person.class.getCanonicalName());
    }

    @Test
    public void testGenerateMethodArgs() {
        Method[] methods = Person.class.getMethods();
        for (Method method : methods) {
            Class<?>[] pts = method.getParameterTypes();
            String code = IntStream.range(0, pts.length)
                    .mapToObj(i -> String.format("%s arg%d", pts[i].getCanonicalName(), i))
                    .collect(Collectors.joining(", "));
            // java.lang.Object arg0, java.lang.String arg1
            System.out.println(code);

            Class<?>[] ets = method.getExceptionTypes();
            String list = Arrays.stream(ets).map(Class::getCanonicalName).collect(Collectors.joining(", "));
            System.out.println(list);
        }
    }

    @Test
    public void testCamelToSplitName() {
        System.out.println(camelToSplitName(Person.class.getSimpleName(), "."));
    }

    @Test
    public void testConcurrentHashSet() {
        Map map = new ConcurrentHashMap<>();
        map.put("key01", "value01");
        map.put("key02", "value02");
        map.put("key05", "value05");
        map.put("key03", "value03");
        map.put("key04", "value04");

        map.forEach((key, value) -> {
            System.out.println(key + "," + value);
        });
    }


    public static String camelToSplitName(String camelName, String split) {
        if (camelName == null || camelName.isEmpty()) {
            return camelName;
        }
        StringBuilder buf = null;
        for (int i = 0; i < camelName.length(); i++) {
            char ch = camelName.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                if (buf == null) {
                    buf = new StringBuilder();
                    if (i > 0) {
                        buf.append(camelName, 0, i);
                    }
                }
                if (i > 0) {
                    buf.append(split);
                }
                buf.append(Character.toLowerCase(ch));
            } else if (buf != null) {
                buf.append(ch);
            }
        }
        return buf == null ? camelName : buf.toString();
    }

    private void loadResource(Map<String, Class<?>> extensionClasses, ClassLoader classLoader, java.net.URL resourceURL) {
        try {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceURL.openStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    final int ci = line.indexOf('#');
                    if (ci >= 0) {
                        line = line.substring(0, ci);
                    }
                    line = line.trim();
                    if (line.length() > 0) {
                        try {
                            String name = null;
                            int i = line.indexOf('=');
                            if (i > 0) {
                                name = line.substring(0, i).trim();
                                // 实现类限定名
                                line = line.substring(i + 1).trim();
                            }
                            if (line.length() > 0) {
                                // 加载类，并添加到extensionClasses中
                                extensionClasses.put(name, Class.forName(line, true, classLoader));
                            }
                        } catch (Throwable t) {
                            IllegalStateException e = new IllegalStateException("Failed to load extension class ( " + ", class line: " + line + ") in " + resourceURL + ", cause: " + t.getMessage(), t);
                        }
                    }
                }
            }
        } catch (Throwable t) {
            System.out.println("Exception occurred when loading extension class ( " +
                     ", class file: " + resourceURL + ") in " + resourceURL);
        }
    }

    public static ClassLoader findClassLoader(Class<?> clazz) {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back to system class loader...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = clazz.getClassLoader();
            if (cl == null) {
                // getClassLoader() returning null indicates the bootstrap ClassLoader
                try {
                    cl = ClassLoader.getSystemClassLoader();
                } catch (Throwable ex) {
                    // Cannot access system ClassLoader - oh well, maybe the caller can live with null...
                }
            }
        }

        return cl;
    }
}

class Holder<T>{
    private volatile T value;

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}

interface Person{
    //@RequestWrapper
    //String getName();
    String getName(Object obj, String str) throws IOException, IllegalArgumentException;
}
