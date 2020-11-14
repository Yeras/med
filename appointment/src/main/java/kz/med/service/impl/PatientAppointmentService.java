package kz.med.service.impl;

import kz.med.model.PatientAppointment;
import kz.med.repository.PatientAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientAppointmentService {

    @Autowired
    PatientAppointmentRepository patientAppointmentRepository;


    public PatientAppointment saveAppoint(PatientAppointment patientAppointment) {
        return patientAppointmentRepository.save(patientAppointment);
    }


}
