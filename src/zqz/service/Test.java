package zqz.service;

import spring.ApplicationContext;

public class Test {

    public static void main(String[] args) throws Exception {

        ApplicationContext app = new ApplicationContext(AppConfig.class);
        Object userService = app.getBean("UserService");
        System.out.println(userService);
        System.out.println(app.getBean("UserService"));
    }

}
