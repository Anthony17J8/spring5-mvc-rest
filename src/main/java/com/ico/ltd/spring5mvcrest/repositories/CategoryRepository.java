package com.ico.ltd.spring5mvcrest.repositories;

import com.ico.ltd.spring5mvcrest.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
