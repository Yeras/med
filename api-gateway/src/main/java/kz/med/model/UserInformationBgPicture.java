package kz.med.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_information_background_picture")
@Data
@SequenceGenerator(name = "user_information_background_picture_id_seq"
        , sequenceName = "user_information_background_picture_id_seq"
        , allocationSize = 1)
public class UserInformationBgPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_information_background_picture_id_seq")
    private int id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "mime_type")
    private String type;

    @Column(name = "picture_file")
    private byte[] pictureFile;

    @Column(name = "user_id")
    private Integer userId;

}
