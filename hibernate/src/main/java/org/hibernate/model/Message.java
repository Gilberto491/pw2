package org.hibernate.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Message extends PanacheEntity {

    private String text;
}
