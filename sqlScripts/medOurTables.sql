alter table patient_appointment
	add if not exists doctor_id int;

ALTER TABLE patient_appointment
    ADD CONSTRAINT patient_appointment_doctor_id_fk
        FOREIGN KEY (doctor_id)
            REFERENCES registration_user(id);

create table if not exists patient_appointment_schedule
(
	id int
		constraint patient_appointment_schedule_pk
			primary key,
	"send_date" timestamp,
	appointment_id int
);

create sequence if not exists patient_appointment_schedule_id_seq
  as integer
  maxvalue 2147483647;

create table if not exists patient_appointment_schedule_check
(
	id int
		constraint patient_appointment_schedule_check_pk
			primary key,
	appointment_schedule_id int
);

create sequence if not exists patient_appointment_schedule_check_id_seq
  as integer
  maxvalue 2147483647;

ALTER TABLE patient_appointment_schedule
    ADD CONSTRAINT patient_appointment_schedule_appointment_id_fk
        FOREIGN KEY (appointment_id)
            REFERENCES patient_appointment(id);

ALTER TABLE patient_appointment_schedule_check
    ADD CONSTRAINT patient_appointment_schedule_appointment_check_id_fk
        FOREIGN KEY (appointment_schedule_id)
            REFERENCES patient_appointment_schedule(id);