package com.lucas.controle_financeiro_api.domain.entities;

import com.lucas.controle_financeiro_api.domain.enums.CategoryType;
import com.lucas.controle_financeiro_api.domain.enums.Frequency;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "movements")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class RecurringMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private BigDecimal amount;

    private Category category; // INCOME ou EXPENSE

    @Enumerated(EnumType.STRING)
    private Frequency frequency; // MONTHLY | WEEKLY

    private Integer dayOfMonth;
    private Integer dayOfWeek;

    private Boolean active = true;
    private LocalDate lastGenerated;

    @ManyToOne
    private User user;
}
