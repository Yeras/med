alter table patient_appointment
	add if not exists doctor_id int;

ALTER TABLE patient_appointment
    ADD CONSTRAINT patient_appointment_doctor_id_fk
        FOREIGN KEY (doctor_id)
            REFERENCES registration_user(id);