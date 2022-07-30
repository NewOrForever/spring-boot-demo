import org.junit.Test;

/**
 * ClassName:TestIOCost
 * Package:PACKAGE_NAME
 * Description:
 *
 * @Date:2022/5/10 13:58
 * @Author:qs@1.com
 */
public class TestIOCost {

    @Test
    public void test() {
        int[][] arr = new int[10000][10000];
        int sum = 0;

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < 10000; j++) {
                sum += arr[i][j];
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("按行耗时：" + (end - start) + "ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < 10000; j++) {
                sum += arr[j][i];
            }
        }
        end = System.currentTimeMillis();
        System.out.println("按列耗时：" + (end - start) + "ms");
    }
}
