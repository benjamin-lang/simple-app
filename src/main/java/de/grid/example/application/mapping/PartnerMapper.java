package de.grid.example.application.mapping;


import de.grid.example.adapter.persistence.jpa.model.PartnerDao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartnerMapper
{
    PartnerDao create(String id, String firstname, String lastname);
}
