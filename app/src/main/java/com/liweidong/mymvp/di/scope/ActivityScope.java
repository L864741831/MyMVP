package com.liweidong.mymvp.di.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Author:李炜东
 * Date:2018/9/3.
 * WX:17337162963
 * Email:18037891572@163.com
 */

//作用域
@Scope
@Retention(RUNTIME)
public @interface ActivityScope {

}
