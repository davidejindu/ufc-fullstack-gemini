package com.ejindu.ufc_gemini.mapper;

import com.ejindu.ufc_gemini.dto.FighterDto;
import com.ejindu.ufc_gemini.entity.Fighter;

public class FighterMapper {

    public static Fighter mapFighterDtoToFighter(FighterDto fighterDto) {
        return new Fighter(fighterDto.getId(), fighterDto.getName(), fighterDto.getDivision(), fighterDto.getImageUrl());
    }

    public static FighterDto mapFighterToFighterDto(Fighter fighter) {
        return new FighterDto(fighter.getId(), fighter.getName(), fighter.getDivision(), fighter.getImageUrl());
    }
}
