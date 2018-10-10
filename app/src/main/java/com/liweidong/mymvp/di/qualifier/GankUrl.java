package com.liweidong.mymvp.di.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Author:李炜东
 * Date:2018/9/3.
 * WX:17337162963
 * Email:18037891572@163.com
 */

/*
Documented 注解

Documented 注解表明这个注解应该被 javadoc工具记录. 默认情况下,javadoc是不包括注解的.
但如果声明注解时指定了 @Documented,则它会被 javadoc 之类的工具处理, 所以注解类型信息也会被包括在生成的文档中

 */
@Qualifier
@Documented
@Retention(RUNTIME)
public @interface GankUrl {

}
