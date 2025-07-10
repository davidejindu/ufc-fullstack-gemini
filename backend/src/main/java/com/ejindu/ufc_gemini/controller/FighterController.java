package com.ejindu.ufc_gemini.controller;

import java.util.List;

import com.ejindu.ufc_gemini.entity.Fighter;
import com.ejindu.ufc_gemini.repository.FighterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejindu.ufc_gemini.dto.FighterDto;
import com.ejindu.ufc_gemini.service.FighterService;
import com.ejindu.ufc_gemini.service.GeminiService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/fighters")
public class FighterController {

    FighterService fighterService;
    GeminiService geminiService;
    FighterRepository fighterRepository;

    @PostMapping
    public ResponseEntity<FighterDto> createFighter(@RequestBody FighterDto fighterDto) {
        FighterDto createdFighterDto = fighterService.createFighter(fighterDto);

        return new ResponseEntity<>(createdFighterDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FighterDto> updateFighter(
            @PathVariable Long id,
            @RequestBody FighterDto fighterDto) {
        FighterDto updated = fighterService.updateFighter(id, fighterDto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping(path = "/division/{division}")
    public ResponseEntity<List<FighterDto>> getFightersByDivision(@PathVariable String division) {
        return ResponseEntity.ok(fighterService.findByDivision(division));
    }

    @GetMapping(path = "/compare/{fighter1}/{fighter2}")
    public Mono<ResponseEntity<?>> fighterPrediction(@PathVariable String fighter1, @PathVariable String fighter2) {
        if (fighter1.equalsIgnoreCase(fighter2)) {
            return Mono.just(ResponseEntity.badRequest().body("You cannot compare a fighter with themselves."));
        }
        return geminiService.getGeminiResponse(fighter1, fighter2)
                .map(ResponseEntity::ok);
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<FighterDto> getFighterById(@PathVariable Long id) {
        return ResponseEntity.ok(fighterService.getFighterById(id));
    }

    @GetMapping(path = "/all-divisions")
    public ResponseEntity<?> getAllDivisions() {
        List<String> divisions = fighterRepository.getAllDivisions();
        if (divisions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(divisions);
    }
 }
