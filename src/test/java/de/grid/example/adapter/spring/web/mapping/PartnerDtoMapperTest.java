package de.grid.example.adapter.spring.web.mapping;

import de.grid.example.adapter.persistence.jpa.model.PartnerDao;
import de.grid.example.adapter.spring.web.model.PartnerDto;
import org.junit.Assert;
import org.junit.Test;

import static org.mapstruct.factory.Mappers.getMapper;

public class PartnerDtoMapperTest
{
    @Test
    public void map()
    {
        PartnerDao partnerDao = new PartnerDao();
        partnerDao.setId("42");
        partnerDao.setFirstname("Zoe");
        partnerDao.setLastname("Zieht");

        PartnerDtoMapper mapper = getMapper(PartnerDtoMapper.class);

        PartnerDto partnerDto = mapper.map(partnerDao);

        Assert.assertEquals("42", partnerDto.getId());
        Assert.assertEquals("Zoe", partnerDto.getFirstname());
        Assert.assertEquals("Zieht", partnerDto.getLastname());
    }
}