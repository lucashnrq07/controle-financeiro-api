package com.lucas.controle_financeiro_api.domain.entities;

import com.lucas.controle_financeiro_api.domain.enums.Frequency;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "recurring_movements")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class RecurringMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(max = 150, message = "Descrição deve ter no máximo 150 caracteres")
    @Column(nullable = false, length = 150)
    private String description;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @NotNull(message = "Categoria é obrigatória")
    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @NotNull(message = "Frequência é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Frequency frequency; // MONTHLY | WEEKLY

    @Min(value = 1, message = "Dia do mês deve ser entre 1 e 31")
    @Max(value = 31, message = "Dia do mês deve ser entre 1 e 31")
    private Integer dayOfMonth;

    @Min(value = 1, message = "Dia da semana deve ser entre 1 (Seg) e 7 (Dom)")
    @Max(value = 7, message = "Dia da semana deve ser entre 1 (Seg) e 7 (Dom)")
    private Integer dayOfWeek;

    @NotNull
    @Column(nullable = false)
    private Boolean active = true;

    private LocalDate lastGenerated;

    @NotNull(message = "Usuário é obrigatório")
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
