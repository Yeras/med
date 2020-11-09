package kz.med.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_verification_token")
@Data
@SequenceGenerator(name = "user_verification_token_id_seq", sequenceName = "user_verification_token_id_seq", allocationSize = 1)
public class UserVerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_verification_token_id_seq")
    private int id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "token")
    private String token;

    @Column(name = "expiry_date")
    private Timestamp expiryDate;

}
