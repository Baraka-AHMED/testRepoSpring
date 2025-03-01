package com.td.demo.repository;

import com.td.demo.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//Test
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {}