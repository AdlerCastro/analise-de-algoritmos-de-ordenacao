package com.example.projeto_backend.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.projeto_backend.algoritmos.BubbleSort;
import com.example.projeto_backend.algoritmos.MergeSort;
import com.example.projeto_backend.algoritmos.QuickSort;
import com.example.projeto_backend.algoritmos.ShellSort;
import com.example.projeto_backend.models.Resultado;
import com.example.projeto_backend.services.CacheService;
import com.example.projeto_backend.services.ResultadoService;
import com.example.projeto_backend.utils.Cenarios;

@RestController
@RequestMapping("/api/resultados")
public class ResultadoController {

    @Autowired
    private CacheService cacheService;  // Injetando o serviço CacheService

    @PostMapping("/limparCache")
    public String limparCache() {
        cacheService.clearFirstLevelCache();  // Chamando o método para limpar o cache
        return "Cache de primeiro nível limpo!";
    }

    @PostMapping("/limparCache2")
    public String limparCache2() {
        cacheService.clearSecondLevelCache();  // Chamando o método para limpar o cache
        return "Cache de segundo nível limpo!";
    }

    @Autowired
    private ResultadoService service;

    @GetMapping
    public List<Resultado> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/algoritmo")
    public List<Resultado> listarPorAlgoritmo(@RequestParam String algoritmo) {
        return service.listarPorAlgoritmo(algoritmo);
    }

    // Método para realizar o teste para BubbleSort
    @PostMapping("/bubbleSort")
    public void realizarTesteBubbleSort() {
        int[] tamanhos = {10000, 100000, 500000};  // Tamanhos de amostra
        String[] cenarios = {"Crescente", "Decrescente", "Aleatorio", "StringsAleatorias"};

        for (int tamanho : tamanhos) {
            for (String cenario : cenarios) {
                System.out.println("Testando Bubble Sort com " + tamanho + " elementos no cenário: " + cenario);

                Object array = gerarVetor(tamanho, cenario);  // Usando Object para acomodar tanto int[] quanto String[]

                long tempoBubbleSort = 0; // Inicializa a variável de tempo

                // Verificar o tipo de array e usar o algoritmo adequado
                if (array instanceof int[]) {
                    tempoBubbleSort = medirTempoExecucao(() -> BubbleSort.sort((int[]) array), tamanho);  // Cast para int[] onde necessário
                } else if (array instanceof String[]) {
                    tempoBubbleSort = medirTempoExecucao(() -> Arrays.sort((String[]) array), tamanho);  // Ordenação de strings
                }

                System.out.println("Bubble Sort levou " + tempoBubbleSort + " microsegundos");

                // Salvar no banco de dados
                Resultado resultado = new Resultado("Bubble Sort", tempoBubbleSort, tamanho, cenario);
                service.salvar(resultado);
            }
        }
    }

    // Método para realizar o teste para MergeSort
    @PostMapping("/mergeSort")
    public void realizarTesteMergeSort() {
        int[] tamanhos = {10000, 100000, 500000};  // Tamanhos de amostra
        String[] cenarios = {"Crescente", "Decrescente", "Aleatorio", "StringsAleatorias"};

        for (int tamanho : tamanhos) {
            for (String cenario : cenarios) {
                System.out.println("Testando Merge Sort com " + tamanho + " elementos no cenário: " + cenario);

                Object array = gerarVetor(tamanho, cenario);  // Usando Object para acomodar tanto int[] quanto String[]

                long tempoMergeSort = 0; // Inicializa a variável de tempo

                // Verificar o tipo de array e usar o algoritmo adequado
                if (array instanceof int[]) {
                    tempoMergeSort = medirTempoExecucao(() -> MergeSort.sort((int[]) array), tamanho);
                } else if (array instanceof String[]) {
                    tempoMergeSort = medirTempoExecucao(() -> Arrays.sort((String[]) array), tamanho);
                }

                System.out.println("Merge Sort levou " + tempoMergeSort + " microsegundos");

                // Salvar no banco de dados
                Resultado resultado = new Resultado("Merge Sort", tempoMergeSort, tamanho, cenario);
                service.salvar(resultado);
            }
        }
    }

    // Método para realizar o teste para QuickSort
    @PostMapping("/quickSort")
    public void realizarTesteQuickSort() {
        int[] tamanhos = {10000, 100000, 500000};
        String[] cenarios = {"Crescente", "Decrescente", "Aleatorio", "StringsAleatorias"};
    
        System.out.println("Iniciando testes de Quick Sort...");
    
        long totalStartTime = System.nanoTime();
    
        for (int tamanho : tamanhos) {
            for (String cenario : cenarios) {
                try {
                    System.out.println("Testando Quick Sort com " + tamanho + " elementos no cenário: " + cenario);
    
                    Object array = gerarVetor(tamanho, cenario);
                    System.out.println("Vetor gerado para " + cenario + ": " + array.getClass().getSimpleName());
    
                    long tempoQuickSort = 0;
    
                    if (array instanceof int[]) {
                        tempoQuickSort = medirTempoExecucao(() -> QuickSort.sort((int[]) array), tamanho);
                    } else if (array instanceof String[]) {
                        tempoQuickSort = medirTempoExecucao(() -> Arrays.sort((String[]) array), tamanho);
                    }
    
                    System.out.println("Quick Sort levou " + tempoQuickSort + " microsegundos");
    
                    Resultado resultado = new Resultado("Quick Sort", tempoQuickSort, tamanho, cenario);
                    System.out.println("Tentando salvar no banco: " + resultado);
    
                    service.salvar(resultado);
                    System.out.println("Resultado salvo com sucesso: " + resultado);
    
                } catch (Exception e) {
                    System.err.println("Erro ao executar Quick Sort para " + cenario + " com tamanho " + tamanho);
                    e.printStackTrace();
                }
            }
        }
    
        long totalEndTime = System.nanoTime();
        System.out.println("Tempo total de execução dos testes de Quick Sort: " + (totalEndTime - totalStartTime) / 1000000 + " ms");
    }
    

    // Método para realizar o teste para ShellSort
    @PostMapping("/shellSort")
    public void realizarTesteShellSort() {
        int[] tamanhos = {10000, 100000, 500000};  // Tamanhos de amostra
        String[] cenarios = {"Crescente", "Decrescente", "Aleatorio", "StringsAleatorias"};

        for (int tamanho : tamanhos) {
            for (String cenario : cenarios) {
                System.out.println("Testando Shell Sort com " + tamanho + " elementos no cenário: " + cenario);

                Object array = gerarVetor(tamanho, cenario);  // Usando Object para acomodar tanto int[] quanto String[]

                long tempoShellSort = 0; // Inicializa a variável de tempo

                // Verificar o tipo de array e usar o algoritmo adequado
                if (array instanceof int[]) {
                    tempoShellSort = medirTempoExecucao(() -> ShellSort.sort((int[]) array), tamanho);
                } else if (array instanceof String[]) {
                    tempoShellSort = medirTempoExecucao(() -> Arrays.sort((String[]) array), tamanho);
                }

                System.out.println("Shell Sort levou " + tempoShellSort + " microsegundos");

                // Salvar no banco de dados
                Resultado resultado = new Resultado("Shell Sort", tempoShellSort, tamanho, cenario);
                service.salvar(resultado);
            }
        }
    }

    // Método auxiliar para gerar os vetores conforme os cenários
    private Object gerarVetor(int tamanho, String cenario) {
        try {
            switch (cenario) {
                case "Crescente":
                    return Cenarios.vetorCrescente(tamanho);
                case "Decrescente":
                    return Cenarios.vetorDecrescente(tamanho);
                case "Aleatorio":
                    return Cenarios.vetorAleatorio(tamanho);
                case "StringsAleatorias":
                    return Cenarios.vetorStringsAleatorias(tamanho);
                default:
                    throw new IllegalArgumentException("Cenário desconhecido: " + cenario);
            }
        } catch (Exception e) {
            System.err.println("Erro ao gerar vetor para o cenário: " + cenario + " e tamanho: " + tamanho);
            throw e;
        }
    }
    

    // Método para medir o tempo de execução
    private long medirTempoExecucao(Runnable algoritmo, int tamanho) {
        long startTime = System.nanoTime();
        algoritmo.run();
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1000;  // Converte de nanosegundos para microsegundos
    }
}
