import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * ClassName:LearnSomeThingFromSpringBoot
 * Package:PACKAGE_NAME
 * Description:
 *
 * @Date:2022/6/7 14:20
 * @Author:qs@1.com
 */
public class LearnSomeThingFromSpringBoot {

    @Test
    public void testRemoveDuplicates() {
        List<String> originList = new ArrayList<>();
        originList.add("a");
        originList.add("c");
        originList.add("b");
        originList.add("a");
        originList.add("b");

        ArrayList<String> filteredList = new ArrayList<>(new HashSet<>(originList));
        System.out.println(filteredList);

    }

}
