package zqz.service;

import spring.ApplicationContext;

public class Test {

    public static void main(String[] args) throws Exception {

        ApplicationContext app = new ApplicationContext(AppConfig.class);
        UserService userService = (UserService)app.getBean("userService");
        userService.test();

//        System.out.println(userService);
//        // 这里一定会和上面的UserService是一个对象，因为声明了单例对象
//        System.out.println(app.getBean("UserService"));
    }

}
