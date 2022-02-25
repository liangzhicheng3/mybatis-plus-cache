package com.liangzhicheng.config.mybatis.annotation;

import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SecondCache {

    //from中mapper class对应的缓存更新时，需要更新当前注解标注mapper的缓存
    Class<?>[] from() default {};
    //当前注解标注mapper的缓存更新时，需要更新to中mapper class对应的缓存
    Class<?>[] to() default {};

}
