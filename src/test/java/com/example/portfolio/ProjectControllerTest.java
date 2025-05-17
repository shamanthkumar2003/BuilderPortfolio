package com.example.portfolio;

import com.example.portfolio.controller.ProjectController;
import com.example.portfolio.dto.ProjectDTO;
import com.example.portfolio.model.Project.Status;
import com.example.portfolio.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @Autowired
    private ObjectMapper objectMapper; // for JSON serialization/deserialization

    private ProjectDTO sampleProjectDTO;

    @BeforeEach
    void setup() {
        sampleProjectDTO = new ProjectDTO(
                1L,
                "Sample Project",
                "Project description",
                Status.IN_PROGRESS,
                10L,
                20L
        );
    }

    @Test
    void testCreateProject() throws Exception {
        Mockito.when(projectService.createProject(any(ProjectDTO.class))).thenReturn(sampleProjectDTO);

        mockMvc.perform(post("/projects/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleProjectDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(sampleProjectDTO.getId()))
                .andExpect(jsonPath("$.title").value(sampleProjectDTO.getTitle()))
                .andExpect(jsonPath("$.description").value(sampleProjectDTO.getDescription()))
                .andExpect(jsonPath("$.status").value(sampleProjectDTO.getStatus().toString()))
                .andExpect(jsonPath("$.clientId").value(sampleProjectDTO.getClientId()))
                .andExpect(jsonPath("$.builderId").value(sampleProjectDTO.getBuilderId()));
    }

    @Test
    void testGetAllProjects() throws Exception {
        List<ProjectDTO> projects = Arrays.asList(sampleProjectDTO);
        Mockito.when(projectService.getAllProjects()).thenReturn(projects);

        mockMvc.perform(get("/projects")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(sampleProjectDTO.getId()))
                .andExpect(jsonPath("$[0].title").value(sampleProjectDTO.getTitle()));
    }

    @Test
    void testGetProjectById() throws Exception {
        Mockito.when(projectService.getProjectById(1L)).thenReturn(sampleProjectDTO);

        mockMvc.perform(get("/projects/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleProjectDTO.getId()))
                .andExpect(jsonPath("$.title").value(sampleProjectDTO.getTitle()));
    }

    @Test
    void testUpdateProject() throws Exception {
        ProjectDTO updatedProject = new ProjectDTO(
                1L,
                "Updated Project",
                "Updated description",
                Status.COMPLETED,
                11L,
                21L
        );

        Mockito.when(projectService.updateProject(eq(1L), any(ProjectDTO.class))).thenReturn(updatedProject);

        mockMvc.perform(put("/projects/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProject)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(updatedProject.getTitle()))
                .andExpect(jsonPath("$.status").value(updatedProject.getStatus().toString()));
    }

    @Test
    void testDeleteProject() throws Exception {
        Mockito.doNothing().when(projectService).deleteProject(1L);

        mockMvc.perform(delete("/projects/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
