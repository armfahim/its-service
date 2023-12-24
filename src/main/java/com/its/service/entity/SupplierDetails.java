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
@Table(name = "SUPPLIER_DETAILS")
public class SupplierDetails extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "SUPPLIER_NAME", nullable = false)
    private String supplierName;

    @Column(name = "CONTACT_PERSON", nullable = false)
    private String contactPerson;

    @Column(name = "PHONE", nullable = false, unique = true)
    @Pattern(
            regexp = "^\\+?[0-9\\-\\s]+$",
            message = "Please provide a valid phone number"
    )
    private String phone;

    @Column(name = "EMAIL", nullable = true, unique = true)
    @Email(message = "Please provide a valid email address")
    private String email;

    @Column(name = "ADDRESS", nullable = false)
    private String address;

}
