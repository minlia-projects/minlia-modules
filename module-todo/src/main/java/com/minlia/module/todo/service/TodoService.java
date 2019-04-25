package com.minlia.module.todo.service;


import com.github.pagehelper.PageInfo;
import com.minlia.module.todo.bean.to.TodoCTO;
import com.minlia.module.todo.bean.to.TodoOperateTO;
import com.minlia.module.todo.bean.qo.TodoQO;
import com.minlia.module.todo.bean.to.TodoUTO;
import com.minlia.module.todo.bean.domain.MyTodo;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by garen on 2018/4/27.
 */
public interface TodoService {

    MyTodo create(TodoCTO cro);

    MyTodo update(TodoUTO uto);

    MyTodo operate(TodoOperateTO to);

    void delete(String number);

    MyTodo queryByNumber(String number);

    long count(TodoQO qro);

    List<MyTodo> queryList(TodoQO qro);

    PageInfo<MyTodo> queryPage(TodoQO qro, Pageable pageable);

}
