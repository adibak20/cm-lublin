package pl.cm.core.model.entities;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = Patient.TABLE_NAME)
public class Patient extends BaseEntity
{
    final static String TABLE_NAME = "PATIENT";

    @Column()
    private String firstName;

    @Column()
    private String lastName;

    @Column()
    private String serialNumber;

    @Column()
    private LocalDate birthday;

    @Column
    private String keywords;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "patient_id")
    private Set<Visit> visits = new HashSet<>();

    @Override
    protected void prePersist() {
        this.techInsTs = LocalDateTime.now();
        this.lockVersion = 0L;
        this.keywords = Strings.nullToEmpty(firstName) + Strings.nullToEmpty(lastName) + Strings.nullToEmpty(serialNumber);
    }

    @Override
    protected void preUpdate() {
        this.techUpdTs = LocalDateTime.now();
        this.keywords = Strings.nullToEmpty(firstName) + Strings.nullToEmpty(lastName) + Strings.nullToEmpty(serialNumber);
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

    public Set<Visit> getVisits() {
        return visits;
    }

    public void addVisit(Visit visit) {
        visits.add(visit);
    }

    public void setVisits(Set<Visit> visits) {
        this.visits = visits;
    }
}
