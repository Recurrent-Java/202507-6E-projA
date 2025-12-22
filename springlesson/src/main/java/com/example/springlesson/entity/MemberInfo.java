package com.example.springlesson.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name="MEMBER")
@Data
public class MemberInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id") 
    private Integer id;

    @Column(name="full_name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="registration_date")
    private java.time.LocalDateTime registerDate;
}