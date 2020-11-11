package kz.med.controller;

import kz.med.model.UserTeacherInformationList;
import kz.med.service.impl.UserTeacherInformationListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher/create")
public class UserTeacherInformationListController {

    @Autowired
    UserTeacherInformationListService teacherInformationListService;


    @PostMapping()
    public UserTeacherInformationList save(@RequestBody UserTeacherInformationList teacherInformationList) {
        return teacherInformationListService.save(teacherInformationList);
    }

    @GetMapping("/{userId}")
    public List<UserTeacherInformationList> findByUserId(@PathVariable Integer userId) {
        return teacherInformationListService.findByUserId(userId);
    }

}
