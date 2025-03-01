package com.td.demo.service;
import com.td.demo.model.Employee;
import com.td.demo.model.Metier;
import com.td.demo.repository.EmployeeRepository;
import com.td.demo.repository.MetierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MetierRepository metierRepository;

    public Employee ajouterEmployee(String nom, String prenom, LocalDate birthDate, Long metierId) {
        Metier metier = metierRepository.findById(metierId)
                .orElseThrow(() -> new RuntimeException("Métier non trouvé"));

        Employee employee = new Employee(nom, prenom, birthDate, metier);
        return employeeRepository.save(employee);
    }


    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
