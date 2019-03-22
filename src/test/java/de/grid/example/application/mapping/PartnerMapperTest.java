package de.grid.example.application.mapping;

import de.grid.example.adapter.persistence.jpa.model.PartnerDao;
import org.junit.Assert;
import org.junit.Test;

import static org.mapstruct.factory.Mappers.getMapper;

public class PartnerMapperTest
{
    @Test
    public void create()
    {
        PartnerMapper mapper = getMapper(PartnerMapper.class);

        PartnerDao partnerDao = mapper.create("42", "Rainer", "Zufall");

        Assert.assertEquals("42", partnerDao.getId());
        Assert.assertEquals("Rainer", partnerDao.getFirstname());
        Assert.assertEquals("Zufall", partnerDao.getLastname());
    }
}