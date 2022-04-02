package zqz.spring;

public class ApplicationContext {

    private Class configClass;

    public ApplicationContext(Class configClass) {
        this.configClass = configClass;

        //扫描
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScan = (ComponentScan)configClass.getAnnotation(ComponentScan.class);
            // 得到扫描路径
            String path = componentScan.value();
            // 需要扫描class文件
        }
    }

    public Object getBean(String beanName) {

        return null;
    }
}
