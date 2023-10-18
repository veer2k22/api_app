package com.api.controller;

import com.api.entities.Employee;
import com.api.payload.EmployeeTaxDTO;
import com.api.repositories.EmployeeRepository;
import com.api.service.EmployeeTaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tax")
public class TaxController {

    @Autowired
    private EmployeeTaxService employeeTaxService;

    @Autowired
    private EmployeeRepository employeeRepository; // Inject the repository for accessing employee data

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<EmployeeTaxDTO> getEmployeeTax(@PathVariable Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId).orElse(null);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        EmployeeTaxDTO employeeTaxDTO = employeeTaxService.calculateTax(employee);

        return ResponseEntity.ok(employeeTaxDTO);
    }
}

