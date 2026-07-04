package com.example.demo.controller;

import com.example.demo.service.ReputacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*")
public class ReputacionController {

    @Autowired
    private ReputacionService reputacionService;

    @GetMapping("/{id}/reputacion")
    public ResponseEntity<?> getReputacion(@PathVariable Long id) {
        int progreso = reputacionService.obtenerProgreso(id);
        
        Map<String, Object> response = new HashMap<>();
        response.put("progreso", progreso);
        
        return ResponseEntity.ok(response);
    }
}