package com.jpd.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // hoặc JOINED tùy chọn
@DiscriminatorColumn(name = "qtype") // cột phân biệt loại câu hỏi
@Data
public abstract class ModuleContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Enumerated(EnumType.STRING)
    private TypeOfContent typeOfContent;
     
    @ManyToOne
    @JoinColumn(name = "module_id")
    @JsonBackReference
    protected Module module;
    @OneToMany(mappedBy = "moduleContent",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Customer_Question> customer_Question;
    
    
}