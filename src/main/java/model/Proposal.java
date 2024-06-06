package model;

import base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SoftDelete;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

@SoftDelete
@ToString(callSuper = true)

@Entity
@Table(name = "proposal")
public class Proposal extends BaseEntity<Long> {

    @Column(name = "proposal_Date_Time")
    private LocalDateTime proposalDateTime;

    @Column(name = "proposed_Price")
    private double proposedPrice;

    @Column(name = "duration")
    private int duration;

    @Column(name = "is_accepted")
    private boolean isAccepted;


    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;


    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "specialist_id")
    private Specialist specialist;



}
