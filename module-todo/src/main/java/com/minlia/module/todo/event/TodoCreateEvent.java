package com.minlia.module.todo.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.module.todo.entity.MyTodo;
import org.springframework.context.ApplicationEvent;

/**
 * Created by garen on 2018/4/15.
 */
public class TodoCreateEvent extends ApplicationEvent {

    public TodoCreateEvent(Object source) {
        super(source);
    }

    public static void onCreate(MyTodo todo) {
        ContextHolder.getContext().publishEvent(new TodoCreateEvent(todo));
    }

}
