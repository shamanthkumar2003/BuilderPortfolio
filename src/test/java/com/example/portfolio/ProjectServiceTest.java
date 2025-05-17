package com.example.portfolio;

import com.example.portfolio.dto.ProjectDTO;
import com.example.portfolio.exception.ResourceNotFoundException;
import com.example.portfolio.model.Project;
import com.example.portfolio.repository.ProjectRepository;
import com.example.portfolio.service.impl.ProjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private Project project1;
    private Project project2;
    private ProjectDTO projectDTO;

    @BeforeEach
    public void setup() {
        project1 = new Project(
                1L,
                "Modern House",
                "A modern house with 3 bedrooms",
                Project.Status.UPCOMING,
                1L,
                2L
        );

        project2 = new Project(
                2L,
                "Office Building",
                "A 5-story office building in downtown",
                Project.Status.IN_PROGRESS,
                3L,
                2L
        );

        projectDTO = new ProjectDTO();
        projectDTO.setTitle("Modern House");
        projectDTO.setDescription("A modern house with 3 bedrooms");
        projectDTO.setStatus(Project.Status.UPCOMING);
        projectDTO.setClientId(1L);
        projectDTO.setBuilderId(2L);
    }

    @Test
    public void createProject_ShouldReturnProjectDTO() {
        when(projectRepository.save(any(Project.class))).thenReturn(project1);

        ProjectDTO result = projectService.createProject(projectDTO);

        assertNotNull(result);
        assertEquals(project1.getTitle(), result.getTitle());
        assertEquals(project1.getDescription(), result.getDescription());
        assertEquals(project1.getStatus(), result.getStatus());
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    public void getAllProjects_ShouldReturnAllProjects() {
        when(projectRepository.findAll()).thenReturn(Arrays.asList(project1, project2));

        List<ProjectDTO> result = projectService.getAllProjects();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    public void getProjectById_WithValidId_ShouldReturnProject() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project1));

        ProjectDTO result = projectService.getProjectById(1L);

        assertNotNull(result);
        assertEquals(project1.getTitle(), result.getTitle());
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    public void getProjectById_WithInvalidId_ShouldThrowException() {
        when(projectRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            projectService.getProjectById(99L);
        });

        verify(projectRepository, times(1)).findById(99L);
    }

    @Test
    public void updateProject_WithValidId_ShouldReturnUpdatedProject() {
        ProjectDTO updateDTO = new ProjectDTO();
        updateDTO.setTitle("Modern House Updated");
        updateDTO.setDescription("An updated modern house with 4 bedrooms");
        updateDTO.setStatus(Project.Status.IN_PROGRESS);
        updateDTO.setClientId(1L);
        updateDTO.setBuilderId(2L);

        Project updatedProject = new Project(
                1L,
                "Modern House Updated",
                "An updated modern house with 4 bedrooms",
                Project.Status.IN_PROGRESS,
                1L,
                2L
        );

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project1));
        when(projectRepository.save(any(Project.class))).thenReturn(updatedProject);

        ProjectDTO result = projectService.updateProject(1L, updateDTO);

        assertNotNull(result);
        assertEquals(updateDTO.getTitle(), result.getTitle());
        assertEquals(updateDTO.getDescription(), result.getDescription());
        assertEquals(updateDTO.getStatus(), result.getStatus());
        verify(projectRepository, times(1)).findById(1L);
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    public void deleteProject_WithValidId_ShouldDeleteProject() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project1));
        doNothing().when(projectRepository).delete(project1);

        projectService.deleteProject(1L);

        verify(projectRepository, times(1)).findById(1L);
        verify(projectRepository, times(1)).delete(project1);
    }
}