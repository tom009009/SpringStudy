package zqz.service;

import spring.Autowired;
import spring.Component;

@Component("userService")
// @Scope("prototype")  // 控制单例还是原型
public class UserService {

    @Autowired
    private OrderService orderService;

    public void test() {
        System.out.println(orderService);
    }

}
