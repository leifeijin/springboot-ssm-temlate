# template-springboot-mybatis-plus

后端SpringBoot框架模板项目，基于SpringBoot2.3.2+mybatis-plus3.3.2+JDK1.8开发

## Introduction
> 本项目为后端SpringBoot模板样例，便于后续开发人员构建新项目

以下为本项目目录结构详细介绍：

```
├─logs                                                  // 系统生成日志文件夹
|   application_warn.log                                // 系统警告日志
|   application_info.log                                // 系统运行日志
|   application_error.log                               // 系统错误日志
└─src                                                   // 代码目录
   ├─main                                               // 主入口
   |   ├─java                                           // java源码
   |   |  └─com.gczx.application
   |   |   |   Application                              // 程序主入口
   |   |   ├─common                                     // 通用函数、类或模块
   |   |   |   |   JsonResult                           // 返回前端格式json类
   |   |   |   |   PaginationDto                        // 分页DTO类
   |   |   |   |
   |   |   |   ├─config                                 // 配置模块
   |   |   |   |   InterceptorConfig                    // 拦截器配置类
   |   |   |   |   SwaggerConfig                        // swagger配置类
   |   |   |   |
   |   |   |   ├─definition                             // 全局变量模块
   |   |   |   |   Definition                           // 全局变量
   |   |   |   |
   |   |   |   ├─exception                              // 异常配置模块
   |   |   |   |   BaseBusinessException                // 业务基础异常
   |   |   |   |   GlobalExceptionHandler               // 全局异常处理类
   |   |   |   |   ResponseCodeEnum                     // 异常类型定义类
   |   |   |   |   UserException                        // 用户基础异常，继承BaseBusinessException
   |   |   |   |
   |   |   |   ├─interceptor                            // 拦截器
   |   |   |   |   AuthenticationInterceptor            // 权限认证拦截
   |   |   |   |
   |   |   |   └─utils                                  // 公用工具类文件夹
   |   |   |      DateUtils                             // 日期公用方法处理类
   |   |   |      UUIDUtils                             // UUID生成类
   |   |   |
   |   |   ├─controller                                 // 控制器目录
   |   |   |   |   DemoController                       // demo控制器
   |   |   |   |   FileController                       // 文件控制器
   |   |   |   |   UserController                       // 用户控制器
   |   |   |   └─dto                                    // 接收前端参数目录
   |   |   |      DemoAddDto                            // demo新增参数dto
   |   |   |      DemoGetDto                            // demo查询参数dto
   |   |   |      DemoUpdateDto                         // demo修改参数dto
   |   |   |      UserGetDto                            // user修改参数dto
   |   |   |
   |   |   ├─entity                                     // 实体类
   |   |   |   AttachmentEntity                         // 附件实体类
   |   |   |   BaseEntity                               // 基础实体类
   |   |   |   DemoEntity                               // demo实体类
   |   |   |   UserEntity                               // 用户实体类
   |   |   |
   |   |   ├─handler                                    // 全局处理器
   |   |   |   HeaderModifierHandler                    // 修改头文件处理器
   |   |   |   MyMetaObjectHandler                      // mybatis字段填充处理器
   |   |   |
   |   |   └─service                                    // 业务类
   |   |      |   IAttachmentService                    // 附件业务接口
   |   |      |   IDemoService                          // demo业务接口
   |   |      |   IUserService                          // 用户业务接口
   |   |      ├─dao
   |   |      |   IAttachmentMapper                     // 附件dao接口
   |   |      |   IDemoMapper                           // demo dao接口
   |   |      |   IUserMapper                           // 用户dao接口
   |   |      └─impl
   |   |         AttachmentServiceImpl                  // 附件业务接口实现类
   |   |         DemoServiceImpl                        // demo业务接口实现类
   |   |         UserServiceImpl                        // 用户业务接口实现类
   |   |
   |   └─resources                                      // 配置文件目录
   |     | application.yml                              // 程序配置主文件
   |     | application-dev.yml                          // 程序开发版配置文件
   |     | application-prod.yml                         // 程序生产版配置文件
   |     | application-qa.yml                           // 程序qa配置版文件
   |     | logback-spring.xml                           // 日志输出格式配置文件
   |     |
   |     ├─lib                                          // 本地jar包目录
   |     |   DmJdbcDriver18.jar                         // 达梦数据库驱动包
   |     |
   |     └─mapper
   |         DemoMapper.xml                             // IDemoMapper对应mybatis的接口实现类文件
   |
   └─test
      └─java
         └─com.gczx.application
             ApplicationTests                           // 程序测试主类
```

## HowToUse

#### swagger地址

http://localhost:8080/swagger-ui.html

#### Development

To start your application in the dev profile, simply run:

```shell
mvn
```

#### Production

- Packaging as jar

To build the final jar and optimize the pvisp application for production, run:

```shell
mvn -Pprod clean verify
```

To ensure everything worked, run:

```shell
java -jar target/*.jar
```

- Packaging as war

To package your application as a war in order to deploy it to an application server, run:

```shell
mvn -Pprod,war clean verify
```
