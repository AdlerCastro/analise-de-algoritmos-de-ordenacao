package com.example.projeto_backend.utils;


import java.util.Random;

public class Cenarios {

    // Método para gerar vetor de inteiros em ordem crescente
    public static int[] vetorCrescente(int tamanho) {
        int[] array = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            array[i] = i;  // Preenche com valores crescentes
        }
        return array;
    }

    // Método para gerar vetor de inteiros em ordem decrescente
    public static int[] vetorDecrescente(int tamanho) {
        int[] array = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            array[i] = tamanho - i - 1;  // Preenche com valores decrescentes
        }
        return array;
    }

    // Método para gerar vetor de inteiros aleatórios
    public static int[] vetorAleatorio(int tamanho) {
        Random random = new Random();
        int[] array = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            array[i] = random.nextInt(1000000);  // Preenche com valores aleatórios
        }
        return array;
    }

    // Método para gerar vetor de strings aleatórias
    public static String[] vetorStringsAleatorias(int tamanho) {
        Random random = new Random();
        String[] array = new String[tamanho];
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        
        for (int i = 0; i < tamanho; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 10; j++) {  // Cada string terá 10 caracteres
                int index = random.nextInt(characters.length());
                sb.append(characters.charAt(index));
            }
            array[i] = sb.toString();  // Preenche com palavras aleatórias
        }
        return array;
    }
}
