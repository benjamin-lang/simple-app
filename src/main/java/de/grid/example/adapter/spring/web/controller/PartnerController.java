package de.grid.example.adapter.spring.web.controller;

import de.grid.example.adapter.persistence.jpa.model.PartnerDao;
import de.grid.example.adapter.spring.web.PartnersApi;
import de.grid.example.adapter.spring.web.mapping.PartnerDtoMapper;
import de.grid.example.adapter.spring.web.model.GetPartnerDto;
import de.grid.example.adapter.spring.web.model.PostPartnerDto;
import de.grid.example.application.PartnerService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Api(value = "partners", tags = "partners", description = "REST endpoint for partner management")
public class PartnerController implements PartnersApi
{
    private final PartnerService service;
    private final PartnerDtoMapper mapper;

    public PartnerController(PartnerService service, PartnerDtoMapper mapper)
    {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<GetPartnerDto> getPartner(UUID partnerId)
    {
        Optional<PartnerDao> partnerDao = service.queryPartnerById(partnerId.toString());

        return ResponseEntity.ok(partnerDao.map(mapper::mapFromDao).orElse(null));
    }

    @Override
    public ResponseEntity<List<GetPartnerDto>> getPartners(@Valid String lastnameFilter)
    {
        List<PartnerDao> partnerDaos = service.queryAllPartners(lastnameFilter);

        return ResponseEntity.ok(mapper.mapFromDaoList(partnerDaos));
    }

    @Override
    public ResponseEntity<Void> postPartner(@Valid PostPartnerDto dto)
    {
        String partnerId = service.registerNewPartner(dto.getLastname(), dto.getFirstname());

        return responseWithLocationHeader(partnerId);
    }

    private ResponseEntity<Void> responseWithLocationHeader(String createdId)
    {
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdId).toUri()).build();
    }

    @Override
    public ResponseEntity<Void> putPartner(UUID partnerId, @Valid PostPartnerDto dto)
    {
        Optional<String> partnerIdOpt = service.updatePartner(partnerId, dto.getLastname(), dto.getFirstname());

        return partnerIdOpt.map(this::responseWithLocationHeader).orElse(ResponseEntity.noContent().build());
    }
}
