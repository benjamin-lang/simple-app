package de.grid.example.adapter.spring.web.mapping;

import de.grid.example.adapter.persistence.jpa.model.PartnerDao;
import de.grid.example.adapter.spring.web.model.PartnerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartnerDtoMapper
{
    PartnerDto map(PartnerDao partnerDao);
}
