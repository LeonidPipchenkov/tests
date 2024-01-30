package net.happiness.tests.service;

import net.happiness.tests.entity.Address;
import net.happiness.tests.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private AddressRepository repository;

    @Autowired
    public AddressService(AddressRepository repository) {
        this.repository = repository;
    }

    public Address createAddress(final String name) {
        final Address address = new Address();
        address.setName(name);
        return repository.save(address);
    }

    public Address findAddress(final String id) {
        return repository.findById(id).orElse(null);
    }

}
