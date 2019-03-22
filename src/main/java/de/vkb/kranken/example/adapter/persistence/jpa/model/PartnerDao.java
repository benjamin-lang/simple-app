package de.vkb.kranken.example.adapter.persistence.jpa.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PartnerDao
{
    @Id
    private String id;

    private String firstname;
    private String lastname;

    public String getFirstname()
    {
        return firstname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public String getLastname()
    {
        return lastname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
}
