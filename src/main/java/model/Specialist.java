package model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import myEnum.Status;
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
@Table(name = "specialist")
public class Specialist extends Person {

    @ToString.Exclude
    @Column(name = "photo")
    private byte[] photo; // max 300KB, jpg format

    @Column(name = "score")
    private int score; // initial score is 0

    @Column(name = "enable")
    private boolean enable;

    @Column(name = "credit")
    private double credit = 0;

    @Column(name = "specialist_status")
    @Enumerated(value = EnumType.STRING)
    private Status specialistStatus;


    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "specialist_sub_service",
            joinColumns = @JoinColumn(name = "specialist_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_service_id"))

    private List<SubService> subServices; // services offered by the specialist

    @ToString.Exclude
    @OneToMany(mappedBy = "specialist")
    private List<Proposal> proposalList;


}
