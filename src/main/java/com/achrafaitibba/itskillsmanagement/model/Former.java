package com.achrafaitibba.itskillsmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Former {

    @Id
    @GeneratedValue
    private Long id;
    private String expertiseDomain;
    private String companyName;
    @OneToOne
    private User user;
}
