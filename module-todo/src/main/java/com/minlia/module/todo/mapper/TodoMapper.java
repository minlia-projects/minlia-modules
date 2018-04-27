package com.minlia.module.todo.mapper;

import com.minlia.module.todo.body.TodoQueryRequestBody;
import com.minlia.module.todo.entity.MyTodo;

import java.util.List;

/**
 * Created by garen on 2018/4/27.
 */
public interface TodoMapper {

    int create(MyTodo todo);

    int update(MyTodo todo);

    int delete(Long id);

    MyTodo queryById(Long id);

    MyTodo queryByNumber(String number);

    List<MyTodo> queryList(TodoQueryRequestBody body);

}
