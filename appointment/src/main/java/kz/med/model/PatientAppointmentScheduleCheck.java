package kz.med.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "patient_appointment_schedule_check")
@Data
@SequenceGenerator(name = "patient_appointment_schedule_check", sequenceName = "patient_appointment_schedule_check", allocationSize = 1)
public class PatientAppointmentScheduleCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_appointment_schedule_id_seq")
    private int id;

    @Column(name = "appointment_schedule_id")
    private Integer appointmentScheduleId;

}
