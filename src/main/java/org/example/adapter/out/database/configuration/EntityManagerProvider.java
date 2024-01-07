package org.example.adapter.out.database.configuration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class EntityManagerProvider {
    @PersistenceContext
    private EntityManager entityManager;
}
