package kz.med.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "user_education_info")
@SequenceGenerator(name = "user_info_full_id_seq", sequenceName = "user_info_full_id_seq", allocationSize = 1)
public class UserEducationInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_info_full_id_seq")
    private int id;

    @Column(name = "enter")
    private Date enter;

    @Column(name = "finish")
    private Date finish;

    @Column(name = "user_info_id")
    private int userInfoId;

    @Column(name = "country_id")
    private Integer countryId;

    @Column(name = "city_id")
    private Integer cityId;

    @Column(name = "university_id")
    private Integer universityId;

    @Column(name = "faculty_id")
    private Integer facultyId;

    @Column(name = "specialty_id")
    private Integer specialtyId;

    @Column(name = "degree_id")
    private Integer degreeId;

    @Column(name = "university_picture_id")
    private Integer universityPictureId;

}
