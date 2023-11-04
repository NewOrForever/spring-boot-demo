import org.junit.Test;

/**
 * ClassName:TestInterface
 * Package:PACKAGE_NAME
 * Description:
 *
 * @Date:2023/7/29 14:09
 * @Author:qs@1.com
 */
public class TestInterface {

    @Test
    public void testInterface() {
        Class<B> bClass = B.class;
        System.out.println(bClass.getInterfaces().length);
        System.out.println(bClass.getInterfaces()[0]);

        B b = new B();
        System.out.println(b.getClass().getName());
    }



}

interface A {

}

class B implements A {

}
