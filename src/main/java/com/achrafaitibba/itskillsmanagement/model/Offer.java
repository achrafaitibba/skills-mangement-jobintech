package com.achrafaitibba.itskillsmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryCustomizer;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Offer {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private Long salary;
    private String location;
    @JsonIgnore
    @OneToMany
    private List<Application> applications;
    @ManyToOne
    private Recruiter recruiter;
}
