package com.auty.modules;

import android.content.Context;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public abstract class Applet {

    String appName;

    public Applet(String appName, Object config){
        this.appName = appName;
    }

    public ArrayList<String> listFunctionality(){
        ArrayList<String> methodList = new ArrayList<>();
        Method[] methods = this.getClass().getDeclaredMethods();

        for (Method method : methods) {
            if (!method.getName().startsWith("get") && !Modifier.isStatic(method.getModifiers())) {
                methodList.add(method.getName());
            }
        }

        return methodList;
    }

}
