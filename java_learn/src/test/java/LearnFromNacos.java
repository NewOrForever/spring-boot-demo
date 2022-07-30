import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:LearnFromNacos
 * Package:PACKAGE_NAME
 * Description:
 *
 * @Date:2022/6/23 13:54
 * @Author:qs@1.com
 */
public class LearnFromNacos {

    @Test
    public void testStr() {
        String groupName = "group";
        String serviceName = "service";
        String result = groupName + "@@" + serviceName;
        System.out.println(result.intern());
    }

    @Test
    public void testSplitStr() {
        String a = "group@@name";
        String b = "group@@";
        String c = "@@name";
        System.out.println(a.split("@@").length);
        System.out.println(b.split("@@").length);
        System.out.println(c.split("@@").length);
    }

    @Test
    public void testList() {
        List<User> list = new ArrayList<>();
        list.add(new User("a"));
        List<User> upList = new ArrayList<>(list);
        User user = upList.get(0);
        user.setName("b");
    }

    @Test
    public void testTime() {
        System.out.println(TimeUnit.SECONDS.toMillis(5));
        System.out.println(TimeUnit.SECONDS.toSeconds(5));
    }

    @Test
    public void testFile() {
        String dirName = "conf";
        // 拼接
        Path path = Paths.get("E:\\Workspaces\\tuling\\nacos-cluster\\nacos-8848", dirName);
    }

    @Test
    public void testCopyModelProperty() {
        User user = new User("test");
        User target = new User();
        BeanUtils.copyProperties(user, target);
    }

    @Test
    public void testPropertySource() {
        MutablePropertySources incoming = new MutablePropertySources();
        ArrayList<PropertySource<?>> list = new ArrayList<>();

        list.add(new MyPropertySource("key01", new User("a")));
        list.add(new MyPropertySource("key02", new User("b")));
        list.add(new MyPropertySource("key03", new User("c")));

        ArrayList<PropertySource<?>> reverseList = new ArrayList<>(list);
        Collections.reverse(reverseList);

        for (PropertySource<?> myPropertySource : reverseList) {
            incoming.addFirst(myPropertySource);
        }
    }

    @Test
    public void testChar() {
        System.out.println(Character.toString((char) 2));
    }

    @Test
    public void testApend() {
        StringBuilder sb = new StringBuilder();
        urlEncode("nacos-config.yaml", sb);
        sb.append('+');
        urlEncode("DEFAULT", sb);
        if (!StringUtils.isEmpty("")) {
            sb.append('+');
            urlEncode("", sb);
        }

        System.out.println(sb.toString());

    }

    static void urlEncode(String str, StringBuilder sb) {
        for (int idx = 0; idx < str.length(); ++idx) {
            char c = str.charAt(idx);
            if ('+' == c) {
                sb.append("%2B");
            } else if ('%' == c) {
                sb.append("%25");
            } else {
                sb.append(c);
            }
        }
    }

    @Test
    public void getVersionNumber() {
        /**
         * 这个算法还不错哎 - 来自nacos
         * 1.4.1 -> 141
         */
        String version = "1.4.1";
        if (version == null) {
            System.out.println(-1);
        }
        String[] vs = version.split("\\.");
        int sum = 0;
        for (int i = 0; i < vs.length; i++) {
            try {
                sum = sum * 10 + Integer.parseInt(vs[i]);
            } catch (Exception e) {
                // ignore
            }
        }
        System.out.println(sum);
    }

    @Test
    public void getNumberToVersion() {
        /**
         * 我自己搞得
         * 141 -> 1.4.1
         */
//        int versionNumber = 204;
//        int remain = versionNumber;
//        String version = "";
//        for (int i = 0; i < String.valueOf(versionNumber).length(); i++) {
//            try {
//                int c = remain % 10;
//                version = c + (i > 0 ? "." : "") + version;
//                remain = (remain - c) / 10;
//            } catch (Exception e) {
//                // ignore
//            }
//        }
//        System.out.println(version);

        /****************方法二*****************/
        int versionNumber = 204;
        char[] chars = String.valueOf(versionNumber).toCharArray();
        String version = "";
        for (char c : chars) {
            version += c + ".";
        }
        version = StringUtils.trimTrailingCharacter(version, '.');
        System.out.println(version);
    }

    @Test
    public void testRef() {
        Map<String, User> cacheMap = new HashMap<>();
        User user = cacheMap.get("key");
        user = new User("test");
        System.out.println(cacheMap.putIfAbsent("key", user));
    }

    @Test
    public void changes() {
        Map<String, Object> before = new HashMap<>();
        before.put("key01", "a");
        before.put("key02", "b");
        before.put("key03", "c");
        Map<String, Object> after = new HashMap<>();
        after.put("key01", "a");
        after.put("key05", "05");
        after.put("key03", "cc");
        after.put("key02", "bb");
        Map<String, Object> result = new HashMap<String, Object>();
        for (String key : before.keySet()) {
            if (!after.containsKey(key)) {
                result.put(key, null);
            }
            else if (!equal(before.get(key), after.get(key))) {
                result.put(key, after.get(key));
            }
        }
        for (String key : after.keySet()) {
            if (!before.containsKey(key)) {
                result.put(key, after.get(key));
            }
        }

        // 有变动的数据
        System.out.println(result);
    }

    @Test
    public void testMethodName() throws NoSuchMethodException {
        Method me = LearnFromNacos.class.getMethod("changes");
        System.out.println(me.getName());
    }

    private boolean equal(Object one, Object two) {
        if (one == null && two == null) {
            return true;
        }
        if (one == null || two == null) {
            return false;
        }
        return one.equals(two);
    }

    @Test
    public void testPath() {
        System.out.println(Paths.get("user.home", "nacos").toString());
    }






    class MyPropertySource extends PropertySource<User> {
        public MyPropertySource(String name, User source) {
            super(name, source);
        }

        @Override
        public Object getProperty(String name) {
            return null;
        }
    }

    @Test
    public void testSchedule() throws InterruptedException {
        new BeeperControl().beepForAnHour();
        TimeUnit.SECONDS.sleep(100);
    }

    class BeeperControl {
        private final ScheduledExecutorService scheduler =
                Executors.newScheduledThreadPool(1);

        public void beepForAnHour() {
            final Runnable beeper = new Runnable() {
                public void run() {
                    System.out.println("beep");
                }
            };

            final ScheduledFuture<?> beeperHandle =
                    scheduler.scheduleAtFixedRate(beeper, 10, 10, TimeUnit.SECONDS);
            // 取消任务
            scheduler.schedule(new Runnable() {
                public void run() {
                    beeperHandle.cancel(true);
                }
            }, 60 * 60, TimeUnit.SECONDS);
        }
    }

    class User {
        private String name;

        public User() {
        }

        public User(String name) {
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
