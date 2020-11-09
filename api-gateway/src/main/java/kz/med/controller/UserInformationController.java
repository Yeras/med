package kz.med.controller;

import kz.med.model.UserInformation;
import kz.med.model.UserInformationBgPicture;
import kz.med.model.UserInformationList;
import kz.med.model.UserInformationPicture;
import kz.med.service.impl.UserInformationBgPictureService;
import kz.med.service.impl.UserInformationListService;
import kz.med.service.impl.UserInformationPictureService;
import kz.med.service.impl.UserInformationService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/userInformation")
public class UserInformationController {

    @Autowired
    UserInformationService userInformationService;
    @Autowired
    UserInformationPictureService pictureService;
    @Autowired
    UserInformationBgPictureService bgPictureService;
    @Autowired
    UserInformationListService userInformationListService;


    @PostMapping()
    public UserInformation save(@RequestBody UserInformation userInformation) {

        UserInformationList userInformationList = new UserInformationList();

        System.out.println("User Information: ");
        System.out.println("User Information: " + userInformation);

//        try {
//            uploadUserPicture(userInformation.getUserId());
//        } catch (Exception e) {
//            System.out.println("User Profile: " + e.getCause());
//        }
//        try {
//            uploadUserBgPicture(userInformation.getUserId());
//        } catch (Exception e) {
//            System.out.println("User profile background: " + e.getCause());
//        }

        userInformationList.setUserId(userInformation.getUserId());
        userInformationListService.save(userInformationList);

        return userInformationService.save(userInformation);
    }

//    public void uploadUserPicture(int userId) throws Exception {
//        //Local path file (in project)
//        String filePath = "/opt/wuk/registration/user-registration/not-found.jpg";
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
//        try {
//            FileUtils.readFileToByteArray(new File("/not-found.jpg"));
//        } catch (Exception e) {
//            System.out.println("Exception Cause 1: " + e.getCause());
//            System.out.println("Exception Message 1: " + e.getMessage());
//        }
//
//        try {
//            FileUtils.readFileToByteArray(new File("not-found.jpg"));
//        } catch (Exception e) {
//            System.out.println("Exception Cause 2: " + e.getCause());
//            System.out.println("Exception Message 2: " + e.getMessage());
//        }
//
//        try {
//            FileUtils.readFileToByteArray(new File("registration/not-found.jpg"));
//        } catch (Exception e) {
//            System.out.println("Exception Cause 3: " + e.getCause());
//            System.out.println("Exception Message 3: " + e.getMessage());
//        }
//
//        try {
//            FileUtils.readFileToByteArray(new File("/registration/not-found.jpg"));
//        } catch (Exception e) {
//            System.out.println("Exception Cause 4: " + e.getCause());
//            System.out.println("Exception Message 4: " + e.getMessage());
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

//    public void uploadUserBgPicture(int userId) throws Exception {
//        //Local path file (in project)
//        String filePath = "/opt/wuk/registration/worldMap.jpg";
//
////        byte[] bFile = Files.readAllBytes(new File(filePath).toPath());
//
//        /***
//         * New Block for upload picture
//         * */
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

    @GetMapping("/user/{userId}")
    public UserInformation findByUserId(@PathVariable int userId) {
        return userInformationService.findByUserId(userId);
    }

    @PostMapping("/change")
    public UserInformation changeProfile(@RequestBody UserInformation userInformation) {
        return userInformationService.save(userInformation);
    }

    @GetMapping("/userList/{userId}")
    public List<UserInformationList> findByUserIdByUserList(@PathVariable Integer userId) {
        return userInformationListService.findById(userId);
    }

}
