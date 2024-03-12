package com.achrafaitibba.itskillsmanagement.model;

import com.achrafaitibba.itskillsmanagement.model.Application;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @OneToMany
    private List<Application> applications;
}
