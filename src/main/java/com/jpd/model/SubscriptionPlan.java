package com.jpd.model;

import java.util.List;



import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Long planId;
    @ManyToOne()
    @JoinColumn(name="creator_id")
    private Creator creator;
    private String name;   // Ví dụ: "Basic", "Advanced", "Premium"
    private double price;  // 100000, 330000, 500000
    private int duration;  // số tháng (1, 3, 12)
    private double storage; // dung lượng GB
    @OneToMany(mappedBy = "plan",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<UserSubscription>userSubscriptions;
}