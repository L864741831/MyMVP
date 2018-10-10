package com.liweidong.mymvp.model.event;

/**
 * Author:李炜东
 * Date:2018/9/6.
 * WX:17337162963
 * Email:18037891572@163.com
 */

public class EventBusStickyMsg {
    // 类的结构是自定义的,我这类添加了一个 String类型的 name 字段 方便测试.
    public String name;

    public EventBusStickyMsg(String name) {
        this.name = name;
    }
}
