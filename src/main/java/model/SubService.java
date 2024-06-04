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
@Table(name = "sub_service")
public class SubService extends BaseEntity<Long> {

    @Column(name = "name")
    private String name;

    @Column(name = "base_Price")
    private double basePrice;

    @Column(name = "description")
    private String description;


    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service; // parent service


    @ToString.Exclude
    @ManyToMany(mappedBy ="subServices")
    private List<Specialist> specialists; // specialists offering this sub-service
}
