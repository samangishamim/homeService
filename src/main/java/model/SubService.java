package model;

import base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SoftDelete;

import java.util.ArrayList;
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
    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(
            name = "specialist_sub_service",
            joinColumns = @JoinColumn(name = "sub_service_id"),
            inverseJoinColumns = @JoinColumn(name = "specialist_id")
    )
    private List<Specialist> specialists; // specialists offering this sub-service

    public void addSpecialist(Specialist specialist){
        if (specialists==null){
            specialists=new ArrayList<>();
        }
        specialists.add(specialist);
    }

    public void removeSpecialist(Specialist specialist){
        specialists.remove(specialist);
    }
}
