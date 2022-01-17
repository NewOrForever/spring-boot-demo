package com.learn;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ClassName:Main
 * Package:com.learn
 * Description:
 *
 * @Date:2021/12/24 16:26
 * @Author:qs@1.com
 */
public class Main {
    public static void main(String[] args) {
        Child child = new Child();
        System.out.println(child.getClass().getGenericSuperclass());

        try {
            Method method = MyUserService.class.getMethod("test", String.class);
            Parameter[] parameters = method.getParameters();
            for (Parameter parameter : parameters) {
                // jdk做不多获取参数的方法名
                // 但是jdk1.8版本开始,可以配置编译参数来保留参数名，为反射提供便利
                /**
                 * mavan中配置编译参数
                 *     <compilerArgs>
                 *          <!--JDK1.8 开启-PARAMETERS参数，编译保留参数名，为反射提供便利-->
                 *          <arg>-parameters</arg>
                 *     </compilerArgs>
                 */
                System.out.println(parameter.getName());
                System.out.println(parameter.getType());
            }

            // 使用了spring的方法（解析class字节码信息来获取的）
            ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
            String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
            for (String name : parameterNames) {
                System.out.println(name);
            }

            /***************Field****************/
            try {
                Field field = MyUserService.class.getDeclaredField("abc");
                System.out.println("field.getDeclaringClass=" + field.getDeclaringClass());
                System.out.println("field.gettype=" + field.getType());
                System.out.println("field.getgenerictype=" + field.getGenericType());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

            /**************Map Streanm*************/
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("userService", new UserService());
            map.put("orderService", new OrderService());
            map.put("userService1", new UserService());
            map.put("userService2", new UserService());
            map.put("userService3", new OrderService());

            Stream<String> stream = map.keySet().stream()
                    .map(name -> addPrefix(name))
                    .filter(beanName -> beanName.endsWith("Service"));
            //stream.forEach(System.out::println);
            // 转换前不能操作，上面打印了之后在执行这个就报错了
            List<String> list = stream.collect(Collectors.toList());
            list.forEach(System.out::println);

            /******************引用对象存入List、Map、Array，修改引用对象，看集合内部对象是否变******************/
            Map<String, Object> cacheMap = new LinkedHashMap<>();
            List<Object> cacheList = new ArrayList<>();
            Set<Object> cacheSet = new LinkedHashSet<>();
            Object[] cacheArray = new Object[1];
            MyUserService myUserService = new MyUserService();
            cacheMap.put("cacheKey", myUserService);
            cacheList.add(myUserService);
            cacheSet.add(myUserService);
            cacheArray[0] = myUserService;
            // map中的存的对象的属性也变了，因为指向的是同一个内存地址
            // myUserService是对象引用，指向的那个槽修改了，map中的不也就是变了么
            myUserService.setAbc("xx======");
            System.out.println();

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static String addPrefix(String name) {
        return "beanName" + name;
    }
}
