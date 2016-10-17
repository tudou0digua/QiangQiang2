package com.cb.qiangqiang2.test.java;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cb on 2016/10/10.
 */

public class Test {
    public static void main(String[] args){
        A a = new A();
        B b = new B();

        B b1 = new B();

        List<Object> objects = new ArrayList<>();

        System.out.print(a instanceof B);

        long s = System.currentTimeMillis();
        System.out.print(s);

    }

    static class A{}

    static class B extends A{}
}
