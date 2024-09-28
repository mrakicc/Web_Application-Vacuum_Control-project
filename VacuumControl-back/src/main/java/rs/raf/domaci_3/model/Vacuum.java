package rs.raf.domaci_3.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Getter
@Setter
public class Vacuum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String status;
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="user_id")
    private User addedBy;
    @Column
    private boolean active;
    @Column
    private Date dateAdded;

    private boolean disabled;
}
