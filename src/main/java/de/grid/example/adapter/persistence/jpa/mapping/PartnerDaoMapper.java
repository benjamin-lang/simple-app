package de.grid.example.adapter.persistence.jpa.mapping;


import de.grid.example.adapter.persistence.jpa.model.PartnerDao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartnerDaoMapper
{
    PartnerDao create(String partnerId, String lastname, String firstname);
}
