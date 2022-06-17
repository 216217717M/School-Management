/* EmployeeServiceImpl.java
Service Implementation for the Employee
Author: Sharfaa Sedick Anthony 220041571
Date: 14 June 2022 */

package za.ac.cput.schoolmanagement.services.employeeService.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.schoolmanagement.domain.Employee;
import za.ac.cput.schoolmanagement.repository.EmployeeRepository;
import za.ac.cput.schoolmanagement.services.employeeService.IEmployeeService;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private final EmployeeRepository repository;

    @Autowired public EmployeeServiceImpl(EmployeeRepository repository){
        this.repository = repository;
    }

    @Override
    public Employee save(Employee employee) {return this.repository.save(employee);}

    @Override
    public Optional<Employee> read(String id) {
        return this.repository.findById(id);
    }

    @Override
    public void delete(Employee employee) {
        this.repository.delete(employee);
    }

    @Override
    public List<Employee> findAll() {
        return this.repository.findAll();
    }

    public void deleteById(String id) {
        repository.deleteById(id);
        Optional<Employee> employee = read(id);
        if (employee.isPresent()) {
            delete(employee.get());
        }
    }
}