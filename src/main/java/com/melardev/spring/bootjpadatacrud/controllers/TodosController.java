package com.melardev.spring.bootjpadatacrud.controllers;

import com.melardev.spring.bootjpadatacrud.dtos.responses.*;
import com.melardev.spring.bootjpadatacrud.entities.Todo;
import com.melardev.spring.bootjpadatacrud.repositories.TodosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/todos")
public class TodosController {


    @Autowired
    private TodosRepository todosRepository;

    @GetMapping
    public AppResponse index(@RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "page_size", defaultValue = "10") int pageSize,
                             HttpServletRequest request) {

        Pageable pageable = getPageable(page, pageSize);
        Page<Todo> todos = this.todosRepository.findAll(pageable);
        List<TodoSummaryDto> todoDtos = buildTodoDtos(todos);
        return new TodoListResponse(PageMeta.build(todos, request.getRequestURI()), todoDtos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AppResponse> get(@PathVariable("id") Long id) {
        Optional<Todo> todo = this.todosRepository.findById(id);
        /*
        if (todo.isPresent())
            return new ResponseEntity<>(new TodoDetailsResponse(todo.get()), HttpStatus.OK);
        else
            return new ResponseEntity<>(new ErrorResponse("Todo not found"), HttpStatus.NOT_FOUND);
        */
        return todo.<ResponseEntity<AppResponse>>map(todo1 -> new ResponseEntity<>(new TodoDetailsResponse(todo1), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new ErrorResponse("Todo not found"), HttpStatus.NOT_FOUND));
    }

    @GetMapping("/pending")
    public AppResponse getNotCompletedTodos(@RequestParam(value = "page", defaultValue = "1") int page,
                                            @RequestParam(value = "page_size", defaultValue = "10") int pageSize,
                                            HttpServletRequest request) {

        Pageable pageable = getPageable(page, pageSize);
        Page<Todo> todos = this.todosRepository.findByCompletedFalse(pageable);
        return new TodoListResponse(PageMeta.build(todos, request.getRequestURI()), buildTodoDtos(todos));
    }

    @GetMapping("/completed")
    public AppResponse getCompletedTodos(@RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "page_size", defaultValue = "10") int pageSize,
                                         HttpServletRequest request) {

        Page<Todo> todosPage = todosRepository.findByCompletedIsTrue(getPageable(page, pageSize));
        return new TodoListResponse(PageMeta.build(todosPage, request.getRequestURI()), buildTodoDtos(todosPage));
    }

    @PostMapping
    public ResponseEntity<AppResponse> create(@Valid @RequestBody Todo todo) {
        return new ResponseEntity<>(new TodoDetailsResponse(todosRepository.save(todo), "Todo created successfully"), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppResponse> update(@PathVariable("id") Long id,
                                              @RequestBody Todo todoInput) {
        Optional<Todo> optionalTodo = todosRepository.findById(id);
        if (optionalTodo.isPresent()) {
            Todo todo = optionalTodo.get();
            todo.setTitle(todoInput.getTitle());
            todo.setDescription(todoInput.getDescription());
            todo.setCompleted(todoInput.isCompleted());
            return ResponseEntity.ok(new TodoDetailsResponse(todosRepository.save(optionalTodo.get()), "Todo updated successfully"));
        } else {
            return new ResponseEntity<>(new ErrorResponse("Todo does not exist"), HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<AppResponse> delete(@PathVariable("id") Long id) {
        Optional<Todo> todo = todosRepository.findById(id);
        if (todo.isPresent()) {
            todosRepository.delete(todo.get());
            return ResponseEntity.ok(new SuccessResponse("You have successfully deleted the article"));
        } else {
            return new ResponseEntity<>(new ErrorResponse("This todo does not exist"), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public AppResponse deleteAll() {
        todosRepository.deleteAll();
        return new SuccessResponse("Deleted all todos successfully");
    }

    private Pageable getPageable(int page, int pageSize) {
        if (page <= 0)
            page = 1;

        if (pageSize <= 0)
            pageSize = 5;

        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.Direction.DESC, "createdAt");
        return pageRequest;
    }

    private List<TodoSummaryDto> buildTodoDtos(Page<Todo> todos) {
        List<TodoSummaryDto> todoDtos = todos.getContent().stream().map(TodoSummaryDto::build).collect(Collectors.toList());
        return todoDtos;
    }
}
