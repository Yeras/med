package kz.med.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "country_city")
@Data
@SequenceGenerator(name = "country_city_id_seq", sequenceName = "country_city_id_seq", allocationSize = 1)
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "country_city_id_seq")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "region")
    private String region;

    @Column(name = "code")
    private String code;

    @Column(name = "country_id")
    private Integer countryId;

}
