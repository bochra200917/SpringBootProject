package com.example.gestimmob.dao.repositories;

import com.example.gestimmob.dao.entities.Bien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BienRepository extends JpaRepository<Bien, Long> {
}