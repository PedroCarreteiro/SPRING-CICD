package com.devops.todocicd;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc; // Simula o navegador/postman

    //MockBean deprecated
    @MockBean
    private TaskRepository repository;

    //Return to do list
    @Test
    public void deveRetornarTodasTarefas() throws Exception {

        //Arrange
        Task task1 = new Task();
        task1.setId(1L);
        task1.setDescription("Learn Docker");
        task1.setCompleted(true);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setDescription("Learn GitHub Actions");
        task2.setCompleted(false);

        List<Task> tarefas = Arrays.asList(task1, task2);

        Mockito.when(repository.findAll()).thenReturn(tarefas);

        //Act and Assert
        mockMvc.perform(get("/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 200 status
                .andExpect(jsonPath("$", hasSize(2))) // 2 itens returned
                .andExpect(jsonPath("$[0].description", is("Learn Docker"))); // confirm content
    }

    //Create new task
    @Test
    public void deveCriarTarefa() throws Exception {

        Task novaTask = new Task();
        novaTask.setDescription("New DevOps task");
        novaTask.setCompleted(false);

        Task taskSalva = new Task();
        taskSalva.setId(1L); //databse generate the id
        taskSalva.setDescription("New DevOps task");
        taskSalva.setCompleted(false);

        //Return saved task after save
        Mockito.when(repository.save(Mockito.any(Task.class))).thenReturn(taskSalva);

        mockMvc.perform(post("/tasks") // Simula um POST /tasks
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"New DevOps task\", \"completed\": false}")) // Corpo do JSON
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("New DevOps task")));
    }
}