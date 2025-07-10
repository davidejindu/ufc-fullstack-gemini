package com.ejindu.ufc_gemini.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FightPrediction {

    private String winner;
    private String probability;
    private List<String> reasons;
    private String outcome;
}
