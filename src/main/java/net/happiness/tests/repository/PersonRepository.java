package net.happiness.tests.repository;

import net.happiness.tests.entity.Person;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends DistributedRepository<Person> {
}
