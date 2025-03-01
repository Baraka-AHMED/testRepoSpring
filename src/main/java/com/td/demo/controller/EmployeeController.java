package com.td.demo.controller;

import com.td.demo.model.Employee;
import com.td.demo.repository.EmployeeRepository;
import com.td.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public Iterable<Employee> findAllEmployee() {
        return this.employeeRepository.findAll();
    }

    @PostMapping
    public Employee addOneEmployee(@RequestBody Employee employee) {
        return this.employeeRepository.save(employee);
    }

    @PostMapping("/add")
    public Employee ajouterEmployee(
            @RequestParam String nom,
            @RequestParam String prenom,
            @RequestParam String birthDate, // Date en format YYYY-MM-DD
            @RequestParam Long metierId) {

        LocalDate parsedBirthDate = LocalDate.parse(birthDate); // Convertir String en LocalDate
        return employeeService.ajouterEmployee(nom, prenom, parsedBirthDate, metierId);
    }

    @GetMapping("/by-lastname/{lastName}")
    public List<Employee> getEmployeeByLastName(@PathVariable String lastName) {
        return this.employeeRepository.findByLastName(lastName);
    }
}
