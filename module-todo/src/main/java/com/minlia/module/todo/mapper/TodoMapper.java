package com.minlia.module.todo.mapper;

import com.minlia.module.todo.bean.qo.TodoQO;
import com.minlia.module.todo.bean.domain.MyTodo;

import java.util.List;

/**
 * Created by garen on 2018/4/27.
 */
public interface TodoMapper {

    int create(MyTodo todo);

    int update(MyTodo todo);

    int delete(Long id);

    MyTodo queryByNumber(String number);

    List<MyTodo> queryList(TodoQO qo);

    long count(TodoQO qo);

}
