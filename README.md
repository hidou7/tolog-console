# 简介

基于tolog-appender的控制台。

# 使用

## application.yml

```yml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/tolog?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&allowMultiQueries=true
    username: username
    password: password
  thymeleaf:
    cache: false

# 登录的用户名和密码
tolog:
  username: admin
  password: admin
```


![image](https://user-images.githubusercontent.com/58681469/206948488-d416f33e-3d01-4a6e-b29b-be45506bbc58.png)
