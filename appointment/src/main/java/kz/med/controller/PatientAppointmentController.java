package kz.med.controller;

import kz.med.model.PatientAppointment;
import kz.med.service.impl.PatientAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/patient/appointment")
public class PatientAppointmentController {

    @Autowired
    PatientAppointmentService patientAppointmentService;

    @PostMapping()
    public PatientAppointment save(@RequestBody PatientAppointment patientAppointment) {
        return patientAppointmentService.saveAppoint(patientAppointment);
    }

    @PostMapping("/message")
    public PatientAppointment sendMessage(@RequestBody PatientAppointment patientAppointment) throws MessagingException {
        return patientAppointmentService.sendMessage(patientAppointment);
    }

}
