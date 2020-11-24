package com.company.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_permission")
@Getter@Setter
public class Permission extends ManipulateInformation  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPermission;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_Permission_Role",
            joinColumns = {@JoinColumn(name = "idPermission", referencedColumnName = "idPermission")},
            inverseJoinColumns = {@JoinColumn(name = "idRole", referencedColumnName = "idRole")})
    private Set<Role> roles = new HashSet<>();

    private String namePermission;

    private String objectPermission;


}