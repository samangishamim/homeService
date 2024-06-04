package model;

import base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SoftDelete;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

@SoftDelete
@ToString(callSuper = true)

@Entity
@Table(name = "comment")
public class Comment extends BaseEntity<Long> {
    @Column(name = "comment")
    private String comment;

    @Column(name = "rating")
    private int rating; // customer's rating for the specialist


    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "proposal_id")
    private Proposal proposal; // order for which the comment is made


    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
