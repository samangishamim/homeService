package model;

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
@Table(name = "customer")
public class Customer extends Person {

    @Column(name = "credit")
    private double credit=0;


    @ToString.Exclude
    @OneToMany(mappedBy = "customer")
    private List<Order> orderList;


}
