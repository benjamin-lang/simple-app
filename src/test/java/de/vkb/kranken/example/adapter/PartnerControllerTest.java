package de.vkb.kranken.example.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.vkb.kranken.example.adapter.persistence.jpa.PartnerRepositoryJpa;
import de.vkb.kranken.example.adapter.persistence.jpa.model.PartnerDao;
import de.vkb.kranken.example.adapter.spring.web.PartnerController;
import de.vkb.kranken.example.adapter.spring.web.model.PartnerDto;
import de.vkb.kranken.example.application.common.IdGenerator;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PartnerControllerTest
{
    private static final String BASE_URL = "http://localhost";
    private static final String PARTNER_RESOURCE = "/partners";

    private static final String PARTNER_ID = "partner-id-42";
    private static final String PARTNER_FIRSTNAME = "Justin";
    private static final String PARTNER_LASTNAME = "Time";

    @Autowired
    @SuppressWarnings("unused")
    private PartnerController controller;

    @Autowired
    @SuppressWarnings("unused")
    private ObjectMapper objectMapper;

    @MockBean
    @SuppressWarnings("unused")
    private IdGenerator idGenerator;

    @MockBean
    @SuppressWarnings("unused")
    private PartnerRepositoryJpa mockRepository;

    private MockMvc mockMvc;

    @Before
    public void setUp()
    {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void postPartner() throws Exception
    {
        String requestUrl = BASE_URL + PARTNER_RESOURCE;

        Mockito.when(idGenerator.generateId()).thenReturn(PARTNER_ID);

        PartnerDto partnerDto = new PartnerDto(null, PARTNER_FIRSTNAME, PARTNER_LASTNAME);

        mockMvc.perform(post(requestUrl)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(partnerDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string(CoreMatchers.equalTo(PARTNER_ID)))
                .andReturn();
    }

    @Test
    public void getAllPartners() throws Exception
    {
        String requestUrl = BASE_URL + PARTNER_RESOURCE;

        PartnerDto expectedPartnerDto = new PartnerDto(PARTNER_ID, PARTNER_FIRSTNAME, PARTNER_LASTNAME);

        PartnerDao partnerDao = new PartnerDao();
        partnerDao.setId(PARTNER_ID);
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

        PartnerDto expectedPartnerDto = new PartnerDto("42", "John", "Doe");

        PartnerDao partnerDao = new PartnerDao();
        partnerDao.setId("42");
        partnerDao.setFirstname("John");
        partnerDao.setLastname("Doe");

        Mockito.when(mockRepository.findAllByLastname("Doe")).thenReturn(Collections.singletonList(partnerDao));

        mockMvc.perform(get(requestUrl)
                .param("lastname", "Doe"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(CoreMatchers.containsString(objectMapper.writeValueAsString(expectedPartnerDto))))
                .andReturn();
    }

    @Test
    public void getPartner() throws Exception
    {
        String requestUrl = BASE_URL + PARTNER_RESOURCE + "/" + "partner-id-42";

        PartnerDto expectedPartnerDto = new PartnerDto("partner-id-42", "John", "Doe");

        PartnerDao partnerDao = new PartnerDao();
        partnerDao.setId("partner-id-42");
        partnerDao.setFirstname("John");
        partnerDao.setLastname("Doe");

        Mockito.when(mockRepository.findById("partner-id-42")).thenReturn(Optional.of(partnerDao));

        mockMvc.perform(get(requestUrl).param("partnerId", "partner-id-42"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(CoreMatchers.containsString(objectMapper.writeValueAsString(expectedPartnerDto))))
                .andReturn();
    }

}