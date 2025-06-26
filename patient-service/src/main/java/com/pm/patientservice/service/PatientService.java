package com.pm.patientservice.service;
import java.util.List;

import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.model.Patient;

@Service
public class PatientService
{
    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository)
    {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients = patientRepository.findAll();

        return patients.stream().map(patient -> PatientMapper.toDTO(patient)).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO)
    {

        if(patientRepository.existsByEmail(patientRequestDTO.getEmail()))
        {
            throw new EmailAlreadyExistsException("A patient with this email "+ "already exists "+ patientRequestDTO.getEmail());
        }
        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));

        return PatientMapper.toDTO(newPatient);
    }
}
