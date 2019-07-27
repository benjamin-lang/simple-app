package de.grid.example.adapter.spring.web.mapping;

import de.grid.example.adapter.persistence.jpa.model.PartnerDao;
import de.grid.example.adapter.spring.web.model.GetPartnerDto;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PartnerDtoMapper
{
    GetPartnerDto mapFromDao(PartnerDao partnerDao);

    List<GetPartnerDto> mapFromDaoList(List<PartnerDao> partners);

    default UUID mapToUuid(String id)
    {
        if (Objects.isNull(id))
            return null;

        return UUID.fromString(id);
    }
}
