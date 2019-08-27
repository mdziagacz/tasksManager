package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTestSuite {

    @InjectMocks
    DbService dbService;
    @Mock
    TaskRepository taskRepository;

    @Test
    public void testGetAllTasks(){
        //Given
        Task task = new Task(1L, "test", "test");
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        when(taskRepository.findAll()).thenReturn(tasks);

        //When
        List<Task> expectedTask = dbService.getAllTasks();

        //Then
        assertEquals(tasks, expectedTask);
    }

    @Test
    public void testGetTask(){
        //Given
        Optional<Task> task = Optional.of(new Task(1L, "test", "test"));
        when(taskRepository.findById(1L)).thenReturn(task);

        //When
        Optional<Task> foundTask = dbService.getTask(1L);

        //Then
        assertEquals(task, foundTask);
    }

    @Test
    public void testSaveTask(){
        //Given
        Task task = new Task(1L, "test", "test");
        when(taskRepository.save(task)).thenReturn(task);

        //When
        Task savedTask = taskRepository.save(task);

        //Then
        assertEquals(task, savedTask);
    }

    @Test
    public void testDeleteTask(){
        //Given

        //When
        taskRepository.deleteById(1L);

        //Then
        verify(taskRepository, times(1)).deleteById(1L);
    }
}
