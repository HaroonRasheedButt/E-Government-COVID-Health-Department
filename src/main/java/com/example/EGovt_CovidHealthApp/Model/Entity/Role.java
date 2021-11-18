package com.example.EGovt_CovidHealthApp.Model.Entity;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(indexes = {
        @Index(name = "createdDate_index", columnList = "createdDate")
})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    private String description;
    @Column
    private Date createdDate;
    @Column(nullable = true)
    private Date updatedDate;
    @Column(nullable = false)
    private boolean status;

    @ManyToMany(cascade = CascadeType.MERGE, targetEntity = Permission.class)
    private List<Permission> permissions = new ArrayList<>();

}
