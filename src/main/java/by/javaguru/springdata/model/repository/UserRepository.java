package by.javaguru.springdata.model.repository;

import by.javaguru.springdata.model.dto.PersonalInfo;
import by.javaguru.springdata.model.dto.PersonalInfoImpl;
import by.javaguru.springdata.model.entity.Role;
import by.javaguru.springdata.model.entity.User;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u " +
           "where u.firstname like %:firstname% and u.lastname like %:lastname%")
    List<User> findAllBy(String firstname, String lastname);

    @Query(value = "SELECT u.* FROM users u WHERE u.username = :username",
            nativeQuery = true)
    List<User> findAllByUsername(String username);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update User u " +
           "set u.role = :role " +
           "where u.id in (:ids)")
    int updateRole(Role role, Long... ids);

    Optional<User> findTopByOrderByIdDesc();

    @QueryHints(@QueryHint(name = "org.hibernate.fetchSize", value = "50"))
    @Lock(LockModeType.OPTIMISTIC)
    List<User> findTop3ByBirthDateBefore(LocalDate birthDate, Sort sort);

    //@EntityGraph("User.company") - именованный граф
    @EntityGraph(attributePaths = {"company", "company.locales"})
    @Query(value = "select u from User u",
            countQuery = "select count(distinct u.firstname) from User u")
    Page<User> findAllBy(Pageable pageable);        //можно использовать Slice, но он без count

    //List<PersonalInfo> findAllByCompanyId(Integer companyId);

    //<T> List<T> findAllByCompanyId(Integer companyId, Class<T> clazz);

    @Query(value = "SELECT firstname, lastname, birth_date " +
                   "FROM users " +
                   "WHERE company_id = :companyId",
    nativeQuery = true)
    List<PersonalInfoImpl> findAllByCompanyId(Integer companyId);
}
