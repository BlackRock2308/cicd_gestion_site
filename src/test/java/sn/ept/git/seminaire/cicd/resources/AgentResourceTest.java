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
import sn.ept.git.seminaire.cicd.data.AgentDTOTestData;
import sn.ept.git.seminaire.cicd.data.AgentVMTestData;
import sn.ept.git.seminaire.cicd.data.TestData;
import sn.ept.git.seminaire.cicd.dto.AgentDTO;
import sn.ept.git.seminaire.cicd.dto.vm.AgentVM;
import sn.ept.git.seminaire.cicd.mappers.AgentMapper;
import sn.ept.git.seminaire.cicd.mappers.vm.AgentVMMapper;
import sn.ept.git.seminaire.cicd.models.Agent;
import sn.ept.git.seminaire.cicd.services.IAgentService;
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
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class AgentResourceTest extends BasicResourceTest {

    static private AgentVM vm;
    @Autowired
    private IAgentService service;
    static private AgentDTO dto;
    @Autowired
    AgentVMMapper vmMapper;
    @Autowired
    AgentMapper mapper;
    @Autowired
    private ObjectMapper objectMapper;
    private static Agent agent;

    @BeforeAll
    static void beforeAll() {
        log.info(" before all ");
    }

    @BeforeEach
    void beforeEach() {
        log.info(" before each ");
        //service.deleteAll();
        //vm = AgentVMTestData.defaultVM();
    }

    @Test
    void findAll_shouldReturnAgents() throws Exception {
        agent = AgentDTOTestData.defaultEntity(agent);
        vm = vmMapper.asDTO(agent);
        mockMvc.perform(get(UrlMapping.Agent.ALL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo(MockMvcResultHandlers.print()) //can print request details
                //.andDo(MockMvcResultHandlers.print()) //can print request details
                //.andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[0].id").exists())
                .andExpect(jsonPath("$.content.[0].version").exists())
                .andExpect(jsonPath("$.content.[0].enabled").exists())
                .andExpect(jsonPath("$.content.[0].deleted").exists())
                .andExpect(jsonPath("$.content.[0].enabled", is(true)))
                .andExpect(jsonPath("$.content.[0].deleted").value(false))
                .andExpect(jsonPath("$.content.[0].name", is(vm.getFirstName())))
                .andExpect(jsonPath("$.content.[0].phone").value(vm.getPhone()))
                .andExpect(jsonPath("$.content.[0].email").value(vm.getEmail()))
                .andExpect(jsonPath("$.content.[0].longitude").value(vm.getLastName()))
                .andExpect(jsonPath("$.content.[0].latitude").value(vm.getAddress()));

    }


    @Test
    void findById_shouldReturnAgent() throws Exception {
        dto = service.save(vm);
        mockMvc.perform(get(UrlMapping.Agent.FIND_BY_ID, dto.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.version").exists())
                .andExpect(jsonPath("$.enabled").exists())
                .andExpect(jsonPath("$.deleted").exists())
                .andExpect(jsonPath("$.content.[0].name", is(dto.getFirstName())))
                .andExpect(jsonPath("$.content.[0].phone").value(dto.getPhone()))
                .andExpect(jsonPath("$.content.[0].email").value(dto.getEmail()))
                .andExpect(jsonPath("$.content.[0].longitude").value(dto.getLastName()))
                .andExpect(jsonPath("$.content.[0].latitude").value(dto.getAddress()));;
    }

    @Test
    void findById_withBadId_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get(UrlMapping.Agent.FIND_BY_ID, UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void add_shouldCreateAgent() throws Exception {
        mockMvc.perform(
                post(UrlMapping.Agent.ADD)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(vm))
        )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.version").exists())
                .andExpect(jsonPath("$.enabled").exists())
                .andExpect(jsonPath("$.deleted").exists())
                .andExpect(jsonPath("$.content.[0].name", is(dto.getFirstName())))
                .andExpect(jsonPath("$.content.[0].phone").value(dto.getPhone()))
                .andExpect(jsonPath("$.content.[0].email").value(dto.getEmail()))
                .andExpect(jsonPath("$.content.[0].longitude").value(dto.getLastName()))
                .andExpect(jsonPath("$.content.[0].latitude").value(dto.getAddress()));;
    }

    @Test
    void add_withlastNameMinLengthExceeded_shouldReturnBadRequest() throws Exception {
        agent = AgentDTOTestData.defaultEntity(agent);
        dto = mapper.asDTO(agent);
        dto.setFirstName(RandomStringUtils.random(SizeMapping.Name.MIN - 1));
        mockMvc.perform(post(UrlMapping.Agent.ADD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(dto)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void add_withfirstNameMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
        agent = AgentDTOTestData.defaultEntity(agent);
        dto = mapper.asDTO(agent);
        dto.setFirstName(RandomStringUtils.random(SizeMapping.Name.MAX + 1));
        mockMvc.perform(
                 post(UrlMapping.Agent.ADD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(dto)))
                .andExpect(status().is5xxServerError());
    }


    @Test
    void add_withPhoneMinLengthExceeded_shouldReturnBadRequest() throws Exception {
        agent = AgentDTOTestData.defaultEntity(agent);
        dto = mapper.asDTO(agent);
        dto.setPhone(RandomStringUtils.random(SizeMapping.Phone.MIN - 1));
        mockMvc.perform(post(UrlMapping.Agent.ADD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(dto)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void add_withPhoneMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
        agent = AgentDTOTestData.defaultEntity(agent);
        vm = vmMapper.asDTO(agent);
        dto.setPhone(RandomStringUtils.random(SizeMapping.Phone.MAX + 1));
        mockMvc.perform(post(UrlMapping.Agent.ADD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().is4xxClientError());
    }


    @Test
    void add_withEmailMinLengthExceeded_shouldReturnBadRequest() throws Exception {
        agent = AgentDTOTestData.defaultEntity(agent);
        vm = vmMapper.asDTO(agent);
        vm.setEmail(RandomStringUtils.random(SizeMapping.Email.MIN - 1));
        mockMvc.perform(post(UrlMapping.Agent.ADD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void add_withEmailMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
        agent = AgentDTOTestData.defaultEntity(agent);
        vm = vmMapper.asDTO(agent);
        vm.setEmail(RandomStringUtils.random(SizeMapping.Email.MAX + 1));
        mockMvc.perform(post(UrlMapping.Agent.ADD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().is5xxServerError());
    }


    @Test
    void update_shouldUpdateAgent() throws Exception {
        agent = AgentDTOTestData.defaultEntity(agent);
        vm = vmMapper.asDTO(agent);
        vm.setFirstName(TestData.Update.firstName);
        vm.setPhone(TestData.Update.phone);
        vm.setEmail(TestData.Update.email);
        vm.setLastName(TestData.Update.lastName);
        vm.setAddress(TestData.Update.address);
        mockMvc.perform(
                put(UrlMapping.Agent.UPDATE, vm.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(vm))
        )
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.version").exists())
                .andExpect(jsonPath("$.enabled").exists())
                .andExpect(jsonPath("$.deleted").exists())
                .andExpect(jsonPath("$.content.[0].name", is(vm.getFirstName())))
                .andExpect(jsonPath("$.content.[0].phone").value(vm.getPhone()))
                .andExpect(jsonPath("$.content.[0].email").value(vm.getEmail()))
                .andExpect(jsonPath("$.content.[0].longitude").value(vm.getLastName()))
                .andExpect(jsonPath("$.content.[0].latitude").value(vm.getAddress()));;
    }

    @Test
    void update_withNameMinLengthExceeded_shouldReturnBadRequest() throws Exception {
        agent = AgentDTOTestData.defaultEntity(agent);
        vm = vmMapper.asDTO(agent);
        vm.setFirstName(RandomStringUtils.random(SizeMapping.Name.MIN - 1));
        mockMvc.perform(put(UrlMapping.Agent.UPDATE, vm.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void update_withNameMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
        agent = AgentDTOTestData.defaultEntity(agent);
        vm = vmMapper.asDTO(agent);
        vm.setFirstName(RandomStringUtils.random(SizeMapping.Name.MAX + 1));
        mockMvc.perform(put(UrlMapping.Agent.UPDATE, vm.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().is4xxClientError());
    }


    @Test
    void update_withPhoneMinLengthExceeded_shouldReturnBadRequest() throws Exception {
        agent = AgentDTOTestData.defaultEntity(agent);
        vm = vmMapper.asDTO(agent);
        vm.setPhone(RandomStringUtils.random(SizeMapping.Phone.MIN - 1));
        mockMvc.perform(put(UrlMapping.Agent.UPDATE, vm.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void update_withPhoneMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
        agent = AgentDTOTestData.defaultEntity(agent);
        vm = vmMapper.asDTO(agent);
        vm.setPhone(RandomStringUtils.random(SizeMapping.Phone.MAX + 1));
        mockMvc.perform(put(UrlMapping.Agent.UPDATE, vm.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void update_withEmailMinLengthExceeded_shouldReturnBadRequest() throws Exception {
        agent = AgentDTOTestData.defaultEntity(agent);
        vm = vmMapper.asDTO(agent);
        vm.setEmail(RandomStringUtils.random(SizeMapping.Email.MIN - 1));
        mockMvc.perform(put(UrlMapping.Agent.UPDATE, vm.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void update_withEmailMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
        agent = AgentDTOTestData.defaultEntity(agent);
        vm = vmMapper.asDTO(agent);
        vm.setEmail(RandomStringUtils.random(SizeMapping.Email.MAX + 1));
        mockMvc.perform(put(UrlMapping.Agent.UPDATE, vm.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().is4xxClientError());
    }


    @Test
    void delete_shouldDeleteAgent() throws Exception {
        //dto = service.save(vm);
        agent = AgentDTOTestData.defaultEntity(agent);
        dto = mapper.asDTO(agent);
        mockMvc.perform(
                delete(UrlMapping.Agent.DELETE, dto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is4xxClientError()); //error
    }

    @Test
    void delete_withBadId_shouldReturnNotFound() throws Exception {
        agent = AgentDTOTestData.defaultEntity(agent);
        dto = mapper.asDTO(agent);
        //dto = service.save(vm);
        mockMvc.perform(
                delete(UrlMapping.Agent.DELETE, UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

}