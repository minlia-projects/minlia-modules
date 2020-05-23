package com.minlia.module.todo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.bible.entity.BibleItem;
import com.minlia.module.bible.ro.BibleItemQRO;
import com.minlia.module.bible.service.BibleItemService;
import com.minlia.module.common.util.NumberGenerator;
import com.minlia.module.todo.bean.domain.MyTodo;
import com.minlia.module.todo.bean.qo.TodoQO;
import com.minlia.module.todo.bean.to.TodoCTO;
import com.minlia.module.todo.bean.to.TodoOperateTO;
import com.minlia.module.todo.bean.to.TodoUTO;
import com.minlia.module.todo.constant.TodoCode;
import com.minlia.module.todo.enumeration.TodoStatus;
import com.minlia.module.todo.event.TodoCreateEvent;
import com.minlia.module.todo.mapper.TodoMapper;
import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private TodoMapper todoMapper;

    @Autowired
    private BibleItemService bibleItemService;

    private final static String BIBLE_TODO_TYPE = "TODO_TYPE";

    @Override
    @Transactional
    public MyTodo create(TodoCTO cto) {
        BibleItem bibleItem = bibleItemService.queryOne(BibleItemQRO.builder().parentCode(BIBLE_TODO_TYPE).code(cto.getType()).build());
        ApiAssert.notNull(bibleItem, TodoCode.Message.TYPE_NOT_EXISTS, cto.getType());

        MyTodo todo = mapper.map(cto, MyTodo.class);
        todo.setNumber(NumberGenerator.generatorByYMDHMSS("TD",1));
        todo.setStatus(TodoStatus.UNDONE);
        todoMapper.create(todo);

        TodoCreateEvent.onCreate(todo);
        return todo;
    }

    @Override
    @Transactional
    public MyTodo update(TodoUTO uto) {
        MyTodo todo = this.queryByNumberAndNotNull(uto.getNumber());
        BeanUtils.copyProperties(uto, todo);
        todoMapper.update(todo);
        return todo;
    }

    @Override
    public MyTodo operate(TodoOperateTO to) {
        MyTodo todo = this.queryByNumberAndNotNull(to.getNumber());
        switch (to.getOperate()) {
            case PEND:
                todo.setStatus(TodoStatus.PENDING);
                break;
            case FINISH:
                todo.setStatus(TodoStatus.FINISHED);
                break;
            case CANCEL:
                todo.setStatus(TodoStatus.CANCELED);
                break;
            default:
                break;
        }
        todo.setNotes(to.getNotes());
        todoMapper.update(todo);
        return todo;
    }

    @Override
    @Transactional
    public void delete(String number) {
        MyTodo todo = this.queryByNumberAndNotNull(number);
        todoMapper.delete(todo.getId());
    }

    @Override
    public MyTodo queryByNumber(String number) {
        return todoMapper.queryByNumber(number);
    }

    @Override
    public long count(TodoQO qro) {
        return todoMapper.count(qro);
    }

    private MyTodo queryByNumberAndNotNull(String number) {
        MyTodo todo = todoMapper.queryByNumber(number);
        ApiAssert.notNull(todo, SystemCode.Message.DATA_NOT_EXISTS);
        return todo;
    }

    @Override
    public List<MyTodo> queryList(TodoQO qro) {
        return todoMapper.queryList(qro);
    }

    @Override
    public PageInfo<MyTodo> queryPage(TodoQO qro, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize(), qro.getOrderBy()).doSelectPageInfo(() -> todoMapper.queryList(qro));
    }

}
