package rs.raf.domaci_3.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Getter
@Setter
public class ErrorMassage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long userId;
    @Column
    private Long vacuumId;
    @Column
    private Date date;
    @Column
    private String operation;
    @Column
    private String message;

    public ErrorMassage() {

    }

    public ErrorMassage(Long userId, Long vacuumId, Date date, String operation, String message) {
        this.userId = userId;
        this.vacuumId = vacuumId;
        this.date = date;
        this.operation = operation;
        this.message = message;
    }

}
