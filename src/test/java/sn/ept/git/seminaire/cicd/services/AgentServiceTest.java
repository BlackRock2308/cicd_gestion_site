package sn.ept.git.seminaire.cicd.services;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import sn.ept.git.seminaire.cicd.data.AgentVMTestData;
import sn.ept.git.seminaire.cicd.dto.AgentDTO;
import sn.ept.git.seminaire.cicd.dto.vm.AgentVM;
import sn.ept.git.seminaire.cicd.exceptions.ItemNotFoundException;
import sn.ept.git.seminaire.cicd.mappers.AgentMapper;
import sn.ept.git.seminaire.cicd.mappers.vm.AgentVMMapper;
import lombok.extern.slf4j.Slf4j;
import sn.ept.git.seminaire.cicd.models.Agent;
import sn.ept.git.seminaire.cicd.repositories.AgentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
@Transactional()
public class AgentServiceTest extends ServiceBaseTest {

    @Autowired
    protected AgentMapper mapper;
    @Autowired
    protected AgentVMMapper vmMapper;
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    IAgentService service;

    private static Agent agent;
    static AgentVM vm ;
    Agent dto;


    @BeforeAll
    static void beforeAll(){
        log.info(" before all");
        vm = AgentVMTestData.defaultVM();
        agent =  AgentVMTestData.defaultEntity(agent);

    }

    @BeforeEach
    void beforeEach(){
        log.info(" before each");
    }

    @AfterEach
    void afterEach(){
        service.deleteAll();
    }

    @Test
    @Rollback(value = false)
    void save_shouldSaveAgent() {
        agent =  AgentVMTestData.defaultEntity(agent);
        dto = agentRepository.save(agent);
        assertThat(dto)
                .isNotNull();
        //.hasNoNullFieldsOrProperties();
    }


    @Test
    void findById_shouldReturnResult() {
        agent =  AgentVMTestData.defaultEntity(agent);
        dto = agentRepository.save(agent);
        final Optional<Agent> optional = agentRepository.findById(dto.getId());
        assertThat(optional)
                .isNotNull()
                .isPresent()
                .get();
        //.hasNoNullFieldsOrProperties();
    }


    @Test
    void findById_withBadId_ShouldReturnNoResult() {
        agent =  AgentVMTestData.defaultEntity(agent);
        final Optional<Agent> optional = agentRepository.findById(UUID.randomUUID());
        assertThat(optional)
                .isNotNull()
                .isNotPresent();
    }


    @Test
    void delete_shouldDeleteAgent() {
        agent =  AgentVMTestData.defaultEntity(agent);
        dto = agentRepository.save(agent);
        long oldCount = agentRepository.count();
        service.delete(dto.getId());
        long newCount = agentRepository.count();
        assertThat(oldCount).isEqualTo(newCount+1);
    }



    @Test
    void delete_withBadId_ShouldThrowException() {
        assertThrows(
                ItemNotFoundException.class,
                () ->service.delete(UUID.randomUUID())
        );
    }

    @Test
    void findByAll_ShouldReturnResult(){
        agent =  AgentVMTestData.defaultEntity(agent);
        dto = agentRepository.save(agent);
        List<AgentDTO> agents = service.findAll();

        assertThat(agents.stream().count()).isGreaterThan(0);
    }

/*
    update
*/


}