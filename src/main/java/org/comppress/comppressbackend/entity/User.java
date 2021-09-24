package org.comppress.comppressbackend.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Collection;

@Entity
@Data
public class User extends AbstractEntity{

    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String username;
    private String password;
    @OneToOne
    private Role role;
    @OneToMany
    private Collection<Preference> preferences;

}
