package example.model;

import java.time.LocalDate;

public class PatientDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String serialNumber;

    private LocalDate birthday;

    public PatientDTO(){}

    public PatientDTO(String firstName, String lastName, String serialNumber, LocalDate birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.serialNumber = serialNumber;
        this.birthday = birthday;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}

