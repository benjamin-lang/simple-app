package de.grid.example.adapter.persistence.jpa.mapping;

import de.grid.example.adapter.persistence.jpa.mapping.PartnerDaoMapper;
import de.grid.example.adapter.persistence.jpa.model.PartnerDao;
import org.junit.Assert;
import org.junit.Test;

import static org.mapstruct.factory.Mappers.getMapper;

public class PartnerDaoMapperTest
{
    @Test
    public void create()
    {
        PartnerDaoMapper mapper = getMapper(PartnerDaoMapper.class);

        PartnerDao partnerDao = mapper.create("42", "Zufall", "Rainer");

        Assert.assertEquals("42", partnerDao.getPartnerId());
        Assert.assertEquals("Zufall", partnerDao.getLastname());
        Assert.assertEquals("Rainer", partnerDao.getFirstname());
    }
}