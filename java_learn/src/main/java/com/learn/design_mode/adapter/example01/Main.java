package com.learn.design_mode.adapter.example01;

/**
 * ClassName:PowerAdapter
 * Package:com.learn.design_mode.adapter.example01
 * Description:
 *
 * @Date:2023/2/25 11:28
 * @Author:qs@1.com
 */
public class Main {
    public static void main(String[] args) {

    }

    public static class PowerAdapter01 extends AC220 implements DC5{
        @Override
        public int output5v() {
            return 0;
        }
    }

    public static class PowerAdapter02 implements DC5{
        private AC220 ac220;

        public PowerAdapter02(AC220 ac220) {
            this.ac220 = ac220;
        }

        @Override
        public int output5v() {
            return 0;
        }
    }

}
