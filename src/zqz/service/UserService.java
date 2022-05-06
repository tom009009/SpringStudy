package zqz.service;

import spring.Component;
import spring.Scope;

@Component("UserService")
// @Scope("prototype")  // 控制单例还是原型
public class UserService {
    private OrderService orderService;

}
