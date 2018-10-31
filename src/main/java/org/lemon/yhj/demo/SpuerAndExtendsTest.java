package org.lemon.yhj.demo;


import java.util.ArrayList;
import java.util.List;

public class SpuerAndExtendsTest {


//    public static void main(String[] args) {
//        A a = new SpuerAndExtendsTest().new A();
//        B b = new SpuerAndExtendsTest().new B();//继承A
//        C c = new SpuerAndExtendsTest().new C();//继承B
//
//        //下界<? super B>用于限定集合参数类型，限定集合中的元素只能存放B或B的子类
//        List<? super B> testSuper = new ArrayList<>();
//        testSuper.add(a);//报错
//        testSuper.add(b);
//        testSuper.add(c);
//        A a1 = testSuper.get(0);//报错
//        B b1 = testSuper.get(0);//报错
//        C c1 = testSuper.get(0);//报错
//        List<Object> objects = testSuper;//报错，下界<? super B>用于限定参数化类型，不能被赋值给任何集合
//
//        List<? extends B> testExtends = getBList();
//        A a2 = testExtends.get(0);
//        B b2 = testExtends.get(0);//可以读取到B及B的父类，编译器能确定集合中的元素是大于等于B的
//        C c2 = testExtends.get(0);//报错
//
//        List<C> back = new ArrayList<>();
//        testExtends = back;//可以把子类的集合赋值给上界的标识的集合，因为编译器可以
//        C c3 = testExtends.get(0);//报错
//
//    }
//
//    private static List<? extends B> getBList(){
//        A a = new SpuerAndExtendsTest().new A();
//        B b = new SpuerAndExtendsTest().new B();//继承A
//        C c = new SpuerAndExtendsTest().new C();//继承B
//        //上界<? extends B>，主要用于限定方法的返回类型，限定了集合类中只能获取到B或者其父类的实例元素
//        List<? extends B> testExtends = new ArrayList<B>();//无法通过这种方法来告诉编译器集合中元素具体的类型
//        testExtends.add(a);//报错，不能确定具体的类型，所以编译器什么元素也不让放进去
//        testExtends.add(b);//报错，对于编译器来说，即使声明了new ArrayList<B>，并不能改变testExtends的定义
//        testExtends.add(c);//报错
//        return testExtends;
//    }

    class A{
        public void get(){
            System.out.println("A");
        }
    }

    class B extends A{
        public void get(){
            System.out.println("B");
        }
    }

    class C extends B{
        public void get(){
            System.out.println("C");
        }
    }
}
