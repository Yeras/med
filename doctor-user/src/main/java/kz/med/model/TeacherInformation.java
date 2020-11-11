package kz.med.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "teacher_information")
@Data
@SequenceGenerator(name = "teacher_information_id_seq", sequenceName = "teacher_information_id_seq", allocationSize = 1)
public class TeacherInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacher_information_id_seq")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "fathers_name")
    private String fathersName;

    @Column(name = "user_birthday")
    private Date userBirthday;

    @Column(name = "about_me")
    private String aboutMe;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "country_id")
    private Integer countryId;

    @Column(name = "city_id")
    private Integer cityId;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "grade_points")
    private Float gradePoints;

    @Column(name = "grade_name")
    private String gradeName;

    @Column(name = "user_quantity")
    private Float userQuantity;

}
