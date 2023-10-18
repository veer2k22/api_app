package com.api.service;

import com.api.entities.Employee;
import com.api.payload.EmployeeTaxDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmployeeTaxService {

    public EmployeeTaxDTO calculateTax(Employee employee) {
        Double yearlySalary = calculateYearlySalary(employee);
        Double taxAmount = calculateTaxAmount(yearlySalary);
        Double cessAmount = calculateCessAmount(yearlySalary);

        EmployeeTaxDTO employeeTaxDTO = new EmployeeTaxDTO();
        employeeTaxDTO.setEmployeeCode(employee.getEmployeeId());
        employeeTaxDTO.setFirstName(employee.getFirstName());
        employeeTaxDTO.setLastName(employee.getLastName());
        employeeTaxDTO.setYearlySalary(yearlySalary);
        employeeTaxDTO.setTaxAmount(taxAmount);
        employeeTaxDTO.setCessAmount(cessAmount);

        return employeeTaxDTO;
    }

    private Double calculateYearlySalary(Employee employee) {
        // Calculate the yearly salary based on date of joining and monthly salary
        LocalDate dateOfJoining = LocalDate.parse(employee.getDateOfJoining());
        LocalDate currentDate = LocalDate.now();

        int months = (currentDate.getYear() - dateOfJoining.getYear()) * 12 + currentDate.getMonthValue() - dateOfJoining.getMonthValue();
        double monthlySalary = employee.getSalary();

        if (dateOfJoining.getDayOfMonth() > 15) {
            months--; // Subtract a month if joined after the 15th of the month.
        }

        return monthlySalary * months;
    }

    private Double calculateTaxAmount(Double yearlySalary) {
        if (yearlySalary <= 250000) {
            return 0.0;
        } else if (yearlySalary <= 500000) {
            return (yearlySalary - 250000) * 0.05;
        } else if (yearlySalary <= 1000000) {
            return 25000 + (yearlySalary - 500000) * 0.1;
        } else {
            return 75000 + (yearlySalary - 1000000) * 0.2;
        }
    }

    private Double calculateCessAmount(Double yearlySalary) {
        if (yearlySalary > 2500000) {
            return (yearlySalary - 2500000) * 0.02;
        } else {
            return 0.0;
        }
    }
}

