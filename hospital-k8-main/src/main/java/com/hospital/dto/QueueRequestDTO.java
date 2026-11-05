package com.hospital.dto;

import jakarta.validation.constraints.NotBlank;

public record QueueRequestDTO(
    @NotBlank(message = "El nombre de la cola no puede estar vacío") 
    String queueName
) {}