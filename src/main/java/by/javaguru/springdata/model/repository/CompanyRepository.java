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

    @Query("select c.name from Company c " +
           "where c.id = :id")
    String findNameById(Integer id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Company c " +
           "set c.name = :name " +
           "where c.id = :id")
    int updateName(String name, Integer id);

    @Query(value = "SELECT c.id FROM Company c " +
           "WHERE c.name LIKE 'A%'",
            nativeQuery = true)
    List<Integer> findByNameBeginA();

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    /*@Query("update User u " +
           "set u.role = :role " +
           "where u.id in (:ids)")*/
    int deleteCompaniesById(List<Integer> ids);
}
