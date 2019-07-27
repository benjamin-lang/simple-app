package de.grid.example.adapter.persistence.jpa.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PartnerDao
{
    @Id
    private String partnerId;
    private String firstname;
    private String lastname;

    public String getPartnerId()
    {
        return partnerId;
    }

    public void setPartnerId(String partnerId)
    {
        this.partnerId = partnerId;
    }

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

}
