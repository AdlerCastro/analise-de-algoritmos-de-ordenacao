package com.example.projeto_backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "resultados")
public class Resultado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String algoritmo;

    @Column(name = "tempo_execucao_milissegundos", nullable = false)
    private Long tempoExecucao;
    
    private Integer tamanhoAmostra;
    private String tipoVetor;

    public Resultado() {
    }

    public Resultado(String algoritmo, Long tempoExecucao, Integer tamanhoAmostra, String tipoVetor) {
        this.algoritmo = algoritmo;
        this.tempoExecucao = tempoExecucao;
        this.tamanhoAmostra = tamanhoAmostra;
        this.tipoVetor = tipoVetor;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    public void setAlgoritmo(String algoritmo) {
        this.algoritmo = algoritmo;
    }

    public Long getTempoExecucao() {
        return tempoExecucao;
    }

    public void setTempoExecucao(Long tempoExecucao) {
        this.tempoExecucao = tempoExecucao;
    }

    public Integer getTamanhoAmostra() {
        return tamanhoAmostra;
    }

    public void setTamanhoAmostra(Integer tamanhoAmostra) {
        this.tamanhoAmostra = tamanhoAmostra;
    }

    public String getTipoVetor() {
        return tipoVetor;
    }

    public void setTipoVetor(String tipoVetor) {
        this.tipoVetor = tipoVetor;
    }
}
