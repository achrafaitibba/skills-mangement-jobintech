package com.achrafaitibba.itskillsmanagement.model;

import com.achrafaitibba.itskillsmanagement.enums.Status;
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
public class Application {
    @Id
    @GeneratedValue
    private Long id;
    private Date date;
    private Status status;
    private String feedback;
    @OneToOne
    private Candidate candidate;
    @OneToOne
    private Offer offer;
}
