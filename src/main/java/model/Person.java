package model;

import base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SoftDelete;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@SoftDelete
@ToString(callSuper = true)

@Entity
@Table(name = "person")
public class Person extends BaseEntity<Long> {

    @Column(name = "firstName")
    private String firstName ;

    @Column(name = "lastName")
    private String lastName ;

    @Column(name = "email",unique = true)
    public String email ;

    @ToString.Exclude
    private String password ;

    @ToString.Exclude
    private LocalDateTime registrationDateTime ;

}
