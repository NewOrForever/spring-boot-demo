import org.junit.Test;

import java.util.Arrays;

/**
 * ClassName:TestClass
 * Package:PACKAGE_NAME
 * Description: 测试反射相关
 *
 * @Date:2022/12/21 9:36
 * @Author:qs@1.com
 */
public class TestClass {
    @Test
    public void testInterfaceGet() {
        Class<Executor> executorClass = Executor.class;
        System.out.println(Arrays.toString(executorClass.getInterfaces()));
        System.out.println(Arrays.toString(SimpleExecutor.class.getInterfaces()));
    }

    interface Executor {

    }
    class SimpleExecutor implements Executor {

    }
}
