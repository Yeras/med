package kz.med.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_teacher_information_list")
@Data
@SequenceGenerator(name = "user_teacher_information_list_id_seq"
        , sequenceName = "user_teacher_information_list_id_seq"
        , allocationSize = 1)
public class UserTeacherInformationList {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_teacher_information_list_id_seq")
    private int id;

    @Column(name = "user_id")
    private Integer userId;

}
