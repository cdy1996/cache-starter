# cache-starter

## 介绍

该项目的主要目的是为了创建符合自己要求的缓存注解,并且基于springboot的自动配置,来快速引入到需要的项目中.

## 内容

该项目主要包括两个注解

com.cdy.cachestarter.configuration.CachePut

将注解放在对应的查询方法上,给定key以及失效时间,aop会自动将值放入redis中,然后在第二次调用时就可以读取缓存中的值

com.cdy.cachestarter.configuration.CacheDel

将注解放在对应的查询方法上,给定key,则自动删除对应的缓存

## 使用

首先项目依赖redis-starter的依赖(https://github.com/cdy1996/redis-starter),根据redis-starter项目的配置完后在进行如下配置

在springbootApplication 的main类上,加上@EnableCache注解即可

## 设计

该项目的想法主要来自spring cache,并且其中的key也是用到了spel

与spring cache的主要区别在于,spring cache将三个注解使用放在一个环绕通知中,而我是分为两个aspect的切面逻辑,主要是不想再一个通知中放入过多的逻辑,造成逻辑混乱.

