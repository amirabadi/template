package com.company.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_role")
@Getter
@Setter
public class Role extends ManipulateInformation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole;

    private String roleName;

    private String roleNamePersian;

   /* @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private Set<Permission> permissionSet;*/
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)

    private Set<User> user = new HashSet<>();
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)

    private Set<Permission> permissions = new HashSet<>();
}
