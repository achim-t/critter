package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private UserService userService;
    @Autowired
    private PetService petService;
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertDTOToSchedule(scheduleDTO);
        return convertScheduleToDTO(scheduleService.saveSchedule(schedule));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getSchedules();
        return convertSchedulesToDTOs(schedules);
    }

    private ScheduleDTO convertScheduleToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        List<Pet> pets = schedule.getPets();

        if (pets != null) {
            List<Long> petdIds = pets
                    .stream()
                    .map(pet -> pet.getId())
                    .collect(Collectors.toList());
            scheduleDTO.setPetIds(petdIds);
        }
        List<Employee> employees = schedule.getEmployees();
        if (employees != null) {
            List<Long> employeeIds = employees
                    .stream()
                    .map(employee -> employee.getId())
                    .collect(Collectors.toList());
            scheduleDTO.setEmployeeIds(employeeIds);
        }
        return scheduleDTO;
    }

    private Schedule convertDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        List<Long> petIds = scheduleDTO.getPetIds();
        if (petIds != null) {
            schedule.setPets(petService.getPetsByIds(petIds));
        }

        List<Long> employeeIds = scheduleDTO.getEmployeeIds();

        if (employeeIds != null) {
            schedule.setEmployees(userService.getEmployeesByIds(employeeIds));
        }
        return schedule;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getSchedulesForPet(petId);
        return convertSchedulesToDTOs(schedules);
    }

    private List<ScheduleDTO> convertSchedulesToDTOs(List<Schedule> schedules) {
        List<ScheduleDTO> scheduleDTOS = schedules
                .stream()
                .map(schedule -> convertScheduleToDTO(schedule))
                .collect(Collectors.toList());
        return scheduleDTOS;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getSchedulesForEmployee(employeeId);
        return convertSchedulesToDTOs(schedules);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getScheduleForCustomer(customerId);
        return convertSchedulesToDTOs(schedules);
    }
}
