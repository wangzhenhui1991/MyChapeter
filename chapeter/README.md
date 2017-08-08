
#该项目用来进行日常功能测试使用 以及 对新掌握的技术练手的功能


# 目前项目基于spring boot v1.5.3.Realease



# 1. @RestController
   - 1.1 对RESTful的接口，通过UserController，写了几个demo，涵盖了日常使用。

# 2. SpringBootTest 测试环境
    - 2.1 MockMvc测试，可对Controller进行mock测试
    - 2.2
# 3. @ControllerAdvice
    - 3.1 可对项目全局抛出异常的进行统一处理

# 4. Util
    - 4.1 Printable 抽象类，主要对继承该抽象类的子类，方便实现toString()方法，该技巧来自@liuhaoyong
    - 4.2 //TODO lombok工具包，也是非常好用简化操作的工具包，也可以实现toString，get、set方法，以及构造函数，但是在toString上并没有Printable上好用。

# 5. @Configuration
     @EnableStateMachine 状态机

     - 5.1 实现了简单的状态机，详见stateMechineConfig和stateMachineEventConfig

# 6. 添加了mabatis 以及 spring-data-jpa ORM框架，

# 7. TODO 日志
# 8. TODO 拦截器
# 9. TODO AOP
#
# 10. TODO spring-security 实现授权登录
# 11. TODO @schedule 定时任务
# 12. TODO actuator
# 13. TODO druid 数据源
# 14. TODO ELK
# 15. TODO