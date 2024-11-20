package com.auty.modules.applets;

import android.util.Log;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public abstract class Applet {

    String appName;

    public Applet(String appName, Object config){
        this.appName = appName;
        Log.d("App","Applet constructor");
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
