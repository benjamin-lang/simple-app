package de.grid.example.adapter.spring.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PartnerDto
{
    private String id;
    private String firstname;
    private String lastname;

    @JsonCreator
    public PartnerDto(@JsonProperty("id") String id, @JsonProperty("firstname") String firstname, @JsonProperty("lastname") String lastname)
    {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public PartnerDto()
    {
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

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
}
