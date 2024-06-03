package model;

import base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import myEnum.Role;
import org.hibernate.annotations.SoftDelete;

import java.util.Date;

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
    private String username;

    private String password;
}
