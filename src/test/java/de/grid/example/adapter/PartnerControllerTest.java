package de.grid.example.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.grid.example.adapter.persistence.jpa.PartnerRepositoryJpa;
import de.grid.example.adapter.persistence.jpa.model.PartnerDao;
import de.grid.example.adapter.spring.web.controller.PartnerController;
import de.grid.example.adapter.spring.web.model.GetPartnerDto;
import de.grid.example.adapter.spring.web.model.PostPartnerDto;
import de.grid.example.application.common.IdGenerator;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PartnerControllerTest
{
    private static final String BASE_URL = "http://localhost";
    private static final String PARTNER_RESOURCE = "/partners";

    private static final String PARTNER_ID = "bf437ac0-24c6-435f-a63d-3b000e7bb9db";
    private static final String PARTNER_FIRSTNAME = "Justin";
    private static final String PARTNER_LASTNAME = "Time";

    @Autowired
    private PartnerController controller;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IdGenerator idGenerator;

    @MockBean
    private PartnerRepositoryJpa mockRepository;

    private MockMvc mockMvc;

    @Before
    public void setUp()
    {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper)).build();
    }


    @Test
    public void postPartner() throws Exception
    {
        String requestUrl = BASE_URL + PARTNER_RESOURCE;

        Mockito.when(idGenerator.generateId()).thenReturn(PARTNER_ID);

        PostPartnerDto postPartnerDto = new PostPartnerDto().lastname(PARTNER_LASTNAME).firstname(PARTNER_FIRSTNAME);

        mockMvc.perform(post(requestUrl)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .content(objectMapper.writeValueAsString(postPartnerDto)))
            .andExpect(status().isCreated())
            .andExpect(header().string("location", CoreMatchers.containsString(PARTNER_ID)))
            .andReturn();
    }

    @Test
    public void getAllPartners() throws Exception
    {
        String requestUrl = BASE_URL + PARTNER_RESOURCE;

        GetPartnerDto expectedPartnerDto = new GetPartnerDto().partnerId(UUID.fromString(PARTNER_ID)).lastname(PARTNER_LASTNAME).firstname(PARTNER_FIRSTNAME);

        PartnerDao partnerDao = new PartnerDao();
        partnerDao.setPartnerId(PARTNER_ID);
        partnerDao.setFirstname(PARTNER_FIRSTNAME);
        partnerDao.setLastname(PARTNER_LASTNAME);

        Mockito.when(mockRepository.findAll()).thenReturn(Collections.singletonList(partnerDao));

        mockMvc.perform(get(requestUrl))
//                .andDo(print())
            .andExpect(status().isOk())
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string(CoreMatchers.containsString(objectMapper.writeValueAsString(expectedPartnerDto))))
            .andReturn();
    }

    @Test
    public void getPartnersFilteredByLastname() throws Exception
    {
        String requestUrl = BASE_URL + PARTNER_RESOURCE;

        GetPartnerDto expectedPartnerDto = new GetPartnerDto().partnerId(UUID.fromString(PARTNER_ID)).lastname(PARTNER_LASTNAME).firstname(PARTNER_FIRSTNAME);

        PartnerDao partnerDao = new PartnerDao();
        partnerDao.setPartnerId(PARTNER_ID);
        partnerDao.setFirstname(PARTNER_FIRSTNAME);
        partnerDao.setLastname(PARTNER_LASTNAME);

        Mockito.when(mockRepository.findAllByLastname(PARTNER_LASTNAME)).thenReturn(Collections.singletonList(partnerDao));

        mockMvc.perform(get(requestUrl)
            .param("lastnameFilter", PARTNER_LASTNAME))
            .andExpect(status().isOk())
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string(CoreMatchers.containsString(objectMapper.writeValueAsString(expectedPartnerDto))))
            .andReturn();
    }

    @Test
    public void getPartner() throws Exception
    {
        String requestUrl = BASE_URL + PARTNER_RESOURCE + "/" + PARTNER_ID;

        GetPartnerDto expectedPartnerDto = new GetPartnerDto().partnerId(UUID.fromString(PARTNER_ID)).lastname(PARTNER_LASTNAME).firstname(PARTNER_FIRSTNAME);

        PartnerDao partnerDao = new PartnerDao();
        partnerDao.setPartnerId(PARTNER_ID);
        partnerDao.setFirstname(PARTNER_FIRSTNAME);
        partnerDao.setLastname(PARTNER_LASTNAME);

        Mockito.when(mockRepository.findById(PARTNER_ID)).thenReturn(Optional.of(partnerDao));

        mockMvc.perform(get(requestUrl).param("partnerId", PARTNER_ID))
            .andExpect(status().isOk())
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string(CoreMatchers.containsString(objectMapper.writeValueAsString(expectedPartnerDto))))
            .andReturn();
    }

}