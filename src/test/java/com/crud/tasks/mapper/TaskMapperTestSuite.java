package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class TaskMapperTestSuite {

    @InjectMocks
    TaskMapper taskMapper;

    @Test
    public void testMapToTask(){
        //Given
        TaskDto taskDto = new TaskDto(5L, "test_task", "test");

        //When
        Task task = taskMapper.mapToTask(taskDto);

        //Then
        assertNotNull(task);
        assertEquals(5L, task.getId(), 0.001);
        assertEquals("test_task", task.getTitle());
        assertEquals("test", task.getContent());
    }

    @Test
    public void testMapToTaskDto(){
        //Given
        Task task = new Task(5L, "test_task", "test");

        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        //Then
        assertNotNull(taskDto);
        assertEquals(5L, taskDto.getId(), 0.001);
        assertEquals("test_task", taskDto.getTitle());
        assertEquals("test", taskDto.getContent());
    }

    @Test
    public void testMapToTaskDtoList(){
        //Given
        Task task1 = new Task(1L, "test1", "test1");
        Task task2 = new Task(2L, "test2", "test2");
        List<Task> taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);

        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);

        //Then
        assertNotNull(taskDtoList);
        assertEquals(2, taskDtoList.size());
        long i = 1L;
        for (TaskDto taskDto: taskDtoList){
            assertEquals(i, taskDto.getId(), 0.001);
            assertEquals("test" + i, taskDto.getTitle());
            assertEquals("test" + i, taskDto.getContent());
            i++;
        }
    }
}
