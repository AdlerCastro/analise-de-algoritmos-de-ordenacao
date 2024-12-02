package com.example.projeto_backend.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.example.projeto_backend.models.Resultado;

@Repository
public class ResultadoRepositoryFake {
    private List<Resultado> resultados = new ArrayList<>();
    private AtomicLong idGenerator = new AtomicLong(1);

    // Salvar um novo resultado
    public Resultado save(Resultado resultado) {
        if (resultado.getId() == null) {
            resultado.setId(idGenerator.getAndIncrement());
        }
        resultados.add(resultado);
        return resultado;
    }

    // Listar todos os resultados
    public List<Resultado> findAll() {
        return resultados;
    }
}

