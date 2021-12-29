package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PetRepository petRepository;

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getSchedules() {
        return scheduleRepository.findAll();
    }

    public  List<Schedule> getScheduleForCustomer(long id) {
        List<Schedule> customerSchedules = new ArrayList<>();
        List<Pet> pets = customerRepository.getOne(id).getPets();

        for (Pet pet : pets) {
            customerSchedules.addAll(scheduleRepository.findByPets(pet));
        }
        return customerSchedules;
    }

    public List<Schedule> getSchedulesForEmployee(long id) {
        return scheduleRepository.findByEmployees(employeeRepository.getOne(id));
    }

    public List<Schedule> getSchedulesForPet(long id) {
        return scheduleRepository.findByPets(petRepository.getOne(id));
    }
}
