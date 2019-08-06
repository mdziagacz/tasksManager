package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTestSuite {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    DbService dbService;
    @MockBean
    TaskMapper taskMapper;

    @Test
    public void shouldGetAllTasks() throws Exception {
        //Given
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(new TaskDto(1L, "test", "test_task"));
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1L, "test", "test_task"));

        when(taskMapper.mapToTaskDtoList(taskList)).thenReturn(taskDtoList);
        when(dbService.getAllTasks()).thenReturn(taskList);

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("test")))
                .andExpect(jsonPath("$[0].content", is("test_task")));
    }

    @Test
    public void shouldGetTaskById() throws Exception {
        //Given
        Optional<Task> task = Optional.of(new Task(1L, "test", "test_task"));
        TaskDto taskDto = new TaskDto(1L, "test", "test_task");

        when(dbService.getTask(1L)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task.get())).thenReturn(taskDto);

        //When & Then
        mockMvc.perform(get("/v1/task/getTask/?taskId=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("test")))
                .andExpect(jsonPath("$.content", is("test_task")));
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        //Given
        Task task = new Task(1L, "test", "test_task");
        TaskDto taskDto = new TaskDto(1L, "test", "test_task");

        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        // when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        // why it doesn't work?
        // throws: java.lang.AssertionError: No value at JSON path "$.id"

        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);
        when(dbService.saveTask(task)).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("test")))
                .andExpect(jsonPath("$.content", is("test_task")));
    }

    @Test
    public void shouldCreateAndDeleteTask() {
/*
        methods createTask() and deleteTask() doesn't return any object
        how to test them? no data to check
*/
    }
}
