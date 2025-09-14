package com.jpd.model;

import java.util.List;

import org.springframework.data.annotation.Id;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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

    private String name;   // Ví dụ: "Basic", "Advanced", "Premium"
    private double price;  // 100000, 330000, 500000
    private int duration;  // số tháng (1, 3, 12)
    private double storage; // dung lượng GB
    @OneToMany(mappedBy = "plan",cascade = CascadeType.ALL)
    private List<UserSubscription>userSubscriptions;
}