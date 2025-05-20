package com.example.portfolio.dto;

import com.example.portfolio.model.Project;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

    private Long id;

    @NotBlank(message = "Project title is required.")
    private String title;

    @NotBlank(message = "Project description is required.")
    private String description;

    @NotNull(message = "Project status is required.")
    private Project.Status status;

    @NotNull(message = "Client ID must not be null.")
    private Long clientId;

    @NotNull(message = "Builder ID must not be null.")
    private Long builderId;
}
