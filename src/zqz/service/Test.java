package zqz.service;

import zqz.spring.ApplicationContext;

public class Test {

    public static void main(String[] args) {

        ApplicationContext app = new ApplicationContext(AppConfig.class);
        Object testService = app.getBean("TestService");
    }

}
