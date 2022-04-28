package zqz.service;

import zqz.spring.ApplicationContext;

public class Test {

    public static void main(String[] args) throws Exception {

        ApplicationContext app = new ApplicationContext(AppConfig.class);
        Object testService = app.getBean("TestService");
        System.out.println(testService);
        System.out.println(app.getBean("TestService"));
    }

}
