package by.javaguru.springdata.integration.model;

import by.javaguru.springdata.integration.annotation.IT;
import by.javaguru.springdata.model.entity.Company;
import by.javaguru.springdata.model.entity.Role;
import by.javaguru.springdata.model.repository.CompanyRepository;
import jakarta.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


@IT
class CompanyRepositoryTest {

    private static final Integer COMPANY_ID = 5;

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
        var maybeCompany = companyRepository.findById(COMPANY_ID);
        assertTrue(maybeCompany.isPresent());
        maybeCompany.ifPresent(companyRepository::delete);
        entityManager.flush();
        assertTrue(companyRepository.findById((COMPANY_ID)).isEmpty());
    }

    @Test
    void checkFindByQueries() {
        companyRepository.findByName("google");
        companyRepository.findByNameContainingIgnoreCase("a");
    }

    @Test
    void checkUpdateName() {
        var company = companyRepository.getReferenceById(COMPANY_ID);
        var name = companyRepository.findNameById(COMPANY_ID);
        assertEquals(name, company.getName());
        companyRepository.updateName("Test!", COMPANY_ID);

        company = companyRepository.getReferenceById(COMPANY_ID);
        assertEquals("Test!", company.getName());
    }

    @Test
    void checkDeleteByBeginA() {
        List<Integer> companies = companyRepository.findByNameBeginA();
        assertEquals(2, companies.size());
        /*var resultCount = companyRepository.deleteCompaniesById(companies);
        assertEquals(2, resultCount);*/
    }
}
