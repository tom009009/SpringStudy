package zqz.service;

import spring.Autowired;
import spring.BeanNameAware;
import spring.Component;
import spring.InitializingBean;

@Component("userService")
// @Scope("prototype")  // 控制单例还是原型
public class UserService implements BeanNameAware, InitializingBean, UserServiceInf {

    @Autowired
    private OrderService orderService;

    private String beanName;

    private String testBeanPostProcessorName;

    public void setTestBeanPostProcessorName(String testBeanPostProcessorName) {
        this.testBeanPostProcessorName = testBeanPostProcessorName;
    }

    @Override
    public void test() {
        System.out.println("orderService对象为:" + orderService);
        System.out.println("beanName为:" + beanName);
        System.out.println("通过beanPostProcessor设置后的属性testBeanPostProcessorName为:" + testBeanPostProcessorName);
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 有需要的初始化逻辑可以在这里实装
        System.out.println("有需要的初始化逻辑可以在这里实装");
    }

}
