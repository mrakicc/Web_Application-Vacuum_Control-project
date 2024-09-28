package rs.raf.domaci_3.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column
    private String name;

    @Column
    private String last_name;

    @Column
    private String email;


    @Column(updatable = false)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "users_permissions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private List<Permission> permissions;

    @JsonManagedReference
    @OneToMany(mappedBy = "addedBy",cascade = CascadeType.DETACH)
    private List<Vacuum> vacuums;
}
