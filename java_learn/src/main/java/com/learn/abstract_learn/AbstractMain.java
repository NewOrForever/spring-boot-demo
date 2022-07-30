package com.learn.abstract_learn;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:AbstractMain
 * Package:com.learn.abstract_learn
 * Description:
 *
 * @Date:2022/1/25 14:05
 * @Author:qs@1.com
 */
public class AbstractMain extends MyAbstract{

    private String username;
    protected String usercode;

    public static void main(String[] args) {
        AbstractMain abstractMain = new AbstractMain();
        AAA aaa = new AAA();
        abstractMain.setName("abstractMain");
        abstractMain.setCode("code001");
        abstractMain.aaa();

        aaa.test02();
    }

    @Override
    public void test() {

    }
}
