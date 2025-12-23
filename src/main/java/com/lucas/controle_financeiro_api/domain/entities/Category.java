package com.lucas.controle_financeiro_api.domain.entities;

import com.lucas.controle_financeiro_api.domain.enums.CategoryName;
import com.lucas.controle_financeiro_api.domain.enums.CategoryType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Category {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryName name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType type;
}
