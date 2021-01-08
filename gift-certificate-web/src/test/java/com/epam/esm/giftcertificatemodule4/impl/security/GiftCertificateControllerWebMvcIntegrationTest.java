package com.epam.esm.giftcertificatemodule4.impl.security;

import com.epam.esm.giftcertificatemodule4.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule4.services.GiftCertificateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GiftCertificateControllerWebMvcIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GiftCertificateService giftCertificateService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper mapper;

    private static final int STATUS_OK = 200;
    private static final int STATUS_FORBIDDEN = 403;
    private static final Long ID = 1L;
    private String jsonRequest;

    private GiftCertificate giftCertificate = new GiftCertificate();

    @BeforeEach
    public void init() throws JsonProcessingException {
        giftCertificate.setId(1L);
        giftCertificate.setName("Test");
        giftCertificate.setDescription("This is a test cert");
        giftCertificate.setPrice(new BigDecimal(9.99));
        giftCertificate.setCreateDate(ZonedDateTime.now().toOffsetDateTime());
        giftCertificate.setLastUpdateDate(ZonedDateTime.now().toOffsetDateTime());
        giftCertificate.setDuration(31);

        jsonRequest = mapper.writeValueAsString(giftCertificate);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

//    @Test
//    @WithMockUser(roles = {"ADMIN"})
//    public void findAllAdminAccessAllowedTest() throws Exception {
//        MvcResult result = mockMvc.perform(get("/api/certificates"))
//                .andExpect(status().isOk()).andReturn();
//        int actualStatus = result.getResponse().getStatus();
//        assertEquals(STATUS_OK, actualStatus);
//    }
//
//    @Test
//    @WithMockUser(roles = {"USER"})
//    public void findAllUserAccessAllowedTest() throws Exception {
//        MvcResult result = mockMvc.perform(get("/api/certificates"))
//                .andExpect(status().isOk()).andReturn();
//        int actualStatus = result.getResponse().getStatus();
//        assertEquals(STATUS_OK, actualStatus);
//    }
//
//    @Test
//    @WithMockUser(roles = {"GUEST"})
//    public void findAllGuestAccessAllowedTest() throws Exception {
//        MvcResult result = mockMvc.perform(get("/api/certificates"))
//                .andExpect(status().isOk()).andReturn();
//        int actualStatus = result.getResponse().getStatus();
//        assertEquals(STATUS_OK, actualStatus);
//    }
//
//    @Test
//    @WithMockUser(authorities = {"SCOPE_administrate"})
//    public void findAllScopeAdministrateAccessAllowedTest() throws Exception {
//        MvcResult result = mockMvc.perform(get("/api/certificates"))
//                .andExpect(status().isOk()).andReturn();
//        int actualStatus = result.getResponse().getStatus();
//        assertEquals(STATUS_OK, actualStatus);
//    }
//
//    @Test
//    @WithMockUser(authorities = {"SCOPE_read"})
//    public void findAllScopeReadAccessAllowedTest() throws Exception {
//        MvcResult result = mockMvc.perform(get("/api/certificates"))
//                .andExpect(status().isOk()).andReturn();
//        int actualStatus = result.getResponse().getStatus();
//        assertEquals(STATUS_OK, actualStatus);
//    }
//
//    @Test
//    @WithMockUser(authorities = {"SCOPE_guesting"})
//    public void findAllScopeAccessAllowedTest() throws Exception {
//        MvcResult result = mockMvc.perform(get("/api/certificates"))
//                .andExpect(status().isOk()).andReturn();
//        int actualStatus = result.getResponse().getStatus();
//        assertEquals(STATUS_OK, actualStatus);
//    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void findByIdAdminAccessAllowedTest() throws Exception {
        when(giftCertificateService.findById(ID)).thenReturn(giftCertificate);
        mockMvc.perform(get("/api/certificates/{id}", ID))
                .andExpect(status().isOk());
    }

//    @Test
//    @WithMockUser(roles = {"USER"})
//    public void findByIdUserAccessAllowedTest() throws Exception {
//        MvcResult result = mockMvc.perform(get("/api/certificates/{id}", ID))
//                .andExpect(status().isOk()).andReturn();
//        int actualStatus = result.getResponse().getStatus();
//        assertEquals(STATUS_OK, actualStatus);
//    }
//
//    @Test
//    @WithMockUser(roles = {"GUEST"})
//    public void findByIdGuestAccessAllowedTest() throws Exception {
//        MvcResult result = mockMvc.perform(get("/api/certificates/{id}", ID))
//                .andExpect(status().isOk()).andReturn();
////        int actualStatus = result.getResponse().getStatus();
////        assertEquals(STATUS_OK, actualStatus);
//    }
//
//    @Test
//    @WithMockUser(authorities = {"SCOPE_administrate"})
//    public void findByIdScopeAdministrateAccessAllowedTest() throws Exception {
//        MvcResult result = mockMvc.perform(get("/api/certificates/{id}", ID))
//                .andExpect(status().isOk()).andReturn();
//        int actualStatus = result.getResponse().getStatus();
//        assertEquals(STATUS_OK, actualStatus);
//    }
//
//    @Test
//    @WithMockUser(authorities = {"SCOPE_read"})
//    public void findByIdScopeReadAccessAllowedTest() throws Exception {
//        MvcResult result = mockMvc.perform(get("/api/certificates/{id}", ID))
//                .andExpect(status().isOk()).andReturn();
//        int actualStatus = result.getResponse().getStatus();
//        assertEquals(STATUS_OK, actualStatus);
//    }
//
//    @Test
//    @WithMockUser(authorities = {"SCOPE_guesting"})
//    public void findByIdScopeAccessAllowedTest() throws Exception {
//        MvcResult result = mockMvc.perform(get("/api/certificates/{id}", ID))
//                .andExpect(status().isOk()).andReturn();
//        int actualStatus = result.getResponse().getStatus();
//        assertEquals(STATUS_OK, actualStatus);
//    }
//
//    @Test
//    @WithMockUser(roles = {"ADMIN"})
//    public void findByAdminAccessAllowedTest() throws Exception {
//        MvcResult result = mockMvc.perform(get("/api/certificates/findBy"))
//                .andExpect(status().isOk()).andReturn();
//        int actualStatus = result.getResponse().getStatus();
//        assertEquals(STATUS_OK, actualStatus);
//    }
//
//    @Test
//    @WithMockUser(roles = {"USER"})
//    public void findByUserAccessAllowedTest() throws Exception {
//        MvcResult result = mockMvc.perform(get("/api/certificates/findBy"))
//                .andExpect(status().isOk()).andReturn();
//        int actualStatus = result.getResponse().getStatus();
//        assertEquals(STATUS_OK, actualStatus);
//    }
//
//    @Test
//    @WithMockUser(roles = {"GUEST"})
//    public void findByGuestAccessAllowedTest() throws Exception {
//        MvcResult result = mockMvc.perform(get("/api/certificates/findBy"))
//                .andExpect(status().isOk()).andReturn();
//        int actualStatus = result.getResponse().getStatus();
//        assertEquals(STATUS_OK, actualStatus);
//    }
//
//    @Test
//    @WithMockUser(authorities = {"SCOPE_administrate"})
//    public void findByScopeAdministrateAccessAllowedTest() throws Exception {
//        MvcResult result = mockMvc.perform(get("/api/certificates/findBy"))
//                .andExpect(status().isOk()).andReturn();
//        int actualStatus = result.getResponse().getStatus();
//        assertEquals(STATUS_OK, actualStatus);
//    }
//
//    @Test
//    @WithMockUser(authorities = {"SCOPE_read"})
//    public void findByScopeReadAccessAllowedTest() throws Exception {
//        MvcResult result = mockMvc.perform(get("/api/certificates/findBy"))
//                .andExpect(status().isOk()).andReturn();
//        int actualStatus = result.getResponse().getStatus();
//        assertEquals(STATUS_OK, actualStatus);
//    }
//
//    @Test
//    @WithMockUser(authorities = {"SCOPE_guesting"})
//    public void findByScopeAccessAllowedTest() throws Exception {
//        MvcResult result = mockMvc.perform(get("/api/certificates/findBy"))
//                .andExpect(status().isOk()).andReturn();
//        int actualStatus = result.getResponse().getStatus();
//        assertEquals(STATUS_OK, actualStatus);
//    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void updateAdminAccessAllowedTest() throws Exception {
        MvcResult result = mockMvc.perform(put("/api/certificates").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        int actualStatus = result.getResponse().getStatus();
        assertEquals(STATUS_OK, actualStatus);
    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void updateUserAccessDeniedTest() throws Exception {
        MvcResult result = mockMvc.perform(put("/api/certificates").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()).andReturn();
        int actualStatus = result.getResponse().getStatus();
        assertEquals(STATUS_FORBIDDEN, actualStatus);
    }

    @Test
    @WithMockUser(roles = {"GUEST"})
    public void updateGuestAccessDeniedTest() throws Exception {
        MvcResult result = mockMvc.perform(put("/api/certificates").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()).andReturn();
        int actualStatus = result.getResponse().getStatus();
        assertEquals(STATUS_FORBIDDEN, actualStatus);
    }

//    @Test
//    @WithMockUser(authorities = {"SCOPE_administrate"})
//    public void updateScopeAdministrateAccessAllowedTest() throws Exception {
//        MvcResult result = mockMvc.perform(put("/api/certificates").content(jsonRequest)
//                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
//        int actualStatus = result.getResponse().getStatus();
//        assertEquals(STATUS_OK, actualStatus);
//    }

    @Test
    @WithMockUser(authorities = {"SCOPE_read"})
    public void updateScopeReadAccessDeniedTest() throws Exception {
        MvcResult result = mockMvc.perform(put("/api/certificates").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()).andReturn();
        int actualStatus = result.getResponse().getStatus();
        assertEquals(STATUS_FORBIDDEN, actualStatus);
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_guesting"})
    public void updateScopeAccessDeniedTest() throws Exception {
        MvcResult result = mockMvc.perform(put("/api/certificates").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()).andReturn();
        int actualStatus = result.getResponse().getStatus();
        assertEquals(STATUS_FORBIDDEN, actualStatus);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void deleteAdminAccessAllowedTest() throws Exception {
        MvcResult result = mockMvc.perform(delete("/api/certificates").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        int actualStatus = result.getResponse().getStatus();
        assertEquals(STATUS_OK, actualStatus);
    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void deleteUserAccessDeniedTest() throws Exception {
        MvcResult result = mockMvc.perform(delete("/api/certificates").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()).andReturn();
        int actualStatus = result.getResponse().getStatus();
        assertEquals(STATUS_FORBIDDEN, actualStatus);
    }

    @Test
    @WithMockUser(roles = {"GUEST"})
    public void deleteGuestAccessDeniedTest() throws Exception {
        MvcResult result = mockMvc.perform(delete("/api/certificates").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()).andReturn();
        int actualStatus = result.getResponse().getStatus();
        assertEquals(STATUS_FORBIDDEN, actualStatus);
    }

//    @Test
//    @WithMockUser(authorities = {"SCOPE_administrate"})
//    public void deleteScopeAdministrateAccessAllowedTest() throws Exception {
//        MvcResult result = mockMvc.perform(delete("/api/certificates").content(jsonRequest)
//                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
//        int actualStatus = result.getResponse().getStatus();
//        assertEquals(STATUS_OK, actualStatus);
//    }

    @Test
    @WithMockUser(authorities = {"SCOPE_read"})
    public void deleteScopeReadAccessDeniedTest() throws Exception {
        MvcResult result = mockMvc.perform(delete("/api/certificates").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()).andReturn();
        int actualStatus = result.getResponse().getStatus();
        assertEquals(STATUS_FORBIDDEN, actualStatus);
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_guesting"})
    public void deleteScopeAccessDeniedTest() throws Exception {
        MvcResult result = mockMvc.perform(delete("/api/certificates").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()).andReturn();
        int actualStatus = result.getResponse().getStatus();
        assertEquals(STATUS_FORBIDDEN, actualStatus);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void deleteByIdAdminAccessAllowedTest() throws Exception {
        MvcResult result = mockMvc.perform(delete("/api/certificates/{id}", ID))
                .andExpect(status().isOk()).andReturn();
        int actualStatus = result.getResponse().getStatus();
        assertEquals(STATUS_OK, actualStatus);
    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void deleteByIdUserAccessDeniedTest() throws Exception {
        MvcResult result = mockMvc.perform(delete("/api/certificates/{id}", ID))
                .andExpect(status().isForbidden()).andReturn();
        int actualStatus = result.getResponse().getStatus();
        assertEquals(STATUS_FORBIDDEN, actualStatus);
    }

    @Test
    @WithMockUser(roles = {"GUEST"})
    public void deleteByIdGuestAccessDeniedTest() throws Exception {
        mockMvc.perform(delete("/api/certificates/{id}", ID))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_administrate"})
    public void deleteByIdScopeAdministrateAccessAllowedTest() throws Exception {
//        mockMvc.perform(delete("/api/certificates/{id}", ID))
//                .andExpect(handler().methodName("giftCertificateService").)
//                .andExpect(status().isOk());
//        Mockito.verify(giftCertificateService).deleteById(ID);
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_read"})
    public void deleteByIdScopeReadAccessDeniedTest() throws Exception {
        MvcResult result = mockMvc.perform(delete("/api/certificates/{id}", ID))
                .andExpect(status().isForbidden()).andReturn();
        int actualStatus = result.getResponse().getStatus();
        assertEquals(STATUS_FORBIDDEN, actualStatus);
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_guesting"})
    public void deleteByIdScopeAccessDeniedTest() throws Exception {
        MvcResult result = mockMvc.perform(delete("/api/certificates/{id}", ID))
                .andExpect(status().isForbidden()).andReturn();
        int actualStatus = result.getResponse().getStatus();
        assertEquals(STATUS_FORBIDDEN, actualStatus);
    }
}