package com.amrhalahla.springmicrohub.jobservice.repository;

import com.amrhalahla.springmicrohub.jobservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
