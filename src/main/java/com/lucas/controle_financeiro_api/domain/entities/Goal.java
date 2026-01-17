package com.lucas.controle_financeiro_api.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "goals")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal desiredAmount;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal currentAmount = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
