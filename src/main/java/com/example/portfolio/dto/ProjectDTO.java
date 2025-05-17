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

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Status is required")
    private Project.Status status;

    @NotNull(message = "Client ID is required")
    private Long clientId;

    @NotNull(message = "Builder ID is required")
    private Long builderId;
}