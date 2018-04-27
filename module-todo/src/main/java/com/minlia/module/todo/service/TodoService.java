package com.minlia.module.todo.service;


import com.github.pagehelper.PageInfo;
import com.minlia.module.todo.body.TodoCreateRequestBody;
import com.minlia.module.todo.body.TodoOperateRequestBody;
import com.minlia.module.todo.body.TodoQueryRequestBody;
import com.minlia.module.todo.body.TodoUpdateRequestBody;
import com.minlia.module.todo.entity.MyTodo;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by garen on 2018/4/27.
 */
public interface TodoService {

    MyTodo create(TodoCreateRequestBody requestBody);

    MyTodo update(TodoUpdateRequestBody requestBody);

    MyTodo operate(TodoOperateRequestBody body);

    void delete(String number);

    MyTodo queryById(Long id);

    MyTodo queryByNumber(String number);

    List<MyTodo> queryList(TodoQueryRequestBody requestBody);

    PageInfo<MyTodo> queryPage(TodoQueryRequestBody body, Pageable pageable);

}
