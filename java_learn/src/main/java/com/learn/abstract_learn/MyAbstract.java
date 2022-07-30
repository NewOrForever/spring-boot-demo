package com.learn.abstract_learn;

/**
 * ClassName:MyAbstract
 * Package:com.learn.abstract_learn
 * Description:
 *
 * @Date:2022/1/25 14:01
 * @Author:qs@1.com
 */
public abstract class MyAbstract {

    private String name;

    protected String code;

    public abstract void test();

    public void aaa(){
        System.out.println(name + ", " + code);
    }

    private void bbb(){

    }

    public MyAbstract() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void test01(){}
    protected void test02(){}
    private void test03(){}
}
