package by.javaguru.springdata.model.repository;

import by.javaguru.springdata.model.entity.Company;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@Repository
public class CompanyRepository implements CrudRepository<Integer, Company>{

    @PostConstruct
    private void init(){
        log.warn("Init company repository");
    }
    @Override
    public Optional<Company> findById(Integer id) {
        return Optional.of(new Company(id, null, Collections.emptyMap()));
    }

    @Override
    public void delete(Company entity) {
        System.out.println("delete method");
    }
}
