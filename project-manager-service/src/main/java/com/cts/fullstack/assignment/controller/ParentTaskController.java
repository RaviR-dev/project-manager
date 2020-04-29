package com.cts.fullstack.assignment.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.fullstack.assignment.dto.ParentTaskDto;
import com.cts.fullstack.assignment.entities.ParentTask;
import com.cts.fullstack.assignment.service.ParentTaskService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/projectmanager/parenttask")
public class ParentTaskController {
    private final ParentTaskService parentTaskService;

    public ParentTaskController(ParentTaskService parentTaskService) {
        this.parentTaskService = parentTaskService;
    }

    @GetMapping(path="/list")
    public ResponseEntity<Object> getAllParentTasks(){
        List<ParentTask> parentTaskList = parentTaskService.getAllParentTasks();
        List<ParentTaskDto> parentTaskDtoList = parentTaskList.stream().map(parentTask -> parentTaskService.getParentTaskDtoFromParentTask(parentTask)).collect(Collectors.toList());
        return new ResponseEntity<>(parentTaskDtoList, HttpStatus.OK);
    }

    @PostMapping(path="/add")
    public ResponseEntity<Object> addTask(@RequestBody ParentTaskDto parentTaskDto) {
        ParentTask parentTask = parentTaskService.getParentTaskFromParentTaskDto(parentTaskDto);
        parentTaskService.addParentTask(parentTask);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}