package com.hospital.service;

import com.hospital.model.Patient;
import com.hospital.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private static final Logger log = LoggerFactory.getLogger(PatientService.class);

    private final PatientRepository patientRepository;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchanges.hospital}")
    private String exchange;

    @Value("${rabbitmq.routing-keys.notifications}")
    private String routingKey;

    public PatientService(PatientRepository patientRepository, RabbitTemplate rabbitTemplate) {
        this.patientRepository = patientRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    // Método restaurado para el controlador
    @Transactional(readOnly = true)
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    // Método restaurado para el controlador
    @Transactional(readOnly = true)
    public Optional<Patient> findById(Long id) {
        return patientRepository.findById(id);
    }

    // Renombrado de savePatient a save para coincidir con tu controlador
    @Transactional
    public Patient save(Patient patient) {
        Patient savedPatient = patientRepository.save(patient);
        log.info("Paciente guardado en la base de datos con ID: {}", savedPatient.getId());

        try {
            // Dejado con "FALLA" para que puedas probar la Dead Letter Queue (DLQ)
            String mensaje = "FALLA - Simulando error para el paciente " + savedPatient.getEmail();
            
            rabbitTemplate.convertAndSend(exchange, routingKey, mensaje);
            log.info("Evento publicado en el exchange '{}' exitosamente.", exchange);
        } catch (Exception e) {
            log.error("Error al publicar en RabbitMQ: {}", e.getMessage());
        }

        return savedPatient;
    }

    // Método restaurado por si tienes un endpoint DELETE en tu controlador
    @Transactional
    public void deleteById(Long id) {
        patientRepository.deleteById(id);
    }
}