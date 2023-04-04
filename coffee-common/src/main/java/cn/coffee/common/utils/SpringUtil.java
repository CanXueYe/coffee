package cn.coffee.common.utils;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
        log.info("\r\n----------加载applicationContext成功-----------------");
    }

    // 获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    // 通过name获取 Bean.
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    // 通过class获取Bean.
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz) {
        try {
            char[] cs = clazz.getSimpleName().toCharArray();
            cs[0] += 32;// 首字母大写到小写
            return (T) getApplicationContext().getBean(String.valueOf(cs));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}
