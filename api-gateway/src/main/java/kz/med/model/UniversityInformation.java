package kz.med.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "university_information")
@Data
@SequenceGenerator(name = "university_information_id_seq", sequenceName = "university_information_id_seq", allocationSize = 1)
public class UniversityInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "university_information_id_seq")
    private int id;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "closed_date")
    private Date closedDate;

    @Column(name = "about_university")
    private String aboutUniversity;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "country_id")
    private Integer countryId;

    @Column(name = "city_id")
    private Integer cityId;

    @Column(name = "university_id")
    private Integer universityId;

    @Column(name = "university_web_page")
    private String webPage;

}
