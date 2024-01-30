package net.happiness.tests.service;

import net.happiness.tests.entity.Address;
import net.happiness.tests.entity.Person;
import net.happiness.tests.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private PersonRepository repository;
    private AddressService addressService;

    @Autowired
    public PersonService(PersonRepository repository, AddressService addressService) {
        this.repository = repository;
        this.addressService = addressService;
    }

    public Person createPerson(final String addressId, final String personName) {
        final Address address = addressService.findAddress(addressId);
        if (address == null) {
            return null;
        }
        Person person = new Person();
        person.setAddress(address);
        person.setName(personName);
        return repository.save(person);
    }

}
