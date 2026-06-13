package com.example.demo.repository;

import com.example.demo.entity.EvaluacionCrediticia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluacionRepository extends JpaRepository<EvaluacionCrediticia, Long> {
}