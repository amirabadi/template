package com.company.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
public class ManipulateInformation implements Serializable {
    @Column(name = "registerUserId")
    private Long registerUserId;
    @Column(name = "registerDate")
    @Type(type="org.hibernate.type.StringNVarcharType")
    private String registerDate;
    @Column(name = "updateUserId")
    private Long updateUserId;
    @Column(name = "updateDate")
    @Type(type="org.hibernate.type.StringNVarcharType")
    private String updateDate;
    @Column(name = "event")
    private String event;
}

