package com.learn.supplier;

import java.util.function.Supplier;

/**
 * ClassName:supplierTest
 * Package:com.learn.supplier
 * Description:
 *
 * @Date:2022/1/18 16:53
 * @Author:qs@1.com
 */
public class SupplierTest {
    public static void main(String[] args) {
        Supplier<Object> supplier = new Supplier<Object>() {
            @Override
            public Object get() {
                return new MySupplierObject();
            }
        };
        System.out.println(supplier.get());

        test(() -> {
            MySupplierObject mySupplierObject = new MySupplierObject();
            mySupplierObject.setA(1);
            return mySupplierObject.getA();
        });
    }

    public static void test(Supplier<?> supplier) {
        System.out.println("this is test 。。。");
        System.out.println("supplier.get()=" + supplier.get());
        System.out.println("execute supplier finish。");
    }
}

class MySupplierObject{
    private int a;

    public void setA(int a) {
        this.a = a;
    }

    public int getA() {
        return a;
    }
}

