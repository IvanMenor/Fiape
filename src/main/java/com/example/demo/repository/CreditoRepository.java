package com.example.demo.repository;

import com.example.demo.entity.Credito;
import com.example.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditoRepository extends JpaRepository<Credito, Long> {

    List<Credito> findByUsuario(Usuario usuario);

}