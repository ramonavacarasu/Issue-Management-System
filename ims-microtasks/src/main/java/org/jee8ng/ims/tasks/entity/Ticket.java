package org.jee8ng.ims.tasks.entity;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Ticket {
    private int id;
    private String name;

    public Ticket(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
