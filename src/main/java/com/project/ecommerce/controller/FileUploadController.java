package com.project.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/upload")
public class FileUploadController {

    public static final String DIRECTORY = "./uploads/profile/";

    @PostMapping
    public ResponseEntity<List<String>> uploadFile(@RequestPart("files") List<MultipartFile> multipartFiles) throws IOException {

        List<String> fileNames = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {

            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path fileStorage = get(DIRECTORY, fileName).toAbsolutePath().normalize();
            copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);
            fileNames.add(fileName);
        }

        return ResponseEntity.ok().body(fileNames);
    }

    @GetMapping(
            value = "/get-image/{imageName}",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public @ResponseBody byte[] getImageWithMediaType(@PathVariable String imageName) throws IOException {
        String imageDir = Paths.get("").toAbsolutePath()+"/uploads/profile/"+imageName;

        File fi = new File(imageDir);
        byte[] fileContent = Files.readAllBytes(fi.toPath());

        return fileContent;
    }

}
