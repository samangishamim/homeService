package model;

import base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SoftDelete;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@SoftDelete
@ToString(callSuper = true)

@Table(name = "admin")
@Entity
public class Admin extends BaseEntity<Long> {
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
}
