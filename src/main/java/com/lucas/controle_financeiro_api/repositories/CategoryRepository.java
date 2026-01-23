package com.lucas.controle_financeiro_api.repositories;

import com.lucas.controle_financeiro_api.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUserIdOrIsDefaultTrue(Long userId);

    Optional<Category> findByName(String name);

    boolean existsByName(String name);

    @Query("""
       SELECT c FROM Category c
       WHERE (c.user.id = :userId OR c.isDefault = true)
       AND c.system = false
    """)
    List<Category> findVisibleCategories(Long userId);
}
