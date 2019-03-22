package de.grid.example.application;

import de.grid.example.adapter.persistence.jpa.PartnerRepositoryJpa;
import de.grid.example.adapter.persistence.jpa.model.PartnerDao;
import de.grid.example.application.common.IdGenerator;
import de.grid.example.application.mapping.PartnerMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartnerService
{
    private final PartnerMapper mapper;
    private final IdGenerator idGenerator;
    private final PartnerRepositoryJpa reposiory;

    public PartnerService(PartnerMapper mapper, IdGenerator idGenerator, PartnerRepositoryJpa reposiory)
    {
        this.mapper = mapper;
        this.idGenerator = idGenerator;
        this.reposiory = reposiory;
    }

    public String registerNewPartner(String fistname, String lastname)
    {
        PartnerDao partner = mapper.create(idGenerator.generateId(), fistname, lastname);
        reposiory.save(partner);

        return partner.getId();
    }


    public Optional<PartnerDao> queryPartnerById(String partnerId)
    {
        return reposiory.findById(partnerId);
    }

    public List<PartnerDao> queryAllPartners(String lastnameFilter)
    {
        if (StringUtils.isNotEmpty(lastnameFilter))
            return reposiory.findAllByLastname(lastnameFilter);

        return reposiory.findAll();
    }
}
