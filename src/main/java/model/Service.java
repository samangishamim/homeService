package model;


import base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SoftDelete;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

@SoftDelete
@ToString(callSuper = true)

@Entity
@Table(name = "service")
public class Service extends BaseEntity<Long> {

    @Column(name = "name")
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "service",fetch = FetchType.EAGER)
    private List<SubService> subServices;
}
