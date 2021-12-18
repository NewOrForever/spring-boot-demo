package com.learn.strack;

import java.util.Arrays;

/**
 * ClassName:StrackList
 * Package:com.example.boot
 * Description: 底层使用数组来模拟堆栈使用
 * <p>
 * 属性：数组元素、当前索引、容量（但是可以扩容啊，所以还是不要了）
 * 方法：出栈删除元素，入栈添加元素、当前元素数量
 * 初始化对象时创建指定容量的数组
 *
 * @Date:2021/11/26 9:13
 * @Author:qs@1.com
 */
public class StrackList {
    private Object[] elements;
    private int index = 0;
    public static final int CAPACITY_DEFAULT = 3;

    public StrackList() {
        this.elements = new Object[CAPACITY_DEFAULT];
    }

    /**
     * 入栈：往数组中添加元素，添加前需要先判断数组容量是否满了
     * 若满了则先扩容
     *
     * @param o
     */
    public void add(Object o) {
        elements = ensureCapacity();
        elements[index++] = o;
    }

    private Object[] ensureCapacity() {
        if (index == elements.length) {
            int newLength = elements.length * 2 + 1;
            return Arrays.copyOf(elements, newLength);
        }
        return elements;
    }

    /**
     * 出栈
     * 需要出栈的位置需要赋值位null，防止内存溢出
     *
     * @return
     */
    public Object pop() {
        Object o = elements[--index];
        elements[index] = null;
        return o;
    }

    public int getSize() {
        return index;
    }

}
