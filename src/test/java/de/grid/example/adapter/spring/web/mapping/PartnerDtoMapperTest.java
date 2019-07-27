package de.grid.example.adapter.spring.web.mapping;

import de.grid.example.adapter.persistence.jpa.model.PartnerDao;
import de.grid.example.adapter.spring.web.model.GetPartnerDto;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mapstruct.factory.Mappers.getMapper;

public class PartnerDtoMapperTest
{
    @Test
    public void mapFromDao()
    {
        PartnerDao partnerDao = new PartnerDao();
        partnerDao.setPartnerId("99f2f493-0665-43dd-b0b8-03ea1e7a7810");
        partnerDao.setFirstname("Zoe");
        partnerDao.setLastname("Zieht");

        PartnerDtoMapper mapper = getMapper(PartnerDtoMapper.class);
        GetPartnerDto partnerDto = mapper.mapFromDao(partnerDao);

        Assert.assertEquals(UUID.fromString("99f2f493-0665-43dd-b0b8-03ea1e7a7810"), partnerDto.getPartnerId());
        Assert.assertEquals("Zoe", partnerDto.getFirstname());
        Assert.assertEquals("Zieht", partnerDto.getLastname());
    }

    @Test
    public void mapFromDaos()
    {
        PartnerDao partnerDao1 = new PartnerDao();
        partnerDao1.setPartnerId("99f2f493-0665-43dd-b0b8-03ea1e7a7810");
        partnerDao1.setFirstname("Zoe");
        partnerDao1.setLastname("Zieht");

        PartnerDao partnerDao2 = new PartnerDao();
        partnerDao2.setPartnerId("2150c0e9-2360-4027-b8ae-fd3d38d7c1de");
        partnerDao2.setFirstname("Rainer");
        partnerDao2.setLastname("Zufall");

        PartnerDao partnerDao3 = new PartnerDao();
        partnerDao3.setPartnerId("18969041-19f0-492e-8d00-79fb5276602c");
        partnerDao3.setFirstname("Lasmiranda");
        partnerDao3.setLastname("Densivilia");

        PartnerDtoMapper mapper = getMapper(PartnerDtoMapper.class);
        List<GetPartnerDto> partnerDtos = mapper.mapFromDaoList(Arrays.asList(partnerDao1, partnerDao2, partnerDao3));

        Assert.assertEquals(3, partnerDtos.size());
        Assert.assertEquals(UUID.fromString("99f2f493-0665-43dd-b0b8-03ea1e7a7810"), partnerDtos.get(0).getPartnerId());
        Assert.assertEquals("Zoe", partnerDtos.get(0).getFirstname());
        Assert.assertEquals("Zieht", partnerDtos.get(0).getLastname());

        Assert.assertEquals(UUID.fromString("2150c0e9-2360-4027-b8ae-fd3d38d7c1de"), partnerDtos.get(1).getPartnerId());
        Assert.assertEquals("Rainer", partnerDtos.get(1).getFirstname());
        Assert.assertEquals("Zufall", partnerDtos.get(1).getLastname());

        Assert.assertEquals(UUID.fromString("18969041-19f0-492e-8d00-79fb5276602c"), partnerDtos.get(2).getPartnerId());
        Assert.assertEquals("Lasmiranda", partnerDtos.get(2).getFirstname());
        Assert.assertEquals("Densivilia", partnerDtos.get(2).getLastname());
    }
}