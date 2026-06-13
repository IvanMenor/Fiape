package com.example.demo.repository;

import com.example.demo.entity.Pago;
import com.example.demo.entity.Credito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByCredito(Credito credito);

    long countByCreditoUsuarioIdAndPuntualTrue(Long usuarioId);

}