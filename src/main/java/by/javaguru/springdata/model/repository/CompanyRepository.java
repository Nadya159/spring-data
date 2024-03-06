package by.javaguru.springdata.model.repository;

import by.javaguru.springdata.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    @Query("select c from Company c " +
           "join fetch c.locales cl " +
           "where c.name = :name")
    Optional<Company> findByName(@Param("name") String name);

    List<Company> findByNameContainingIgnoreCase(String fragment);

    /**
     * Methods with my @Query:
     * 1) FIND Name of Company by ID
     * 2) UPDATE Name of found Company
     */
    @Query("select c.name from Company c " +
           "where c.id = :id")
    String findNameById(Integer id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Company c " +
           "set c.name = :name " +
           "where c.id = :id")
    void updateNameById(String name, Integer id);

    /**
     * Method with PartTreeJpaQuery
     * @param firstLetter without IgnoreCase
     * @return List of Companies, whose name begins with a given letter
     */
    List<Company> findByNameStartingWith(String firstLetter);
}
