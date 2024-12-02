package com.example.projeto_backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projeto_backend.models.Resultado;
import com.example.projeto_backend.repository.ResultadoRepository;

@Service
public class ResultadoService {

    @Autowired
    private ResultadoRepository repository; // Usar ResultadoRepositoryFake para testes sem o banco

    public List<Resultado> listarTodos() {
        return repository.findAll();
    }

    public Resultado salvar(Resultado resultado) {
        return repository.save(resultado);
    }

    public List<Resultado> listarPorAlgoritmo(String algoritmo) {
        return repository.findByAlgoritmo(algoritmo);
    }
}
