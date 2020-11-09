package kz.med.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "world_country")
@Data
@SequenceGenerator(name = "world_country_id_seq", sequenceName = "world_country_id_seq", allocationSize = 1)
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "world_country_id_seq")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

}
