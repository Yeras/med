package kz.med.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "patient_appointment")
@Data
@SequenceGenerator(name = "patient_appointment_id_seq", sequenceName = "patient_appointment_id_seq", allocationSize = 1)
public class PatientAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_appointment_id_seq")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "fathers_name")
    private String fathersName;

    @Column(name = "patient_birthday")
    private Date patientBirthday;

    @Column(name = "patient_phone")
    private Integer patientPhone;

    @Column(name = "patient_email")
    private String patientEmail;

    @Column(name = "another_email")
    private String anotherEmail;

    @Column(name = "appointment_list")
    private String appointmentList;

    @Column(name = "how_many_days")
    private Integer howManyDays;

    @Column(name = "how_many_times_day")
    private Integer howManyTimesDay;

    @Column(name = "start_send_message")
    private LocalDateTime startSendMessage;

    @Column(name = "doctor_phone_number")
    private Integer doctorPhoneNumber;

    @Column(name = "doctor_work_number")
    private Integer doctorWorkNumber;

    @Column(name = "doctor_email")
    private String doctorEmail;

    @Column(name = "doctor_id")
    private Integer doctorId;

}
