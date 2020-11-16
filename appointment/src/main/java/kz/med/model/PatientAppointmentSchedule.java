package kz.med.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "patient_appointment_schedule")
@Data
@SequenceGenerator(name = "patient_appointment_schedule_id_seq", sequenceName = "patient_appointment_schedule_id_seq", allocationSize = 1)
public class PatientAppointmentSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_appointment_schedule_id_seq")
    private int id;

    @Column(name = "send_date")
    private LocalDateTime sendDate;
//    private Timestamp sendDate;

    @Column(name = "appointment_id")
    private Integer appointmentId;

}
