package kz.med.controller;

import kz.med.model.UserInformationPicture;
import kz.med.service.impl.UserInformationPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/picture")
public class UserInformationPictureController {

    @Autowired
    UserInformationPictureService pictureService;


    @PostMapping("/file/upload/{userId}")
    public String uploadMultipartFile(@RequestParam("file") MultipartFile file,
                                      @PathVariable int userId) {
        try {
            //save file to PostgreSQL, change user picture
            UserInformationPicture informationPicture = new UserInformationPicture();

            Optional<UserInformationPicture> pictureObj = pictureService.findByUserId(userId);

            informationPicture.setFileName(file.getOriginalFilename());
            informationPicture.setType(file.getContentType());
            informationPicture.setPictureFile(file.getBytes());
            informationPicture.setId(pictureObj.get().getId());
            informationPicture.setUserId(userId);

            pictureService.uploadFile(informationPicture);

            return "File uploaded successfully! -> filename = " + file.getOriginalFilename();
        } catch (Exception e) {
            return "FAIL! Maybe You had uploaded the file before or the file's size > 500KB";
        }
    }


    @GetMapping("/file/{userId}")
    public ResponseEntity<byte[]> getFile(@PathVariable int userId) {
        Optional<UserInformationPicture> fileOptional = pictureService.findByUserId(userId);

        if (fileOptional.isPresent()) {
            UserInformationPicture file = fileOptional.get();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                    .body(file.getPictureFile());
        }

        return ResponseEntity.status(404).body(null);
    }

    @GetMapping("/user/{userId}")
    public int getByUserId(@PathVariable int userId) {
        int id = pictureService.getByUserId(userId).getId();

        return id;
    }

}
