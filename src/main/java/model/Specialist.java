package model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import myEnum.Status;
import org.hibernate.annotations.SoftDelete;

import java.util.List;
import java.util.NavigableMap;

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


    @Column(name = "specialist_status")
    @Enumerated(value = EnumType.STRING)
    private Status specialistStatus;


    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "specialist_subservice",
            joinColumns = @JoinColumn(name = "specialist_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_service_id"))

    private List<SubService> subServices; // services offered by the specialist

    @ToString.Exclude
    @OneToMany(mappedBy = "specialist")
    private List<Proposal> proposalList;
}
