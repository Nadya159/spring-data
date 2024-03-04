package by.javaguru.springdata.model.dto;

import org.springframework.beans.factory.annotation.Value;

public interface PersonalInfoImpl {
    String getFirstname();
    String getLastname();
    String getBirthDate();

    @Value("#{target.firstname + ' ' + target.lastname}")
    String getFullName();
}
