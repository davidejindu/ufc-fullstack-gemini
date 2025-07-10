package com.ejindu.ufc_gemini.service.impl;


import com.ejindu.ufc_gemini.dto.FighterDto;
import com.ejindu.ufc_gemini.entity.Fighter;
import com.ejindu.ufc_gemini.mapper.FighterMapper;
import com.ejindu.ufc_gemini.repository.FighterRepository;
import com.ejindu.ufc_gemini.service.FighterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@Service
public class FighterServiceImpl implements FighterService {

    FighterRepository fighterRepository;

    @Override
    public FighterDto getFighterById(Long id) {
        Fighter fighter = fighterRepository.findById(id).orElseThrow(
                () ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Fighter with " + id + " doesn't exist")
        );

        return FighterMapper.mapFighterToFighterDto(fighter);
    }

    @Override
    public List<FighterDto> findByDivision(String division) {
        List<Fighter> fighters = fighterRepository.findByDivision(division);
        List<FighterDto> fighterDtos = fighters.stream()
                .map(fighter -> FighterMapper.mapFighterToFighterDto(fighter)).toList();
        return fighterDtos;
    }

    @Override
    public FighterDto createFighter(FighterDto fighterDto) {
        Fighter fighter = FighterMapper.mapFighterDtoToFighter(fighterDto);
        Fighter createdFighter = fighterRepository.save(fighter);
        return FighterMapper.mapFighterToFighterDto(createdFighter);
    }

    @Override
    public FighterDto updateFighter(Long id, FighterDto dto) {
        Fighter fighter = fighterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fighter not found"));
        fighter.setName(dto.getName());
        fighter.setDivision(dto.getDivision());
        fighter.setImageUrl(dto.getImageUrl());

        return FighterMapper.mapFighterToFighterDto(fighterRepository.save(fighter));
    }
}
