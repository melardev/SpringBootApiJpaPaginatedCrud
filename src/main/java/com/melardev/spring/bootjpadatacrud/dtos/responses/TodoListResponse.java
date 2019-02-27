package com.melardev.spring.bootjpadatacrud.dtos.responses;

import java.util.Collection;

public class TodoListResponse extends SuccessResponse{
    private final PageMeta pageMeta;
    private final Collection<TodoSummaryDto> todos;

    public TodoListResponse(PageMeta pageMeta, Collection<TodoSummaryDto> todos) {
        this.todos = todos;
        this.pageMeta = pageMeta;
    }

    public PageMeta getPageMeta() {
        return pageMeta;
    }

    public Collection<TodoSummaryDto> getTodos() {
        return todos;
    }
}
