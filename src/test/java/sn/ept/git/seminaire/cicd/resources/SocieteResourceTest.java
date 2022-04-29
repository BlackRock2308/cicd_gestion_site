package sn.ept.git.seminaire.cicd.resources;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sn.ept.git.seminaire.cicd.data.SocieteVMTestData;
import sn.ept.git.seminaire.cicd.data.TestData;
import sn.ept.git.seminaire.cicd.dto.SocieteDTO;
import sn.ept.git.seminaire.cicd.dto.vm.SocieteVM;
import sn.ept.git.seminaire.cicd.models.Societe;
import sn.ept.git.seminaire.cicd.services.ISocieteService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import sn.ept.git.seminaire.cicd.utils.SizeMapping;
import sn.ept.git.seminaire.cicd.utils.TestUtil;
import sn.ept.git.seminaire.cicd.utils.UrlMapping;

import javax.transaction.Transactional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class SocieteResourceTest extends BasicResourceTest {

    static private SocieteVM vm;
    @Autowired
    private ISocieteService service;
   static private SocieteDTO dto;



    @BeforeAll
    static void beforeAll() {
        log.info(" before all ");
    }

    @BeforeEach
    void beforeEach() {
        log.info(" before each ");
        service.deleteAll();
        vm = SocieteVMTestData.defaultVM();
    }

    @Test
    void findAll_shouldReturnSocietes() throws Exception {
        dto = service.save(vm);
        mockMvc.perform(get(UrlMapping.Societe.ALL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo(MockMvcResultHandlers.print()) //can print request details
                //.andExpect(jsonPath("$.Body", hasSize(1)))
                //.andExpect(jsonPath("$.[0].id").exists())
                .andExpect(jsonPath("$.[0].version").exists())
                .andExpect(jsonPath("$.[0].enabled").exists())
                .andExpect(jsonPath("$.[0].deleted").exists())
                .andExpect(jsonPath("$.[0].enabled", is(true)))
                .andExpect(jsonPath("$.[0].deleted").value(false))
                .andExpect(jsonPath("$.[0].name", is(dto.getName())))
                .andExpect(jsonPath("$.[0].phone").value(dto.getPhone()))
                .andExpect(jsonPath("$.[0].email").value(dto.getEmail()))
                .andExpect(jsonPath("$.[0].longitude").value(dto.getLongitude()))
                .andExpect(jsonPath("$.[0].latitude").value(dto.getLatitude()))
                .andExpect( jsonPath("$.[0].sites").value(dto.getSites()))
                .andExpect(jsonPath("$.[0].exercices").value(dto.getExercices()));
    }




    @Test
    void findById_shouldReturnSociete() throws Exception {
        dto = service.save(vm);
        mockMvc.perform(get(UrlMapping.Societe.FIND_BY_ID, dto.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.[0].id").exists())
                .andExpect(jsonPath("$.[0].version").exists())
                .andExpect(jsonPath("$.[0].enabled").exists())
                .andExpect(jsonPath("$.[0].deleted").exists())
                .andExpect(jsonPath("$.[0].name", is(dto.getName())))
                .andExpect(jsonPath("$.[0].phone").value(dto.getPhone()))
                .andExpect(jsonPath("$.[0].email").value(dto.getEmail()))
                .andExpect(jsonPath("$.[0].longitude").value(dto.getLongitude()))
                .andExpect(jsonPath("$.[0].latitude").value(dto.getLatitude()))
                .andExpect(jsonPath("$.[0].sites").value(dto.getSites()))
                .andExpect( jsonPath("$.[0].exercices").value(dto.getExercices()));

    }

    @Test
    void findById_withBadId_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get(UrlMapping.Societe.FIND_BY_ID, UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    @Transactional
    void add_shouldCreateSociete() throws Exception {
        mockMvc.perform(
                post(UrlMapping.Societe.ADD)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().isCreated())
                //.andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.version").exists())
                .andExpect(jsonPath("$.enabled").exists())
                .andExpect(jsonPath("$.deleted").exists())
                .andExpect(jsonPath("$.name").value(vm.getName()))
                .andExpect(jsonPath("$.phone").value(vm.getPhone()))
                .andExpect(jsonPath("$.email").value(vm.getEmail()))
                .andExpect(jsonPath("$.longitude").value(vm.getLongitude()))
                .andExpect(jsonPath("$.latitude").value(vm.getLatitude()));
    }

    @Test
    void add_withNameMinLengthExceeded_shouldReturnBadRequest() throws Exception {
        vm.setName(RandomStringUtils.random(SizeMapping.Name.MIN - 1));
        mockMvc.perform(
                post(UrlMapping.Societe.ADD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void add_withNameMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
        vm.setName(RandomStringUtils.random(SizeMapping.Name.MAX + 10));
        mockMvc.perform(post(UrlMapping.Societe.ADD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().isBadRequest());
    }


    @Test
    @Transactional
    void add_withPhoneMinLengthExceeded_shouldReturnBadRequest() throws Exception {
        vm.setPhone(RandomStringUtils.random(SizeMapping.Phone.MIN - 1));
        mockMvc.perform(post(UrlMapping.Societe.ADD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void add_withPhoneMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
        vm.setPhone(RandomStringUtils.random(SizeMapping.Phone.MAX + 2));
        mockMvc.perform(post(UrlMapping.Societe.ADD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void add_withEmailMinLengthExceeded_shouldReturnBadRequest() throws Exception {
        vm.setEmail(RandomStringUtils.random(SizeMapping.Email.MIN - 1));
        mockMvc.perform(post(UrlMapping.Societe.ADD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void add_withEmailMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
        vm.setEmail(RandomStringUtils.random(SizeMapping.Email.MAX + 1));
        mockMvc.perform(post(UrlMapping.Societe.ADD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void update_shouldUpdateSociete() throws Exception {
        dto = service.save(vm);
        vm.setName(TestData.Update.name);
        vm.setPhone(TestData.Update.phone);
        vm.setEmail(TestData.Update.email);
        vm.setLongitude(TestData.Update.longitude);
        vm.setLatitude(TestData.Update.latitude);
        mockMvc.perform(
                put(UrlMapping.Societe.UPDATE, dto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(vm))
        )
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.[0].id").exists())
                .andExpect(jsonPath("$.[0].version").exists())
                .andExpect(jsonPath("$.[0].enabled").exists())
                .andExpect(jsonPath("$.[0].deleted").exists())
                .andExpect(jsonPath("$.[0].name").value(vm.getName()))
                .andExpect(jsonPath("$.[0].phone").value(vm.getPhone()))
                .andExpect(jsonPath("$.[0].email").value(vm.getEmail()))
                .andExpect(jsonPath("$.[0].longitude").value(vm.getLongitude()))
                .andExpect(jsonPath("$.[0].latitude").value(vm.getLatitude()));
    }

    @Test
    void update_withNameMinLengthExceeded_shouldReturnBadRequest() throws Exception {
        dto = service.save(vm);
        vm.setName(RandomStringUtils.random(SizeMapping.Name.MIN - 1));
        mockMvc.perform(put(UrlMapping.Societe.UPDATE, dto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void update_withNameMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
        dto = service.save(vm);
        vm.setName(RandomStringUtils.random(SizeMapping.Name.MAX + 1));
        mockMvc.perform(put(UrlMapping.Societe.UPDATE, dto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().is4xxClientError());
    }


    @Test
    void update_withPhoneMinLengthExceeded_shouldReturnBadRequest() throws Exception {
        dto = service.save(vm);
        vm.setPhone(RandomStringUtils.random(SizeMapping.Phone.MIN - 1));
        mockMvc.perform(put(UrlMapping.Societe.UPDATE, dto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void update_withPhoneMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
        dto = service.save(vm);
        vm.setPhone(RandomStringUtils.random(SizeMapping.Phone.MAX + 1));
        mockMvc.perform(put(UrlMapping.Societe.UPDATE, dto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void update_withEmailMinLengthExceeded_shouldReturnBadRequest() throws Exception {
        dto = service.save(vm);
        vm.setEmail(RandomStringUtils.random(SizeMapping.Email.MIN - 1  ));
        mockMvc.perform(put(UrlMapping.Societe.UPDATE, dto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void update_withEmailMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
        dto = service.save(vm);
        vm.setEmail(RandomStringUtils.random(SizeMapping.Email.MAX + 1));
        mockMvc.perform(put(UrlMapping.Societe.UPDATE, dto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().is4xxClientError());
    }


    @Test
    void delete_shouldDeleteSociete() throws Exception {
        dto = service.save(vm);
        mockMvc.perform(
                delete(UrlMapping.Societe.DELETE, dto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    void delete_withBadId_shouldReturnNotFound() throws Exception {
        dto = service.save(vm);
        mockMvc.perform(
                delete(UrlMapping.Societe.DELETE, UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

}