package zqz.spring;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {

    private Class configClass;

    // 单例bean map
    private Map<String, Object> singletonMap = new ConcurrentHashMap<>();
    // bean definition map
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public ApplicationContext(Class configClass) {
        this.configClass = configClass;

        // 扫描componentScan路径--> 扫描component对象 --> beanDefinition --> beanDefinitionMap
        scan(configClass);

        // 创建单例bean
        for (String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (beanDefinition.getScope().equals("singleton")) {
                Object bean = createBean(beanDefinition);
                singletonMap.put(beanName, beanDefinition);
            }
        }
    }

    public Object createBean(BeanDefinition beanDefinition) {
        Class clazz = beanDefinition.getClazz();
        Object instance = null;
        try {
            instance = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return instance;
    }

    private void scan(Class configClass) {
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScan = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            // 得到扫描路径
            String path = componentScan.value();
            path = path.replace("." , "/");
            System.out.println("得到的路径为：" + path);

            // 需要扫描class文件,类加载器三种：
            // 1,BootStrap : Jre/lib
            // 2,Ext : jre/ext/lib
            // 3,app : 当前应用的classpath
            ClassLoader classLoader = ApplicationContext.class.getClassLoader(); // app
            URL resource = classLoader.getResource(path);
            File file = new File(resource.getFile());
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    // 需要把的class  D:\workspace\IDEAworkspace\SpringStudy\out\production\SpringStudy\zqz\service\TestService.class
                    // 修改为 zqz.service.TestService
                    String fileName = f.getAbsolutePath();
                    if (fileName.endsWith(".class")) {
                        String className = fileName.substring(fileName.indexOf("zqz"), fileName.indexOf(".class"));
                        className = className.replace("\\", ".");
                        System.out.println(className);
                        try {
                            Class<?> clazz = classLoader.loadClass(className);
                            if (clazz.isAnnotationPresent(Component.class)) {
                                // 如果有component注解，则表示当前类是一个bean，分单例bean和原型(prototype)bean。
                                // 解析类----> BeanDefinition
                                Component componentAnnotation = clazz.getDeclaredAnnotation(Component.class);
                                String beanName = componentAnnotation.value();
                                BeanDefinition beanDefinition = new BeanDefinition();
                                beanDefinition.setClazz(clazz);
                                // 如果存在scope注解，说明是原型对象，非单例
                                if (clazz.isAnnotationPresent(Scope.class)) {
                                    Scope scopeAnnotation = clazz.getDeclaredAnnotation(Scope.class);
                                    // 否则是单例对象
                                    beanDefinition.setScope(scopeAnnotation.value());
                                } else {
                                    beanDefinition.setScope("singleton");
                                }
                                beanDefinitionMap.put(beanName, beanDefinition);
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public Object getBean(String beanName) throws Exception {
        if (beanDefinitionMap.containsKey(beanName)) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            // 单例bean到单例池去取对象
            if (beanDefinition.getScope().equals("singleton")) {
                return singletonMap.get(beanName);
                // 原型bean则创建对象
            } else {
                return createBean(beanDefinitionMap.get(beanName));
            }
        } else {
            throw new Exception("没有对应的bean");
        }
    }
}
