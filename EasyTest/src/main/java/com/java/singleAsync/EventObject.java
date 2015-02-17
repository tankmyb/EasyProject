package com.java.singleAsync;
/**
 * 表示一个事件
 */
public class EventObject {
    final private String eventName; // 事件名
    final private Object source;  // 事件源
    final private Object args[];  // 可选参数

    public EventObject(String eventName, Object source) {
        this(eventName, source, null);
    }

    public EventObject(String eventName, Object source, Object[] args) {
        this.eventName = eventName;
        this.source = source;
        this.args = args;
    }

    public String getEventName() {
        return eventName;
    }

    public Object getSource() {
        return source;
    }

    public Object[] getArgs() {
        return args;
    }
}