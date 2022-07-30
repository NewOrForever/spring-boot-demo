import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ClassName:ObjectRefTest
 * Package:PACKAGE_NAME
 * Description:
 *
 * @Date:2022/6/21 15:47
 * @Author:qs@1.com
 */
public class ObjectRefTest {

    /**
     * 对像引用的测试
     */

    @Test
    public void test() {
        List<String> originList = new ArrayList<>();
        originList.add("a");
        List<String> copyList = originList;
        List<String> newList = new ArrayList<>(originList);
        // copyList和originList指向同一个对象地址，改一个另一个也自动改掉了
        copyList.add("c");
        originList.add("d");
        // 新建的对象，地址不同
        newList.add("b");

    }


}
