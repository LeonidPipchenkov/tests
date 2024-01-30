package net.happiness.tests.repository;

import net.happiness.tests.entity.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends DistributedRepository<Address> {
}
