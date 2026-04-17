package com.practice.SpringBoot.entity;

import jakarta.persistence.*;
import lombok.*;
//import org.hibernate.envers.Audited;
//import org.hibernate.envers.NotAudited;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "post")
@Entity
@Setter
//@Audited
public class PostEntity extends AuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

//    @NotAudited
    private String description;

    @PrePersist
    void beforSave(){

    }

    @PreUpdate
    void beforUpdate(){

    }

    @PreRemove
    void beforDelete(){
    }
}
