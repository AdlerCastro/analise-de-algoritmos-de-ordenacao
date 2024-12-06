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
    private CacheService cacheService;

    @PostMapping("/limparCache")
    public String limparCache() {
        cacheService.clearFirstLevelCache();  
        return "Cache de primeiro nível limpo!";
    }

    @PostMapping("/limparCache2")
    public String limparCache2() {
        cacheService.clearSecondLevelCache();  
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

    @PostMapping("/bubbleSort")
    public void realizarTesteBubbleSort() {
        int[] tamanhos = {10000, 100000, 500000}; 
        String[] cenarios = {"Crescente", "Decrescente", "Aleatorio", "StringsAleatorias"};

        for (int tamanho : tamanhos) {
            for (String cenario : cenarios) {
                System.out.println("Testando Bubble Sort com " + tamanho + " elementos no cenário: " + cenario);

                Object array = gerarVetor(tamanho, cenario);

                long tempoBubbleSort = 0;

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

    @PostMapping("/quickSort")
    public void realizarTesteQuickSort() {
        int[] tamanhos = {10000, 100000, 500000};
        String[] cenarios = {"Crescente", "Decrescente", "Aleatorio", "StringsAleatorias"};
    
        System.out.println("Iniciando testes de Quick Sort...");
    
        for (int tamanho : tamanhos) {
            for (String cenario : cenarios) {
                try {
                    System.out.println("\nTestando Quick Sort com " + tamanho + " elementos no cenário: " + cenario);
    
                    // Log de memória antes
                    logMemoria("Antes de gerar o vetor");
    
                    // Gera o vetor
                    Object array = gerarVetor(tamanho, cenario);
                    if (array == null) {
                        throw new IllegalArgumentException("Erro: O vetor gerado para " + cenario + " com tamanho " + tamanho + " é nulo.");
                    }
    
                    System.out.println("Vetor gerado para " + cenario + ": " + array.getClass().getSimpleName());
    
                    long tempoQuickSort = 0;
    
                    // Verifica o tipo do vetor e executa o Quick Sort ou Arrays.sort
                    if (array instanceof int[]) {
                        tempoQuickSort = medirTempoExecucao(() -> QuickSort.sort((int[]) array), tamanho);
                    } else if (array instanceof String[]) {
                        tempoQuickSort = medirTempoExecucao(() -> Arrays.sort((String[]) array), tamanho);
                    }
    
                    System.out.println("Quick Sort levou " + tempoQuickSort + " microsegundos");
    
                    // Cria o resultado e salva no banco
                    Resultado resultado = new Resultado("Quick Sort", tempoQuickSort, tamanho, cenario);
                    service.salvar(resultado);
                    System.out.println("Resultado salvo com sucesso: " + resultado);
    
                    // Log de memória após salvar
                    logMemoria("Após salvar no banco");
    
                } catch (OutOfMemoryError e) {
                    System.err.println("Erro de memória ao processar " + cenario + " com tamanho " + tamanho);
                    e.printStackTrace();
                } catch (Exception e) {
                    System.err.println("Erro inesperado ao processar " + cenario + " com tamanho " + tamanho);
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Todos os testes de Quick Sort concluídos.");
    }
    
    // Método para medir a memória disponível
    private void logMemoria(String mensagem) {
        Runtime runtime = Runtime.getRuntime();
        long memoriaLivre = runtime.freeMemory() / (1024 * 1024);
        long memoriaTotal = runtime.totalMemory() / (1024 * 1024);
        long memoriaUsada = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024);
    
        System.out.println("[" + mensagem + "] Memória usada: " + memoriaUsada + " MB, Memória livre: " + memoriaLivre + " MB, Memória total: " + memoriaTotal + " MB");
    }
    
    
    
    @PostMapping("/shellSort")
    public void realizarTesteShellSort() {
        int[] tamanhos = {10000, 100000, 500000};
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
        return (endTime - startTime) / 1000000;  // Converte de nanosegundos para milissegundos
    }
}
