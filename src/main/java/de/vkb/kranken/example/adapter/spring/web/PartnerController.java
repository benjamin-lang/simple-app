package de.vkb.kranken.example.adapter.spring.web;


import de.vkb.kranken.example.adapter.persistence.jpa.model.PartnerDao;
import de.vkb.kranken.example.adapter.spring.web.mapping.PartnerDtoMapper;
import de.vkb.kranken.example.adapter.spring.web.model.PartnerDto;
import de.vkb.kranken.example.application.PartnerService;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/partners")
@SuppressWarnings("unused")
public class PartnerController
{
    private final PartnerService service;
    private final PartnerDtoMapper mapper;

    public PartnerController(PartnerService service, PartnerDtoMapper mapper)
    {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<PartnerDto> getPartners(@RequestParam(required = false) String lastname)
    {
        return service.queryAllPartners(lastname).stream().map(mapper::map).collect(Collectors.toList());
    }

    @GetMapping("/{partnerId}")
    public PartnerDto getPartner(@PathVariable String partnerId)
    {
        Optional<PartnerDao> partnerDaoOpt = service.queryPartnerById(partnerId);

        return partnerDaoOpt.map(mapper::map).orElse(null);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String postPartner(@RequestBody PartnerDto partner)
    {
        Assert.hasLength(partner.getFirstname(), "Property 'firstname' must not be null or empty!");
        Assert.hasLength(partner.getLastname(), "Property 'lastname' must not be null or empty!");

        return service.registerNewPartner(partner.getFirstname(), partner.getLastname());
    }
}
