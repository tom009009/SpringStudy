package zqz.spring;

import java.io.File;
import java.net.URL;

public class ApplicationContext {

    private Class configClass;

    public ApplicationContext(Class configClass) {
        this.configClass = configClass;

        // 扫描配置类componentScan注解
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScan = (ComponentScan)configClass.getAnnotation(ComponentScan.class);
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
                            Class<?> clazz = classLoader.loadClass(f.getName());
                            if (clazz.isAnnotationPresent(Component.class)) {
                                // 如果有component注解，则表示当前类是一个bean

                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    }

                }
            }
        }
    }

    public Object getBean(String beanName) {

        return null;
    }
}
