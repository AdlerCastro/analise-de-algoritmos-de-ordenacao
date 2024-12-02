package com.example.projeto_backend.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;

@Service
public class CacheService {

    @Autowired
    private EntityManager entityManager;

    // Método para limpar o cache de primeiro nível
    public void clearFirstLevelCache() {
        Session session = entityManager.unwrap(Session.class);  // Obtém a sessão do Hibernate
        session.clear();  // Limpa o cache de primeiro nível
    }

    @Autowired
    private SessionFactory sessionFactory;

    public void clearSecondLevelCache() {
        sessionFactory.getCache().evictAllRegions();  // Limpa todas as regiões do cache de segundo nível
    }
}

