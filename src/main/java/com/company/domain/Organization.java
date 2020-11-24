package com.company.domain;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "tb_organization")
@Getter
@Setter
public class Organization extends ManipulateInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "type")
    private String type;
    @Column(name = "name")
    private String name;
    @Column(name = "uniquePath")
    private String uniquePath;
    @Column(name = "uniqueName")
    private String uniqueName;
    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "parent",
            referencedColumnName = "id",
            nullable = true
    )
    private Organization parent;
}
