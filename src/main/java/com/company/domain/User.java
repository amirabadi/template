package com.company.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
public class User  extends ManipulateInformation implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type = "org.hibernate.type.StringNVarcharType")
    private String name;

    @Type(type = "org.hibernate.type.StringNVarcharType")
    private String lName;

    @Type(type = "org.hibernate.type.StringNVarcharType")
    private String nationalCode;

    @Type(type = "org.hibernate.type.StringNVarcharType")
    private String tel;

    private String username;

    private String password;

    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_User_Role",
            joinColumns = {@JoinColumn(name = "idUser", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "idRole", referencedColumnName = "idRole")})
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        HashSet<GrantedAuthority> authorities = new HashSet<GrantedAuthority>(roles.size());
        if (roles.size() > 1){authorities.add(new SimpleGrantedAuthority("view"));return authorities; }
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
           /* for(Permission per:role.getPermissions()){
                authorities.add(new SimpleGrantedAuthority(per.getNamePermission()));
            }*/
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
