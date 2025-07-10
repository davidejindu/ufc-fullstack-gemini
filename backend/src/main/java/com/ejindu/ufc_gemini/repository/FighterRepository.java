package com.ejindu.ufc_gemini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ejindu.ufc_gemini.entity.Fighter;

@Repository
public interface FighterRepository extends JpaRepository<Fighter, Long> {

    List<Fighter> findByDivision(String division);

    @Query("SELECT DISTINCT f.division FROM Fighter f")
    List<String> getAllDivisions();
}
