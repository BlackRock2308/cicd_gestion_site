package sn.ept.git.seminaire.cicd.resources;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import sn.ept.git.seminaire.cicd.data.AttachementVMTestData;
import sn.ept.git.seminaire.cicd.data.TestData;
import sn.ept.git.seminaire.cicd.dto.AttachementDTO;
import sn.ept.git.seminaire.cicd.dto.vm.AttachementVM;
import sn.ept.git.seminaire.cicd.services.IAttachementService;
import sn.ept.git.seminaire.cicd.utils.SizeMapping;
import sn.ept.git.seminaire.cicd.utils.TestUtil;
import sn.ept.git.seminaire.cicd.utils.UrlMapping;

import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@SpringBootTest
class AttachementResourceTest extends BasicResourceTest {

    static private AttachementVM vm;
    @Autowired
    private IAttachementService service;
    private AttachementDTO dto;


    @BeforeAll
    static void beforeAll() {
        log.info(" before all ");
    }

    @BeforeEach
    void beforeEach() {
        log.info(" before each ");
        service.deleteAll();
        vm = AttachementVMTestData.defaultVM();
    }

    @Test
    void findAll_shouldReturnAttachements() throws Exception {
        dto = service.save(vm);
        mockMvc.perform(get(UrlMapping.Attachement.ALL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andDo(MockMvcResultHandlers.print()) //can print request details
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[0].id").exists())
                .andExpect(jsonPath("$.content.[0].version").exists())
                .andExpect(jsonPath("$.content.[0].enabled").exists())
                .andExpect(jsonPath("$.content.[0].deleted").exists())
                .andExpect(jsonPath("$.content.[0].enabled", is(true)))
                .andExpect(jsonPath("$.content.[0].deleted").value(false))
                .andExpect(jsonPath("$.content.[0].name", is(dto.getName())))
                .andExpect(jsonPath("$.content.[0].description").value(dto.getDescription()))
                .andExpect(jsonPath("$.content.[0].location").value(dto.getLocation()))
                .andExpect(jsonPath("$.content.[0].hash").value(dto.getHash()));

    }


    @Test
    void findById_shouldReturnAttachement() throws Exception {
        dto = service.save(vm);
        mockMvc.perform(get(UrlMapping.Attachement.FIND_BY_ID, dto.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.version").exists())
                .andExpect(jsonPath("$.enabled").exists())
                .andExpect(jsonPath("$.deleted").exists())
                .andExpect(jsonPath("$.name", is(dto.getName())))
                .andExpect(jsonPath("$.description").value(dto.getDescription()))
                .andExpect(jsonPath("$.location").value(dto.getLocation()))
                .andExpect(jsonPath("$.hash").value(dto.getHash()));
    }

    @Test
    void findById_withBadId_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get(UrlMapping.Attachement.FIND_BY_ID, UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void add_shouldCreateAttachement() throws Exception {
        mockMvc.perform(
                post(UrlMapping.Attachement.ADD)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(vm))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.version").exists())
                .andExpect(jsonPath("$.enabled").exists())
                .andExpect(jsonPath("$.deleted").exists())
                .andExpect(jsonPath("$.name").value(vm.getName()))
                .andExpect(jsonPath("$.description").value(vm.getDescription()))
                .andExpect(jsonPath("$.location").value(vm.getLocation()))
                .andExpect(jsonPath("$.hash").value(vm.getHash()));
    }

    @Test
    void add_withNameMinLengthExceeded_shouldReturnBadRequest() throws Exception {
        vm.setName(RandomStringUtils.random(SizeMapping.Name.MIN - 1));
        mockMvc.perform(post(UrlMapping.Attachement.ADD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void add_withNameMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
        vm.setName(RandomStringUtils.random(SizeMapping.Name.MAX + 1));
        mockMvc.perform(post(UrlMapping.Attachement.ADD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void add_withDescriptionMinLengthExceeded_shouldReturnBadRequest() throws Exception {
        vm.setDescription(RandomStringUtils.random(SizeMapping.Phone.MIN - 1));
        mockMvc.perform(post(UrlMapping.Attachement.ADD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void add_withDescriptionMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
        vm.setDescription(RandomStringUtils.random(SizeMapping.Phone.MAX + 1));
        mockMvc.perform(post(UrlMapping.Attachement.ADD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void add_withLocationMinLengthExceeded_shouldReturnBadRequest() throws Exception {
        vm.setLocation(RandomStringUtils.random(SizeMapping.Location.MIN - 1));
        mockMvc.perform(post(UrlMapping.Attachement.ADD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void add_withLocationMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
        vm.setLocation(RandomStringUtils.random(SizeMapping.Location.MAX + 1));
        mockMvc.perform(post(UrlMapping.Attachement.ADD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void update_shouldUpdateAttachement() throws Exception {
        dto = service.save(vm);
        vm.setName(TestData.Update.name);
        vm.setDescription(TestData.Update.description);
        vm.setLocation(TestData.Update.location);
        vm.setHash(TestData.Update.hash);
        mockMvc.perform(
                put(UrlMapping.Attachement.UPDATE, dto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(vm))
        )
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.version").exists())
                .andExpect(jsonPath("$.enabled").exists())
                .andExpect(jsonPath("$.deleted").exists())
                .andExpect(jsonPath("$.name").value(vm.getName()))
                .andExpect(jsonPath("$.description").value(vm.getDescription()))
                .andExpect(jsonPath("$.location").value(vm.getLocation()))
                .andExpect(jsonPath("$.hash").value(vm.getHash()));
    }

    @Test
    void update_withNameMinLengthExceeded_shouldReturnBadRequest() throws Exception {
        dto = service.save(vm);
        vm.setName(RandomStringUtils.random(SizeMapping.Name.MIN - 1));
        mockMvc.perform(put(UrlMapping.Attachement.UPDATE, dto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_withNameMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
        dto = service.save(vm);
        vm.setName(RandomStringUtils.random(SizeMapping.Name.MAX + 1));
        mockMvc.perform(put(UrlMapping.Attachement.UPDATE, dto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void update_withDescriptionMinLengthExceeded_shouldReturnBadRequest() throws Exception {
        dto = service.save(vm);
        vm.setDescription(RandomStringUtils.random(SizeMapping.Phone.MIN - 1));
        mockMvc.perform(put(UrlMapping.Attachement.UPDATE, dto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_withDescriptionMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
        dto = service.save(vm);
        vm.setDescription(RandomStringUtils.random(SizeMapping.Phone.MAX + 1));
        mockMvc.perform(put(UrlMapping.Attachement.UPDATE, dto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_withLocationMinLengthExceeded_shouldReturnBadRequest() throws Exception {
        dto = service.save(vm);
        vm.setLocation(RandomStringUtils.random(SizeMapping.Location.MIN - 1));
        mockMvc.perform(put(UrlMapping.Attachement.UPDATE, dto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_withLocationMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
        dto = service.save(vm);
        vm.setLocation(RandomStringUtils.random(SizeMapping.Location.MAX + 1));
        mockMvc.perform(put(UrlMapping.Attachement.UPDATE, dto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void delete_shouldDeleteAttachement() throws Exception {
        dto = service.save(vm);
        mockMvc.perform(
                delete(UrlMapping.Attachement.DELETE, dto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    void delete_withBadId_shouldReturnNotFound() throws Exception {
        dto = service.save(vm);
        mockMvc.perform(
                delete(UrlMapping.Attachement.DELETE, UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

}