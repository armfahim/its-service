package com.its.service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.io.Serial;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "SHOP_BRANCH")
public class ShopBranch extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "BRANCH_NAME", nullable = true)
    private String branchName;

    @Column(name = "CITY", nullable = true)
    private String city;

    @Column(name = "ADDRESS", nullable = true)
    private String address;
}