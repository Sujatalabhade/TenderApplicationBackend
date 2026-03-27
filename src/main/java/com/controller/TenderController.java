package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.entity.Tender;
import com.repository.TenderRepository;
import com.security.JwtUtil;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/tender")
public class TenderController {

    @Autowired
    private TenderRepository repo;

    // ===============================
    // ADD / UPDATE TENDER (JWT)
    // ===============================
    @PostMapping
    public Tender saveTender(
            @RequestBody Tender tender,
            @RequestHeader("Authorization") String authHeader
    ) {
        validateToken(authHeader);
        return repo.save(tender);
    }

    // ===============================
    // GET ALL TENDERS (JWT)
    // ===============================
    @GetMapping
    public List<Tender> getAll(
            @RequestHeader("Authorization") String authHeader
    ) {
        validateToken(authHeader);
        return repo.findAll();
    }

    // ===============================
    // GET TENDER BY ID (JWT)
    // ===============================
    @GetMapping("/{id}")
    public Tender getById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader
    ) {
        validateToken(authHeader);
        return repo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Tender not found"));
    }

    // ===============================
    // DELETE TENDER (JWT)
    // ===============================
    @DeleteMapping("/{id}")
    public void deleteTender(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader
    ) {
        validateToken(authHeader);
        repo.deleteById(id);
    }

    // ===============================
    // COMMON JWT VALIDATION METHOD
    // ===============================
    private void validateToken(String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Authorization header missing or invalid"
            );
        }

        String token = authHeader.substring(7);

        try {
            JwtUtil.extractEmail(token); // validate token
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "JWT token expired or invalid"
            );
        }
    }
}