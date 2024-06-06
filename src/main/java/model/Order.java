package model;

import base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import myEnum.OrderStatus;
import org.hibernate.annotations.SoftDelete;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@SoftDelete
@ToString(callSuper = true)

@Entity
@Table(name = "my_order")
public class Order extends BaseEntity<Long> {

    @Column(name = "description")
    private String description;

    @Column(name = "proposed_Price")
    private double proposedPrice;

    @Column(name = "workDate")
    private LocalDateTime workDate;


    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "sub_service_id")
    private SubService subService;

    @ToString.Exclude
    @OneToMany(mappedBy = "order")
    private List<Proposal> proposals;

    @Enumerated(value=EnumType.STRING)
    private OrderStatus status;


    @ToString.Exclude
    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "address_id")
    private Address address;
}
