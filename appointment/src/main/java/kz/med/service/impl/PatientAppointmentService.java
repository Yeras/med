package kz.med.service.impl;

import kz.med.model.PatientAppointment;
import kz.med.repository.PatientAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class PatientAppointmentService {

    @Autowired
    PatientAppointmentRepository patientAppointmentRepository;
    @Autowired
    PatientAppointmentScheduleService patientAppointmentScheduleService;

    @Autowired
    JavaMailSender javaMailSender;


    public PatientAppointment saveAppoint(PatientAppointment patientAppointment) {
        System.out.println("Substr: " + patientAppointment.getStartSendMessage().toString().substring(0, 15));
        System.out.println("Full: " + patientAppointment.getStartSendMessage().toString().substring(11));

        patientAppointment.setStartSendMessage(LocalDateTime.of(                      // Represent a date with time-of-day, but lacking in zone/offset so NOT a moment, NOT a point on the timeline.
                LocalDate.parse(patientAppointment.getStartSendMessage().toString().substring(0, 10)),  // Parse the date-only value.
                LocalTime.parse("05:00")         // Parse the time-of-day.
        ));

        PatientAppointment saveAppointment = patientAppointmentRepository.save(patientAppointment);

        patientAppointmentScheduleService.createSchedule(saveAppointment);

        return saveAppointment;
    }

    public PatientAppointment sendMessage(PatientAppointment patientAppointment) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setTo(patientAppointment.getPatientEmail());
        messageHelper.setSubject("Check Appointment message");

        String messageText = patientAppointment.getAppointmentList();

        messageHelper.setText(messageText, true);

        javaMailSender.send(mimeMessage);

        return patientAppointment;
    }

}
