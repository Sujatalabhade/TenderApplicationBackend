//package com.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.server.ResponseStatusException;
//
//import com.entity.Tender;
//import com.repository.TenderRepository;
//import com.security.JwtUtil;
//
//@RestController
//@CrossOrigin(origins = "http://localhost:3000")
//@RequestMapping("/api/tender")
//public class TenderController {
//
//    @Autowired
//    private TenderRepository repo;
//
//    // ===============================
//    // ADD / UPDATE TENDER (JWT)
//    // ===============================
//    @PostMapping
//    public Tender saveTender(
//            @RequestBody Tender tender,
//            @RequestHeader("Authorization") String authHeader
//    ) {
//        validateToken(authHeader);
//        return repo.save(tender);
//    }
//
//    // ===============================
//    // GET ALL TENDERS (JWT)
//    // ===============================
//    @GetMapping
//    public List<Tender> getAll(
//            @RequestHeader("Authorization") String authHeader
//    ) {
//        validateToken(authHeader);
//        return repo.findAll();
//    }
//
//    // ===============================
//    // GET TENDER BY ID (JWT)
//    // ===============================
//    @GetMapping("/{id}")
//    public Tender getById(
//            @PathVariable Long id,
//            @RequestHeader("Authorization") String authHeader
//    ) {
//        validateToken(authHeader);
//        return repo.findById(id)
//                .orElseThrow(() ->
//                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Tender not found"));
//    }
//
//    // ===============================
//    // DELETE TENDER (JWT)
//    // ===============================
//    @DeleteMapping("/{id}")
//    public void deleteTender(
//            @PathVariable Long id,
//            @RequestHeader("Authorization") String authHeader
//    ) {
//        validateToken(authHeader);
//        repo.deleteById(id);
//    }
//
//    // ===============================
//    // COMMON JWT VALIDATION METHOD
//    // ===============================
//    private void validateToken(String authHeader) {
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            throw new ResponseStatusException(
//                    HttpStatus.UNAUTHORIZED,
//                    "Authorization header missing or invalid"
//            );
//        }
//
//        String token = authHeader.substring(7);
//
//        try {
//            JwtUtil.extractEmail(token); // validate token
//        } catch (Exception e) {
//            throw new ResponseStatusException(
//                    HttpStatus.UNAUTHORIZED,
//                    "JWT token expired or invalid"
//            );
//        }
//    }
//}

package com.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tender")
@CrossOrigin(origins = "*")
public class TenderController {

    @GetMapping
    public List<Map<String, String>> getAllTenders() {
        return List.of(
                Map.of("id", "1", "title", "Road Construction"),
                Map.of("id", "2", "title", "Bridge Work")
        );
    }

    @PostMapping
    public Map<String, String> addTender(@RequestBody Map<String, String> tender) {
        return Map.of("message", "Tender added successfully (demo)");
    }

    @PutMapping("/{id}")
    public Map<String, String> updateTender(@PathVariable String id) {
        return Map.of("message", "Tender updated for id " + id);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteTender(@PathVariable String id) {
        return Map.of("message", "Tender deleted for id " + id);
    }
}