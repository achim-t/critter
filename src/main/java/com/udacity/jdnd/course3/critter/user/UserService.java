package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PetRepository petRepository;

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomerByPet(long petId) {
        Pet pet = petRepository.getOne(petId);
        return customerRepository.findByPets(pet);
    }

    public Customer getCustomer(long id) {
        return customerRepository.getOne(id);
    }
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }
    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public Employee getEmployee(long id){
        return employeeRepository.getOne(id);
    }

    public Employee setEmployeeAvailability(Set<DayOfWeek> days, long id) {
        Employee employee = employeeRepository.getOne(id);
        employee.setWorkDays(days);
        return employeeRepository.save(employee);
    }

    public List<Employee> findEmployeesForService(EmployeeRequestDTO employeeRequest){
        Set<EmployeeSkill> skills = employeeRequest.getSkills();
        LocalDate date = employeeRequest.getDate();
        List<Employee> employees = employeeRepository.findAllByWorkDaysAndSkillsIn(date.getDayOfWeek(), skills)
                .stream()
                .map(employee -> employee)
                .filter(employee -> employee.getSkills().containsAll(skills))
                .collect(Collectors.toList());
        return employees;
    }

    public List<Employee> getEmployeesByIds(List<Long> ids){
        return employeeRepository.findAllById(ids);
    }
}
