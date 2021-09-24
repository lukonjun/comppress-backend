package org.comppress.comppressbackend.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
public class Preference extends AbstractEntity{

    @Column(unique = true)
    private String name;
    private String type;

}
