package com.achrafaitibba.itskillsmanagement.model;

import com.achrafaitibba.itskillsmanagement.enums.Level;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Skill {
    @Id
    @GeneratedValue
    private Long id;
    private String technology;
    private Level level;
    @ManyToOne
    private Candidate candidate;
}
