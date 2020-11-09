package kz.med.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "city_university")
@Data
@SequenceGenerator(name = "city_university_id_seq", sequenceName = "city_university_id_seq", allocationSize = 1)
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_university_id_seq")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "status")
    private String status;

    @Column(name = "city_id")
    private Integer cityId;

}
