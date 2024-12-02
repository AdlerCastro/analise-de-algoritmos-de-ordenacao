package com.example.projeto_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projeto_backend.models.Resultado;

@Repository
public interface ResultadoRepository extends JpaRepository<Resultado, Long> {
    // Método para buscar resultados por algoritmo
    List<Resultado> findByAlgoritmo(String algoritmo);
}

