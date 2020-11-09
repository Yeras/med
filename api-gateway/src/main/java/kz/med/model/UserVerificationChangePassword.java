package kz.med.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_verification_change_password")
@Data
@SequenceGenerator(name = "user_verification_change_password_id_seq", sequenceName = "user_verification_change_password_id_seq", allocationSize = 1)
public class UserVerificationChangePassword {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_verification_change_password_id_seq")
    private int id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "token")
    private String token;

    @Column(name = "new_password")
    private String newPassword;

    @Column(name = "token_status")
    private Boolean tokenStatus;

}
