package de.grid.example.application;

import de.grid.example.adapter.persistence.jpa.PartnerRepositoryJpa;
import de.grid.example.adapter.persistence.jpa.mapping.PartnerDaoMapper;
import de.grid.example.adapter.persistence.jpa.model.PartnerDao;
import de.grid.example.application.common.IdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PartnerService
{
    private final PartnerDaoMapper mapper;
    private final IdGenerator idGenerator;
    private final PartnerRepositoryJpa repository;

    public PartnerService(PartnerDaoMapper mapper, IdGenerator idGenerator, PartnerRepositoryJpa repository)
    {
        this.mapper = mapper;
        this.idGenerator = idGenerator;
        this.repository = repository;
    }

    public boolean exists(String partnerId)
    {
        return repository.existsById(partnerId);
    }

    public String registerNewPartner(String lastname, String fistname)
    {
        PartnerDao partner = mapper.create(idGenerator.generateId(), lastname, fistname);
        repository.save(partner);

        return partner.getPartnerId();
    }

    public Optional<PartnerDao> queryPartnerById(String partnerId)
    {
        return repository.findById(partnerId);
    }

    public List<PartnerDao> queryAllPartners(String lastnameFilter)
    {
        if (StringUtils.isNotEmpty(lastnameFilter))
            return repository.findAllByLastname(lastnameFilter);

        return repository.findAll();
    }

    public Optional<String> updatePartner(UUID partnerId, String lastname, String firstname)
    {
        boolean created = !repository.existsById(partnerId.toString());

        PartnerDao partner = mapper.create(partnerId.toString(), lastname, firstname);
        repository.save(partner);

        if (created)
            return Optional.of(partnerId.toString());

        return Optional.empty();
    }
}
