package com.peels;

import org.springframework.security.core.parameters.P;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class demo {
    public static void main(String[] args) {
        List<? super Son>list = new ArrayList<>();
        list.add(new Son());
        list.add(new GrandSon());
        List<Son>list1 = new ArrayList<>();
        test(list);

    }

    public static void test(List<? super  GrandSon>list){
        Class<? extends List> aClass = list.getClass();
        Type[] genericInterfaces = aClass.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
        }
    }
}

class Parent{

}

class Son extends Parent{

}
class GrandSon extends Son{

}