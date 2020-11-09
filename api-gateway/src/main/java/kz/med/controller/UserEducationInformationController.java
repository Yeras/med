package kz.med.controller;

import kz.med.model.UserEducationInformation;
import kz.med.service.impl.UserEducationEducationInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/education")
public class UserEducationInformationController {

    @Autowired
    UserEducationEducationInformationService userEducationInformationService;


    @PostMapping("/save")
    public UserEducationInformation saveList(@RequestBody UserEducationInformation userEducationInformationList) {
        return userEducationInformationService.save(userEducationInformationList);
    }

    @GetMapping("/{id}")
    public UserEducationInformation findById(@PathVariable int id) {
        return userEducationInformationService.findById(id);
    }

    @GetMapping("/all/{userId}")
    public List<UserEducationInformation> findByUserId(@PathVariable int userId) {
        return userEducationInformationService.findAllByUserId(userId);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteInfo(@PathVariable int id) {
        userEducationInformationService.delete(id);

        return "Education information was deleted";
    }

}
