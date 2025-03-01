package com.td.demo.repository;

import com.td.demo.model.Metier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetierRepository extends JpaRepository<Metier, Long> {
}
