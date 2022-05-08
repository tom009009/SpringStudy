package zqz.service;

import spring.ApplicationContext;
import spring.BeanPostProcessor;
import spring.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component //需要让spring来扫面这个类
public class TestBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {

        System.out.println("BeanPostProcessor初始化前");
        // 在这里可以扩展自己想要的机能
        // eg.
        if (beanName.equals("userService")) {
            ((UserService)bean).setTestBeanPostProcessorName("TestBeanPostProcessorNameOK");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("BeanPostProcessor初始化后");
        // 实现AOP就是在postProcessAfterInitialization里面实现, 这里利用JDK的动态代理
        if (beanName.equals("userService")) {
            // 这里的newProxyInstance的参数的ClassLoader一定要设置为当前应用的classLoader,比如orderService或者ApplicationContext都可以，但是比如用JDK自带的ArrayList之类的就不行，因为不是一个classLoader
            Object proxyInstance = Proxy.newProxyInstance(TestBeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("这里是代理逻辑");
                    Object obj = method.invoke(bean, args);
                    return obj;
                }
            });
            return proxyInstance;
        }
        return bean;
    }
}
