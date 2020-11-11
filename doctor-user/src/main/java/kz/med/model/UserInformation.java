package kz.med.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_information")
@Data
@SequenceGenerator(name = "user_information_id_seq", sequenceName = "user_information_id_seq", allocationSize = 1)
public class UserInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_information_id_seq")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "fathers_name")
    private String fathersName;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "user_birthday")
    private Date userBirthday;

    @Column(name = "about_me")
    private String aboutMe;

    @Column(name = "country_id")
    private Integer countryId;

    @Column(name = "city_id")
    private Integer cityId;

}
