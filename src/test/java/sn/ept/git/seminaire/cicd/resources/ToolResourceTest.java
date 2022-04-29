package sn.ept.git.seminaire.cicd.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sn.ept.git.seminaire.cicd.data.ToolVMTestData;
import sn.ept.git.seminaire.cicd.data.TypeVMTestData;
import sn.ept.git.seminaire.cicd.data.TestData;
import sn.ept.git.seminaire.cicd.dto.ToolDTO;
import sn.ept.git.seminaire.cicd.dto.TypeDTO;
import sn.ept.git.seminaire.cicd.dto.vm.ToolVM;
import sn.ept.git.seminaire.cicd.dto.vm.TypeVM;
import sn.ept.git.seminaire.cicd.services.IToolService;
import sn.ept.git.seminaire.cicd.services.ITypeService;
import sn.ept.git.seminaire.cicd.utils.SizeMapping;
import sn.ept.git.seminaire.cicd.utils.TestUtil;
import sn.ept.git.seminaire.cicd.utils.UrlMapping;

import javax.transaction.Transactional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class ToolResourceTest extends BasicResourceTest {

    static private ToolVM vm;
    @Autowired
    private IToolService service;
    static private ToolDTO dto;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll() {
        log.info(" before all ");
    }

    @BeforeEach
    void beforeEach() {
        log.info(" before each ");
        service.deleteAll();
        vm = ToolVMTestData.defaultVM();
    }

    @Test
    void findAll_shouldReturnTypes() throws Exception {
        dto = service.save(vm);
        mockMvc.perform(get(UrlMapping.Tool.ALL)
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
                .andExpect(jsonPath("$.[0].description").value(dto.getDescription()));
    }




    @Test
    void findById_shouldReturnType() throws Exception {
        dto = service.save(vm);
        mockMvc.perform(get(UrlMapping.Tool.FIND_BY_ID, dto.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.[0].id").exists())
                .andExpect(jsonPath("$.[0].version").exists())
                .andExpect(jsonPath("$.[0].enabled").exists())
                .andExpect(jsonPath("$.[0].deleted").exists())
                .andExpect(jsonPath("$.[0].name", is(dto.getName())))
                .andExpect(jsonPath("$.[0].description").value(dto.getDescription()));

    }

    @Test
    void findById_withBadId_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get(UrlMapping.Tool.FIND_BY_ID, UUID.randomUUID().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    @Transactional
    void add_shouldCreateType() throws Exception {
        mockMvc.perform(
                        post(UrlMapping.Tool.ADD)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.version").exists())
                .andExpect(jsonPath("$.enabled").exists())
                .andExpect(jsonPath("$.deleted").exists())
                .andExpect(jsonPath("$.name").value(vm.getName()))
                .andExpect(jsonPath("$.description").value(vm.getDescription()));
    }

    @Test
    void add_withNameMinLengthExceeded_shouldReturnBadRequest() throws Exception {
        vm.setName(RandomStringUtils.random(SizeMapping.Name.MIN - 1));
        mockMvc.perform(
                        post(UrlMapping.Tool.ADD)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                        //.content(objectMapper.writeValueAsString(vm))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void add_withNameMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
        vm.setName(RandomStringUtils.random(SizeMapping.Name.MAX + 10));
        String q = objectMapper.writeValueAsString(vm);
        mockMvc.perform(post(UrlMapping.Tool.ADD)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                        //.content(q)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_shouldUpdateType() throws Exception {
        vm.setName(TestData.Update.name);
        vm.setDescription(TestData.Update.phone);
        dto = service.save(vm);
        mockMvc.perform(
                        put(UrlMapping.Tool.UPDATE, dto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(vm))
                )
                .andExpect(status().isAccepted())
                //.andExpect(jsonPath("$.[0].id").exists())
                .andExpect(jsonPath("$.[0].version").exists())
                .andExpect(jsonPath("$.[0].enabled").exists())
                .andExpect(jsonPath("$.[0].deleted").exists())
                .andExpect(jsonPath("$.[0].name").value(vm.getName()))
                .andExpect(jsonPath("$.[0].description").value(vm.getDescription()));
    }

    @Test
    void update_withNameMinLengthExceeded_shouldReturnBadRequest() throws Exception {
        dto = service.save(vm);
        vm.setName(RandomStringUtils.random(SizeMapping.Name.MIN - 1));
        mockMvc.perform(put(UrlMapping.Tool.UPDATE, dto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(vm))
                )
                .andExpect(status().is4xxClientError());
    }

    @Test
    void update_withNameMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
        dto = service.save(vm);
        vm.setName(RandomStringUtils.random(SizeMapping.Name.MAX + 1));
        mockMvc.perform(put(UrlMapping.Tool.UPDATE, dto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().is4xxClientError());
    }


    @Test
    void delete_shouldDeleteType() throws Exception {
        dto = service.save(vm);
        mockMvc.perform(
                delete(UrlMapping.Tool.DELETE, dto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    void delete_withBadId_shouldReturnNotFound() throws Exception {
        dto = service.save(vm);
        mockMvc.perform(
                delete(UrlMapping.Tool.DELETE, UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

}