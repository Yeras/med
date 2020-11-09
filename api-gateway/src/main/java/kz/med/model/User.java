package kz.med.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "registration_user")
@SequenceGenerator(name = "user_info_id_seq", sequenceName = "user_info_id_seq", allocationSize = 1)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_info_id_seq")
    private int id;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "password")
    private String password;

    @Column(name = "private_policy")
    private String privatePolicy;

    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "user_activate")
    private Boolean userActivate;

    @Column(name = "language_type")
    private String languageType;

}
