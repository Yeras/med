package kz.med.controller;

import kz.med.model.UserInformation;
import kz.med.model.UserInformationList;
import kz.med.service.impl.UserInformationListService;
import kz.med.service.impl.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userInformation")
public class UserInformationController {

    @Autowired
    UserInformationService userInformationService;

    @Autowired
    UserInformationListService userInformationListService;


    @PostMapping()
    public UserInformation save(@RequestBody UserInformation userInformation) {

        UserInformationList userInformationList = new UserInformationList();

        System.out.println("User Information: ");
        System.out.println("User Information: " + userInformation);

        userInformationList.setUserId(userInformation.getUserId());
        userInformationListService.save(userInformationList);

        return userInformationService.save(userInformation);
    }

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
