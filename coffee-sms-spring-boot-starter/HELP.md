# 使用方法

## 一：打包 -- install coffee  

### 引用 coffee-sms-spring-boot-starter

`<dependency>
<groupId>cn.lhz</groupId>
<artifactId>coffee-sms-spring-boot-starter</artifactId>
<version>1.0.0-SNAPSHOT</version>
</dependency>`

## 二：编辑配置文件

>coffee: 
>  ali-sms:
>   accessKeyId: ***
>   accessKeySecret: ***
>   signName: ***

## 三：启动类注入bean设置

> ConfigurableApplicationContext cx=SpringApplication.run(CoffeeUserApplication.class, args);
> SpringContextUtil.setApplicationContext(cx); 

## 四：使用
### 先new这个工具对象
> AliSmsUtil smsUtil=new AliSmsUtil();

### 就可以正常使用了
> smsUtil.sendSms("13159221056","SMS_154950909","{\"code\":\"1234\"}");


### PS：返回的是 ture/false  官方的返回数据会打印到info里

    
        
