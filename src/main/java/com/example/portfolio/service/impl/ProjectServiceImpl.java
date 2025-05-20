package com.example.portfolio.service.impl;

import com.example.portfolio.dto.ProjectDTO;
import com.example.portfolio.exception.ResourceNotFoundException;
import com.example.portfolio.model.Project;
import com.example.portfolio.model.User;
import com.example.portfolio.repository.ProjectRepository;
import com.example.portfolio.repository.UserRepository;
import com.example.portfolio.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        Project project = convertToEntity(projectDTO);
        Project savedProject = projectRepository.save(project);
        return convertToDTO(savedProject);
    }

    @Override
    public List<ProjectDTO> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        return convertToDTO(project);
    }

    @Override
    public ProjectDTO updateProject(Long id, ProjectDTO projectDTO) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));

        existingProject.setTitle(projectDTO.getTitle());
        existingProject.setDescription(projectDTO.getDescription());
        existingProject.setStatus(projectDTO.getStatus());
        User client = userRepository.findById(projectDTO.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with ID: " + projectDTO.getClientId()));

        User builder = userRepository.findById(projectDTO.getBuilderId())
                .orElseThrow(() -> new ResourceNotFoundException("Builder not found with ID: " + projectDTO.getBuilderId()));

        existingProject.setClient(client);
        existingProject.setBuilder(builder);


        Project updatedProject = projectRepository.save(existingProject);
        return convertToDTO(updatedProject);
    }

    @Override
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        projectRepository.delete(project);
    }

    private Project convertToEntity(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setId(projectDTO.getId());
        project.setTitle(projectDTO.getTitle());
        project.setDescription(projectDTO.getDescription());
        project.setStatus(projectDTO.getStatus());

        User client = userRepository.findById(projectDTO.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with ID: " + projectDTO.getClientId()));
        project.setClient(client);

        User builder = userRepository.findById(projectDTO.getBuilderId())
                .orElseThrow(() -> new ResourceNotFoundException("Builder not found with ID: " + projectDTO.getBuilderId()));
        project.setBuilder(builder);

        return project;
    }

    private ProjectDTO convertToDTO(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setTitle(project.getTitle());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setStatus(project.getStatus());

        if (project.getClient() != null) {
            projectDTO.setClientId(project.getClient().getId());
        }

        if (project.getBuilder() != null) {
            projectDTO.setBuilderId(project.getBuilder().getId());
        }

        return projectDTO;
    }
}