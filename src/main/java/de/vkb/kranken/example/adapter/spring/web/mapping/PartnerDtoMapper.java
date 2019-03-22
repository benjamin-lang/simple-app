package de.vkb.kranken.example.adapter.spring.web.mapping;

import de.vkb.kranken.example.adapter.persistence.jpa.model.PartnerDao;
import de.vkb.kranken.example.adapter.spring.web.model.PartnerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartnerDtoMapper
{
    PartnerDto map(PartnerDao partnerDao);
}
