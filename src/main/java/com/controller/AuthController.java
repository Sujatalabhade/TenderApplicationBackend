package com.controller;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.entity.User;
import com.repository.UserRepository;
import com.security.JwtUtil;


@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserRepository repo;

    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public User register(
            @RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("contact") String contact,
            @RequestParam("gender") String gender,
            @RequestParam("photo") MultipartFile photo
    ) throws Exception {

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);
        user.setContact(contact);
        user.setGender(gender);

        // (optional) photo save logic later
        System.out.println("Photo name: " + photo.getOriginalFilename());

        return repo.save(user);
    }
    
    @GetMapping("/test")
    public String test() {
        return "API WORKING";
    }

    // LOGIN
//    @PostMapping("/login")
//    public User login(@RequestBody User user) {
//        return repo.findByEmailAndPassword(
//                user.getEmail(),
//                user.getPassword()
//        ).orElse(null);
//    }
    @PostMapping(value = "/login", consumes = "application/json")
    public Map<String, String> login(@RequestBody Map<String, String> data) {

        String email = data.get("email");
        String password = data.get("password");

        System.out.println("Login Email: [" + email + "]");
        System.out.println("Login Password: [" + password + "]");

        User dbUser = repo.findByEmailAndPassword(email, password).orElse(null);

        if (dbUser == null) {
            System.out.println("LOGIN FAILED");
            return Map.of("error", "Invalid credentials");
        }

        String token = JwtUtil.generateToken(dbUser.getEmail());

        System.out.println("LOGIN SUCCESS");

        return Map.of(
                "token", token,
                "email", dbUser.getEmail()
        );
    }
}