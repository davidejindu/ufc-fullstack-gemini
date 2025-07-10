package com.ejindu.ufc_gemini.service;

import com.ejindu.ufc_gemini.dto.FighterDto;

import java.util.List;

public interface FighterService {

    public FighterDto getFighterById(Long id);

    public List<FighterDto> findByDivision(String division);

    public FighterDto createFighter(FighterDto fighterDto);

    FighterDto updateFighter(Long id, FighterDto fighterDto);


}
