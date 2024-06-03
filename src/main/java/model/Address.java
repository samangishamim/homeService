package model;

import base.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SoftDelete;

@Getter
@Setter
@NoArgsConstructor

@SuperBuilder
@SoftDelete
@ToString(callSuper = true)

@Entity(name = "address")
public class Address extends BaseEntity<Long> {

    private String province;

    private String city;

    private String detail;


    @ToString.Exclude
    @OneToOne(mappedBy = "address")
    private Order order;

}
