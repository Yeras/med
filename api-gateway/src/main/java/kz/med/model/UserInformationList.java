package kz.med.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_information_list")
@Data
@SequenceGenerator(name = "user_information_list_id_seq", sequenceName = "user_information_list_id_seq", allocationSize = 1)
public class UserInformationList {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_information_list_id_seq")
    private int id;

    @Column(name = "user_id")
    private Integer userId;
    
}
