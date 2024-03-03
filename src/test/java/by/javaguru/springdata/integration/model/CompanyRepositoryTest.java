package by.javaguru.springdata.integration.model;

import by.javaguru.springdata.integration.annotation.IT;
import by.javaguru.springdata.model.entity.Company;
import by.javaguru.springdata.model.repository.CompanyRepository;
import jakarta.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;


@IT
class CompanyRepositoryTest {

    private static final Integer APPLE_ID = 5;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    void findById() {
        var company = entityManager.find(Company.class, 1);
        assertNotNull(company);
        assertThat(company.getLocales()).hasSize(2);
    }

    @Test
    void save() {
        var company = Company.builder()
                .name("Apple")
                .locales(Map.of(
                        "ru", "описание",
                        "en", "description"
                ))
                .build();
    }

    @Test
    void delete() {
        var maybeCompany = companyRepository.findById(APPLE_ID);
        assertTrue(maybeCompany.isPresent());
        maybeCompany.ifPresent(companyRepository::delete);
        entityManager.flush();
        assertTrue(companyRepository.findById((APPLE_ID)).isEmpty());
    }

    @Test
    void checkFindByQueries() {
        companyRepository.findByName("google");
        companyRepository.findByNameContainingIgnoreCase("a");
    }
}
