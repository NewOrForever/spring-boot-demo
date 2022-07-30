import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:LearnFromSentinel
 * Package:PACKAGE_NAME
 * Description:
 *
 * @Date:2022/7/20 13:50
 * @Author:qs@1.com
 */
public class LearnFromSentinel {
    @Test
    public void testMethodInvoke() {
        try {
            Method testMethod = LearnFromSentinel.class.getMethod("testMethod", String.class, String.class);
            testMethod.invoke(this, "a");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     *  list插入输入并根据order排序
     */
    @Test
    public void testInsertAndSortList() {
        List<SpiOrderWrapper<ProcessorSlot>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ProcessorSlot processorSlot = new ProcessorSlot();
            processorSlot.setName("slot00" + i);
            int ramOrder = new Random().nextInt(100);
            sortList(list, processorSlot, ramOrder);
        }

        list.forEach(t ->{
            System.out.println(t.spi.name + "====>" + t.order);
        });
    }

    private static volatile long currentTimeMillis;

    public static void main(String[] args) throws InterruptedException {
        currentTimeMillis = System.currentTimeMillis();
        Thread daemon = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    currentTimeMillis = System.currentTimeMillis();
                    try {
                        TimeUnit.MILLISECONDS.sleep(1);
                        //System.out.println(currentTimeMillis);
                    } catch (Throwable e) {

                    }
                }
            }
        });
        daemon.setDaemon(true);
        daemon.setName("sentinel-time-tick-thread");
        daemon.start();

        //TimeUnit.SECONDS.sleep(50);
    }

    public void sortList(List<SpiOrderWrapper<ProcessorSlot>> list, ProcessorSlot spi, int order) {
        // the order lower then the element's index in the list is lower
        // order越小越靠前
        int idx = 0;
        for (; idx < list.size(); idx++) {
            if (list.get(idx).getOrder() > order) {
                break;
            }
        }
        list.add(idx, new SpiOrderWrapper<>(order, spi));
    }

    public void testMethod(String a, String b) {
        System.out.println(a + b);
    }

    class SpiOrderWrapper<T> {
        private ProcessorSlot spi;
        private int order;

        public SpiOrderWrapper(int order, ProcessorSlot spi) {
            this.spi = spi;
            this.order = order;
        }

        public ProcessorSlot getSpi() {
            return spi;
        }

        public void setSpi(ProcessorSlot spi) {
            this.spi = spi;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }
    }

    class ProcessorSlot {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
