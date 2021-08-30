package pl.cm.core.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = Visit.TABLE_NAME)
public class Visit extends BaseEntity{
    public static final String TABLE_NAME = "VISIT";


    @Column(name = "doctor_name")
    String doctorName;

    @Column(name = "description")
    String description;

    @Column(name = "date_of_visit")
    LocalDateTime dateOfVisit;

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateOfVisit() {
        return dateOfVisit;
    }

    public void setDateOfVisit(LocalDateTime dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
    }
}
