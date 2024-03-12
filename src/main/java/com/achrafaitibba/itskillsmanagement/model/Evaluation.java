package com.achrafaitibba.itskillsmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Evaluation {
    @Id
    @GeneratedValue
    private Long id;
    private Long grade;
    private String comment;
    private Date date;
    @OneToOne
    private Candidate candidate;
    @OneToOne
    private Former former;

}
