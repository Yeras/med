package kz.med.controller;

import kz.med.model.TeacherInformation;
import kz.med.model.UserTeacherInformationList;
import kz.med.service.impl.TeacherInformationService;
import kz.med.service.impl.UserTeacherInformationListService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherInformationController {

    @Autowired
    TeacherInformationService teacherInformationService;
//    @Autowired
//    UserInformationPictureService pictureService;
//    @Autowired
//    UserInformationBgPictureService bgPictureService;
    @Autowired
    UserTeacherInformationListService teacherInformationListService;


    @PostMapping()
    public TeacherInformation save(@RequestBody TeacherInformation teacherInformation) {

        UserTeacherInformationList userInformationList = new UserTeacherInformationList();

//        try {
//            uploadUserPicture(teacherInformation.getUserId());
//        } catch (Exception e) {
//            System.out.println("User Profile: " + e.getCause());
//        }
//        try {
//            uploadUserBgPicture(teacherInformation.getUserId());
//        } catch (Exception e) {
//            System.out.println("User profile background: " + e.getCause());
//        }

        userInformationList.setUserId(teacherInformation.getUserId());
        teacherInformationListService.save(userInformationList);

        return teacherInformationService.save(teacherInformation);
    }

//    public void uploadUserPicture(int userId) throws Exception {
//        //Local path file (in project)
//        String filePath = "/opt/wuk/registration/not-found.jpg";
//
////        byte[] bFile = Files.readAllBytes(new File(filePath).toPath());
//
//        /***
//         * New Block for upload picture
//         * */
//        File file = new File(filePath);
//
//        byte[] bFile = null;
//
//        try {
//            bFile = FileUtils.readFileToByteArray(file);
//        } catch (Exception e) {
//            System.out.println("Exception Cause: " + e.getCause());
//            System.out.println("Exception Message: " + e.getMessage());
//        }
//
//        //save file to PostgreSQL, change file if uploaded before
//        UserInformationPicture informationPicture = new UserInformationPicture();
//
//        informationPicture.setFileName("not-found.jpg");
//        informationPicture.setType("image/jpeg");
//        informationPicture.setPictureFile(bFile);
//        informationPicture.setUserId(userId);
//
//        pictureService.uploadFile(informationPicture);
//    }
//
//    public void uploadUserBgPicture(int userId) throws Exception {
//        //Local path file (in project)
//        String filePath = "/opt/wuk/registration/worldMap.jpg";
//
////        byte[] bFile = Files.readAllBytes(new File(filePath).toPath());
//
//        /***
//         * New Block for upload picture
//         * */
//        File file = new File(filePath);
//
//        byte[] bFile = null;
//
//        try {
//            bFile = FileUtils.readFileToByteArray(file);
//        } catch (Exception e) {
//            System.out.println("Exception Cause: " + e.getCause());
//            System.out.println("Exception Message: " + e.getMessage());
//        }
//
//        //save file to PostgreSQL, change file if uploaded before
//        UserInformationBgPicture informationPicture = new UserInformationBgPicture();
//
//        informationPicture.setFileName("not-found-bg.jpg");
//        informationPicture.setType("image/jpeg");
//        informationPicture.setPictureFile(bFile);
//        informationPicture.setUserId(userId);
//
//        bgPictureService.uploadFile(informationPicture);
//    }

//    @PostMapping("/change")
//    public TeacherInformation changeProfile(@RequestBody TeacherInformation teacherInformation) {
//        return teacherInformationService.save(teacherInformation);
//    }

    @GetMapping("/userList/{userId}")
    public List<UserTeacherInformationList> findByUserIdByUserList(@PathVariable Integer userId) {
        return teacherInformationListService.findByUserId(userId);
    }

    @GetMapping("/user/{userId}")
    public TeacherInformation findByUserId(@PathVariable int userId) {
        return teacherInformationService.findByUserId(userId);
    }

}
