package com.hospital.controller;

import com.hospital.dto.QueueRequestDTO;
import com.hospital.service.RabbitAdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/queues")
public class RabbitAdminController {

    private final RabbitAdminService adminService;

    public RabbitAdminController(RabbitAdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<String> createQueue(@Valid @RequestBody QueueRequestDTO request) {
        adminService.createQueue(request.queueName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Cola '" + request.queueName() + "' creada exitosamente.");
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteQueue(@PathVariable String name) {
        adminService.deleteQueue(name);
        return ResponseEntity.ok("Cola '" + name + "' eliminada exitosamente.");
    }
}