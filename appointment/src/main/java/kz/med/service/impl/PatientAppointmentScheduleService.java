package kz.med.service.impl;

import kz.med.model.PatientAppointment;
import kz.med.model.PatientAppointmentSchedule;
import kz.med.repository.PatientAppointmentScheduleCheckRepository;
import kz.med.repository.PatientAppointmentScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class PatientAppointmentScheduleService {

    @Autowired
    PatientAppointmentScheduleRepository patientAppointmentScheduleRepository;
    @Autowired
    PatientAppointmentScheduleCheckRepository patientAppointmentScheduleCheckRepository;


    public void createSchedule(PatientAppointment patientAppointment) {
        // Days count for loop
        int daysCount = patientAppointment.getHowManyDays();
        // Count of drink medicine for loop
        int countInOneDay = patientAppointment.getHowManyTimesDay();

        // Count of days
        for (int i = 0; i < daysCount; i++) {
            int plusHours = 0;
            // Count of drink for per day
            for (int j = 0; j < countInOneDay; j++) {

                // Increasing time for per day
                if (j != 0) {
                    switch (countInOneDay) {
                        case 2:
                            plusHours = plusHours + 9;
                            break;
                        case 3:
                            plusHours = plusHours + 6;
                            break;
                        case 4:
                            plusHours = plusHours + 3;
                            break;
                        case 5:
                            plusHours = plusHours + 2;
                            break;
                        case 6:
                            plusHours = plusHours + 2;
                            break;
                        case 7:
                            plusHours = plusHours + 1;
                            break;
                        case 8:
                            plusHours = plusHours + 1;
                            break;
                        case 9:
                            plusHours = plusHours + 1;
                            break;
                        case 10:
                            plusHours = plusHours + 1;
                            break;
                    }
                }

                PatientAppointmentSchedule patientAppointmentSchedule = new PatientAppointmentSchedule();
                patientAppointmentSchedule.setAppointmentId(patientAppointment.getId());
                patientAppointmentSchedule.setSendDate(LocalDateTime.of(                      // Represent a date with time-of-day, but lacking in zone/offset so NOT a moment, NOT a point on the timeline.
                        LocalDate.parse(patientAppointment.getStartSendMessage().toString().substring(0, 10)).plusDays(i),  // Parse the date-only value.
                        LocalTime.parse("09:00").plusHours(plusHours)         // Parse the time-of-day.
                ));

                patientAppointmentScheduleRepository.save(patientAppointmentSchedule);
            }
        }
    }

}
