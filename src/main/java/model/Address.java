package model;

import base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SoftDelete;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@SoftDelete
@ToString(callSuper = true)

@Entity(name = "address")
public class Address extends BaseEntity<Long> {
    @Column(name = "province")
    private String province;

    @Column(name = "city")
    private String city;

    @Column(name = "detail")
    private String detail;


    @ToString.Exclude
    @OneToOne(mappedBy = "address")
    private Order order;

}
