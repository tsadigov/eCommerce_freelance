package com.project.ecommerce.controller;

import com.project.ecommerce.dto.*;
import com.project.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import static com.project.ecommerce.bootstrap.Constants.DIRECTORY;
import static com.project.ecommerce.bootstrap.Constants.PROFILE_PHOTO_END;
import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/customer/{username}")
    ResponseEntity<ResponseDTO> getCustomer(@PathVariable String username) {

        ResponseDTO responseDTO = userService.getCustomer(username);

        return ResponseEntity.ok()
                .body(responseDTO);
    }

    @GetMapping("/seller/{username}")
    ResponseEntity<ResponseDTO> getSeller(@PathVariable String username) {

        ResponseDTO responseDTO = userService.getSeller(username);

        return ResponseEntity.ok()
                .body(responseDTO);
    }

    @PostMapping(path = "/register/customer")
    ResponseEntity<ResponseDTO> signUpCustomer(@RequestBody CustomerSignUpDTO customerSignUpDTO) {

        log.info("Request body -> {}", customerSignUpDTO);

        ResponseDTO responseDTO = userService.customerSignUp(customerSignUpDTO);

        return ResponseEntity.ok()
                .body(responseDTO);
    }

    @PostMapping(path = "/register/seller")
    ResponseEntity<ResponseDTO> signUpSeller(@RequestBody SellerSignUpDTO sellerSignUpDTO) {

        log.info("Request body -> {}", sellerSignUpDTO);

        ResponseDTO responseDTO = userService.sellerSignUp(sellerSignUpDTO);

        return ResponseEntity.ok()
                .body(responseDTO);
    }

    @PostMapping("/update/customer")
    ResponseEntity<ResponseDTO> updateCustomerProfile(@RequestBody CustomerDTO customerDTO) {

        ResponseDTO responseDTO = userService.updateCustomerProfile(customerDTO);
        return ResponseEntity.ok()
                .body(responseDTO);
    }

    @PutMapping("/update/seller")
    ResponseEntity<ResponseDTO> updateSellerProfile(@RequestBody SellerDTO sellerDTO) {

        ResponseDTO responseDTO = userService.updateSellerProfile(sellerDTO);
        return ResponseEntity.ok()
                .body(responseDTO);
    }

    @PostMapping("/upload-profile-picture")
    public ResponseEntity<String> uploadProfilePhoto(@RequestPart("file") MultipartFile file, @RequestPart("username") String username) throws IOException {

        String fileName = StringUtils.cleanPath(username + PROFILE_PHOTO_END);
        Path fileStorage = get(DIRECTORY, fileName).toAbsolutePath().normalize();
        copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);

        return ResponseEntity.ok().body(fileName + " - " + username);
    }

    @GetMapping(
            value = "/get-profile-picture/{username}",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public @ResponseBody
    byte[] getProfilePicture(@PathVariable String username) throws IOException {
        String imageDir = Paths.get("").toAbsolutePath() + "/uploads/profile/" + username + PROFILE_PHOTO_END;
        imageDir.replace("\\", "/");
        File fi = new File(imageDir);
        byte[] fileContent;

        if (fi.exists()) {
            fileContent = Files.readAllBytes(fi.toPath());
        } else {
            InputStream in = this.getClass().getResourceAsStream("/static/profile-icon.png");
            fileContent = in.readAllBytes();
        }
        return fileContent;
    }

}
