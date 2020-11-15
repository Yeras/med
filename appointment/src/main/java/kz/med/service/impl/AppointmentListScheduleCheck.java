package kz.med.service.impl;

import kz.med.model.PatientAppointment;
import kz.med.model.PatientAppointmentSchedule;
import kz.med.model.PatientAppointmentScheduleCheck;
import kz.med.repository.PatientAppointmentRepository;
import kz.med.repository.PatientAppointmentScheduleCheckRepository;
import kz.med.repository.PatientAppointmentScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Component
public class AppointmentListScheduleCheck {

    @Autowired
    PatientAppointmentRepository appointmentRepository;
    @Autowired
    PatientAppointmentScheduleRepository scheduleRepository;
    @Autowired
    PatientAppointmentScheduleCheckRepository checkRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Scheduled(cron = "0 01 2 ? * *")
    public void schedulerAboutAppointCheck() {
        System.out.println("Scheduled run");
        // First Day for finding
        LocalDateTime firstDay =
                LocalDateTime.of(
                        LocalDate.parse(new Timestamp(System.currentTimeMillis()).toString().substring(0, 10)),  // Parse the date-only value.
                        LocalTime.parse("01:00:00")         // Parse the time-of-day.
                );

        // Second Day for finding
        LocalDateTime secondDay =
                LocalDateTime.of(
                        LocalDate.parse(new Timestamp(System.currentTimeMillis()).toString().substring(0, 10)).plusDays(1),  // Parse the date-only value.
                        LocalTime.parse("01:00:00")         // Parse the time-of-day.
                );

        // List of appointment which start between to days for finding
        List<PatientAppointment> patientAppointmentList = appointmentRepository.findAllByStartSendMessageLessThanEqualAndStartSendMessageGreaterThanEqual(secondDay, firstDay);

        for (PatientAppointment patientAppointment : patientAppointmentList) {
            // Schedule list
            List<PatientAppointmentSchedule> patientAppointmentScheduleList = scheduleRepository.findAllByAppointmentId(patientAppointment.getId());

            // Add schedule List
            for (PatientAppointmentSchedule patientAppointmentSchedule : patientAppointmentScheduleList) {
                PatientAppointmentScheduleCheck patientAppointmentScheduleCheck = new PatientAppointmentScheduleCheck();
                patientAppointmentScheduleCheck.setAppointmentScheduleId(patientAppointmentSchedule.getId());

                checkRepository.save(patientAppointmentScheduleCheck);
            }
        }
    }

    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void sendMessageForPatient() throws MessagingException {
        System.out.println("Scheduled per hour");
        List<PatientAppointmentScheduleCheck> patientAppointmentScheduleCheckList = checkRepository.findAll();

        for (PatientAppointmentScheduleCheck patientAppointmentScheduleCheck : patientAppointmentScheduleCheckList) {
            Optional<PatientAppointmentSchedule> patientAppointmentSchedule = scheduleRepository.findById(patientAppointmentScheduleCheck.getAppointmentScheduleId());

            if (patientAppointmentSchedule.get().getSendDate().isAfter(LocalDateTime.of(
                    LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
                    LocalTime.parse(new Timestamp(System.currentTimeMillis()).toString().substring(11)).minusMinutes(40)))
                    &&
                    patientAppointmentSchedule.get().getSendDate().isBefore(LocalDateTime.of(
                            LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
                            LocalTime.parse(new Timestamp(System.currentTimeMillis()).toString().substring(11)).plusMinutes(40)))
            ) {
                System.out.println("2Send Date: " + patientAppointmentSchedule.get().getSendDate());
                System.out.println("2First: " + patientAppointmentSchedule.get().getSendDate().isAfter(LocalDateTime.of(
                        LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
                        LocalTime.parse(new Timestamp(System.currentTimeMillis()).toString().substring(11)).minusMinutes(40))));
                System.out.println("2Second: " + patientAppointmentSchedule.get().getSendDate().isBefore(LocalDateTime.of(
                        LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
                        LocalTime.parse(new Timestamp(System.currentTimeMillis()).toString().substring(11)).plusMinutes(40))));
                Optional<PatientAppointment> patientAppointment = appointmentRepository.findById(patientAppointmentSchedule.get().getAppointmentId());

                MimeMessage mimeMessage = javaMailSender.createMimeMessage();

                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
                messageHelper.setTo(patientAppointment.get().getPatientEmail());
                messageHelper.setSubject("Check Appointment message");

                String messageText = patientAppointment.get().getAppointmentList();

                messageHelper.setText(messageText, true);

                javaMailSender.send(mimeMessage);

                checkRepository.deleteById(patientAppointmentScheduleCheck.getId());
            }
//            else if (patientAppointmentSchedule.get().getSendDate().isAfter(LocalDateTime.of(
//                    LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
//                    LocalTime.parse("09:40"))) &&
//                    patientAppointmentSchedule.get().getSendDate().isBefore(LocalDateTime.of(
//                            LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
//                            LocalTime.parse("10:40")))
//            ) {
//                Optional<PatientAppointment> patientAppointment = appointmentRepository.findById(patientAppointmentSchedule.get().getAppointmentId());
//
//                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//
//                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
//                messageHelper.setTo(patientAppointment.get().getPatientEmail());
//                messageHelper.setSubject("Check Appointment message");
//
//                String messageText = patientAppointment.get().getAppointmentList();
//
//                messageHelper.setText(messageText, true);
//
//                checkRepository.deleteById(patientAppointmentScheduleCheck.getId());
//            } else if (patientAppointmentSchedule.get().getSendDate().isAfter(LocalDateTime.of(
//                    LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
//                    LocalTime.parse("10:40"))) &&
//                    patientAppointmentSchedule.get().getSendDate().isBefore(LocalDateTime.of(
//                            LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
//                            LocalTime.parse("11:40")))
//            ) {
//                Optional<PatientAppointment> patientAppointment = appointmentRepository.findById(patientAppointmentSchedule.get().getAppointmentId());
//
//                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//
//                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
//                messageHelper.setTo(patientAppointment.get().getPatientEmail());
//                messageHelper.setSubject("Check Appointment message");
//
//                String messageText = patientAppointment.get().getAppointmentList();
//
//                messageHelper.setText(messageText, true);
//
//                checkRepository.deleteById(patientAppointmentScheduleCheck.getId());
//            } else if (patientAppointmentSchedule.get().getSendDate().isAfter(LocalDateTime.of(
//                    LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
//                    LocalTime.parse("11:40"))) &&
//                    patientAppointmentSchedule.get().getSendDate().isBefore(LocalDateTime.of(
//                            LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
//                            LocalTime.parse("12:40")))
//            ) {
//                Optional<PatientAppointment> patientAppointment = appointmentRepository.findById(patientAppointmentSchedule.get().getAppointmentId());
//
//                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//
//                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
//                messageHelper.setTo(patientAppointment.get().getPatientEmail());
//                messageHelper.setSubject("Check Appointment message");
//
//                String messageText = patientAppointment.get().getAppointmentList();
//
//                messageHelper.setText(messageText, true);
//
//                checkRepository.deleteById(patientAppointmentScheduleCheck.getId());
//            } else if (patientAppointmentSchedule.get().getSendDate().isAfter(LocalDateTime.of(
//                    LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
//                    LocalTime.parse("12:40"))) &&
//                    patientAppointmentSchedule.get().getSendDate().isBefore(LocalDateTime.of(
//                            LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
//                            LocalTime.parse("13:40")))
//            ) {
//                Optional<PatientAppointment> patientAppointment = appointmentRepository.findById(patientAppointmentSchedule.get().getAppointmentId());
//
//                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//
//                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
//                messageHelper.setTo(patientAppointment.get().getPatientEmail());
//                messageHelper.setSubject("Check Appointment message");
//
//                String messageText = patientAppointment.get().getAppointmentList();
//
//                messageHelper.setText(messageText, true);
//
//                checkRepository.deleteById(patientAppointmentScheduleCheck.getId());
//            } else if (patientAppointmentSchedule.get().getSendDate().isAfter(LocalDateTime.of(
//                    LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
//                    LocalTime.parse("13:40"))) &&
//                    patientAppointmentSchedule.get().getSendDate().isBefore(LocalDateTime.of(
//                            LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
//                            LocalTime.parse("14:40")))
//            ) {
//                Optional<PatientAppointment> patientAppointment = appointmentRepository.findById(patientAppointmentSchedule.get().getAppointmentId());
//
//                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//
//                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
//                messageHelper.setTo(patientAppointment.get().getPatientEmail());
//                messageHelper.setSubject("Check Appointment message");
//
//                String messageText = patientAppointment.get().getAppointmentList();
//
//                messageHelper.setText(messageText, true);
//
//                checkRepository.deleteById(patientAppointmentScheduleCheck.getId());
//            } else if (patientAppointmentSchedule.get().getSendDate().isAfter(LocalDateTime.of(
//                    LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
//                    LocalTime.parse("14:40"))) &&
//                    patientAppointmentSchedule.get().getSendDate().isBefore(LocalDateTime.of(
//                            LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
//                            LocalTime.parse("15:40")))
//            ) {
//                Optional<PatientAppointment> patientAppointment = appointmentRepository.findById(patientAppointmentSchedule.get().getAppointmentId());
//
//                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//
//                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
//                messageHelper.setTo(patientAppointment.get().getPatientEmail());
//                messageHelper.setSubject("Check Appointment message");
//
//                String messageText = patientAppointment.get().getAppointmentList();
//
//                messageHelper.setText(messageText, true);
//
//                checkRepository.deleteById(patientAppointmentScheduleCheck.getId());
//            } else if (patientAppointmentSchedule.get().getSendDate().isAfter(LocalDateTime.of(
//                    LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
//                    LocalTime.parse("15:40"))) &&
//                    patientAppointmentSchedule.get().getSendDate().isBefore(LocalDateTime.of(
//                            LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
//                            LocalTime.parse("16:40")))
//            ) {
//                Optional<PatientAppointment> patientAppointment = appointmentRepository.findById(patientAppointmentSchedule.get().getAppointmentId());
//
//                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//
//                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
//                messageHelper.setTo(patientAppointment.get().getPatientEmail());
//                messageHelper.setSubject("Check Appointment message");
//
//                String messageText = patientAppointment.get().getAppointmentList();
//
//                messageHelper.setText(messageText, true);
//
//                checkRepository.deleteById(patientAppointmentScheduleCheck.getId());
//            } else if (patientAppointmentSchedule.get().getSendDate().isAfter(LocalDateTime.of(
//                    LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
//                    LocalTime.parse("16:40"))) &&
//                    patientAppointmentSchedule.get().getSendDate().isBefore(LocalDateTime.of(
//                            LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
//                            LocalTime.parse("17:40")))
//            ) {
//                Optional<PatientAppointment> patientAppointment = appointmentRepository.findById(patientAppointmentSchedule.get().getAppointmentId());
//
//                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//
//                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
//                messageHelper.setTo(patientAppointment.get().getPatientEmail());
//                messageHelper.setSubject("Check Appointment message");
//
//                String messageText = patientAppointment.get().getAppointmentList();
//
//                messageHelper.setText(messageText, true);
//
//                checkRepository.deleteById(patientAppointmentScheduleCheck.getId());
//            } else if (patientAppointmentSchedule.get().getSendDate().isAfter(LocalDateTime.of(
//                    LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
//                    LocalTime.parse("17:40"))) &&
//                    patientAppointmentSchedule.get().getSendDate().isBefore(LocalDateTime.of(
//                            LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
//                            LocalTime.parse("18:40")))
//            ) {
//                Optional<PatientAppointment> patientAppointment = appointmentRepository.findById(patientAppointmentSchedule.get().getAppointmentId());
//
//                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//
//                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
//                messageHelper.setTo(patientAppointment.get().getPatientEmail());
//                messageHelper.setSubject("Check Appointment message");
//
//                String messageText = patientAppointment.get().getAppointmentList();
//
//                messageHelper.setText(messageText, true);
//
//                checkRepository.deleteById(patientAppointmentScheduleCheck.getId());
//            } else if (patientAppointmentSchedule.get().getSendDate().isAfter(LocalDateTime.of(
//                    LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
//                    LocalTime.parse("18:40"))) &&
//                    patientAppointmentSchedule.get().getSendDate().isBefore(LocalDateTime.of(
//                            LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
//                            LocalTime.parse("19:40")))
//            ) {
//                Optional<PatientAppointment> patientAppointment = appointmentRepository.findById(patientAppointmentSchedule.get().getAppointmentId());
//
//                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//
//                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
//                messageHelper.setTo(patientAppointment.get().getPatientEmail());
//                messageHelper.setSubject("Check Appointment message");
//
//                String messageText = patientAppointment.get().getAppointmentList();
//
//                messageHelper.setText(messageText, true);
//
//                checkRepository.deleteById(patientAppointmentScheduleCheck.getId());
//            } else if (patientAppointmentSchedule.get().getSendDate().isAfter(LocalDateTime.of(
//                    LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
//                    LocalTime.parse("19:40"))) &&
//                    patientAppointmentSchedule.get().getSendDate().isBefore(LocalDateTime.of(
//                            LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
//                            LocalTime.parse("20:40")))
//            ) {
//                Optional<PatientAppointment> patientAppointment = appointmentRepository.findById(patientAppointmentSchedule.get().getAppointmentId());
//
//                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//
//                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
//                messageHelper.setTo(patientAppointment.get().getPatientEmail());
//                messageHelper.setSubject("Check Appointment message");
//
//                String messageText = patientAppointment.get().getAppointmentList();
//
//                messageHelper.setText(messageText, true);
//
//                checkRepository.deleteById(patientAppointmentScheduleCheck.getId());
//            } else if (patientAppointmentSchedule.get().getSendDate().isAfter(LocalDateTime.of(
//                    LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
//                    LocalTime.parse("20:40"))) &&
//                    patientAppointmentSchedule.get().getSendDate().isBefore(LocalDateTime.of(
//                            LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
//                            LocalTime.parse("21:40")))
//            ) {
//                Optional<PatientAppointment> patientAppointment = appointmentRepository.findById(patientAppointmentSchedule.get().getAppointmentId());
//
//                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//
//                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
//                messageHelper.setTo(patientAppointment.get().getPatientEmail());
//                messageHelper.setSubject("Check Appointment message");
//
//                String messageText = patientAppointment.get().getAppointmentList();
//
//                messageHelper.setText(messageText, true);
//
//                checkRepository.deleteById(patientAppointmentScheduleCheck.getId());
//            } else if (patientAppointmentSchedule.get().getSendDate().isAfter(LocalDateTime.of(
//                    LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
//                    LocalTime.parse("21:40"))) &&
//                    patientAppointmentSchedule.get().getSendDate().isBefore(LocalDateTime.of(
//                            LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
//                            LocalTime.parse("22:40")))
//            ) {
//                Optional<PatientAppointment> patientAppointment = appointmentRepository.findById(patientAppointmentSchedule.get().getAppointmentId());
//
//                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//
//                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
//                messageHelper.setTo(patientAppointment.get().getPatientEmail());
//                messageHelper.setSubject("Check Appointment message");
//
//                String messageText = patientAppointment.get().getAppointmentList();
//
//                messageHelper.setText(messageText, true);
//
//                checkRepository.deleteById(patientAppointmentScheduleCheck.getId());
//            } else if (patientAppointmentSchedule.get().getSendDate().isAfter(LocalDateTime.of(
//                    LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
//                    LocalTime.parse("22:40"))) &&
//                    patientAppointmentSchedule.get().getSendDate().isBefore(LocalDateTime.of(
//                            LocalDate.parse(patientAppointmentSchedule.get().getSendDate().toString().substring(0, 10)),
//                            LocalTime.parse("23:40")))
//            ) {
//                Optional<PatientAppointment> patientAppointment = appointmentRepository.findById(patientAppointmentSchedule.get().getAppointmentId());
//
//                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//
//                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
//                messageHelper.setTo(patientAppointment.get().getPatientEmail());
//                messageHelper.setSubject("Check Appointment message");
//
//                String messageText = patientAppointment.get().getAppointmentList();
//
//                messageHelper.setText(messageText, true);
//
//                checkRepository.deleteById(patientAppointmentScheduleCheck.getId());
//            }
        }
    }

}
