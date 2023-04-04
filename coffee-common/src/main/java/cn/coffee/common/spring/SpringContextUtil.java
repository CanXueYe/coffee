package cn.coffee.common.spring;

import org.springframework.beans.BeansException;

import org.springframework.context.ApplicationContext;


public class SpringContextUtil {

    private static ApplicationContext applicationContext;


    // 设置上下文

    public static void setApplicationContext(ApplicationContext context) throws BeansException {

        applicationContext = context;

    }


    // 获取上下文

    public static ApplicationContext getApplicationContext() {

        return applicationContext;

    }


    // 通过名字获取上下文中的bean

    public static Object getBean(String name) {

        if(name == null || name.length()==0) {

            return null;

        }

        try {

            String beanName = "";

            if(name.length() >1){

                beanName = name.substring(0, 1).toLowerCase() + name.substring(1);

            } else {

                beanName = name.toLowerCase();

            }

            return applicationContext.getBean(beanName);

        } catch (Exception ex){

            ex.printStackTrace();

            return null;

        }

    }


    // 通过类型获取上下文中的bean

    public static <T> T getBean(Class<T> clazz) {

        try {

            return (T) applicationContext.getBean(clazz);

        } catch(Exception ex) {

            ex.printStackTrace();

            return null;

        }

    }

}