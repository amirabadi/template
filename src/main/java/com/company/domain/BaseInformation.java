package com.company.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_baseInformation")
@Getter
@Setter
public class BaseInformation extends ManipulateInformation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    @Type(type="org.hibernate.type.StringNVarcharType")
    private String title;
    @Column(name = "fullName")
    @Type(type="org.hibernate.type.StringNVarcharType")
    private String fullName;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent",referencedColumnName = "id", nullable = true)
    private BaseInformation parent;
}