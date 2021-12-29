package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Pet savePet(Pet pet) {
        Pet persistedPet = petRepository.save(pet);

        Customer customer = persistedPet.getCustomer();
        customer.addPet(persistedPet);
        customerRepository.save(customer);
        return persistedPet;
    }

    public Pet getPet(long id) {
        return petRepository.getOne(id);
    }
    public List<Pet> getPets() {
        return petRepository.findAll();
    }
    public List<Pet> getPetsByCustomer(long customerId) {
        return petRepository.findAllByCustomerId(customerId);
    }
    public List<Pet> getPetsByIds(List<Long> ids){
        return petRepository.findAllById(ids);
    }
}
