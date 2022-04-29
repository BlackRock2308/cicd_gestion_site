package sn.ept.git.seminaire.cicd.repository;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.ept.git.seminaire.cicd.data.AgentDTOTestData;
import sn.ept.git.seminaire.cicd.dto.AgentDTO;
import sn.ept.git.seminaire.cicd.mappers.AgentMapper;
import sn.ept.git.seminaire.cicd.models.Agent;
import sn.ept.git.seminaire.cicd.models.Site;
import sn.ept.git.seminaire.cicd.models.Societe;
import sn.ept.git.seminaire.cicd.repositories.AgentRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AgentRepositoryTest extends RepositoryBaseTest {

    @Autowired
    @Valid
    AgentRepository agentRepository;
    @Autowired
    @Valid
    AgentMapper mapper;
    AgentDTO dto;

    Agent entity;


    @BeforeEach
    void setUp() {
        dto = AgentDTOTestData.defaultDTO();
        //entity = mapper.asEntity(dto);
        entity = AgentDTOTestData.defaultEntity(entity);
        agentRepository.deleteAll();
        entity = agentRepository.saveAndFlush(entity);
    }

    @AfterEach
    public void destroyAll(){
        //agentRepository.deleteAll();
    }

    @Test
    void givenRepository_save_shouldSaveAgent() {
        //dto =service.save(vm);
        entity = agentRepository.save(entity);
        assertThat(entity)
                .isNotNull();
        //.hasNoNullFieldsOrProperties();
    }

    @Test
    @Order(3)
    void givenRepository_whenFindById_thenResult(){
        Optional<Agent> optional = agentRepository.findById(entity.getId());
        assertThat(optional).isNotNull();
        assertThat(optional).isPresent();
    }


    @Test
    @Order(2)
    void givenRepository_whenFindAll_thenResult(){
        List<Agent> agents = agentRepository.findAll();
        assertThat(agents.size()).isGreaterThan(0);
    }


    @Test
    void givenRepository_whenFindByEmail_thenResult() {
        Optional<Agent> optional = agentRepository.findByEmail(entity.getEmail());
        assertThat(optional).isNotNull();
        assertThat(optional).isPresent();
    }

    @Test
    @Order(1)
    void givenRepository_whenFindByBadEmail_thenNotFound() {
        Optional<Agent> optional = agentRepository.findByEmail(UUID.randomUUID().toString());
        assertThat(optional).isNotNull();
        assertThat(optional).isNotPresent();
    }


    @Test
    void givenRepository_whenFindByPhone_thenResult() {
        Optional<Agent> optional = agentRepository.findByPhone(entity.getPhone());
        assertThat(optional).isNotNull();
        assertThat(optional).isPresent();
    }

    @Test
    void givenRepository_whenFindByBadPhone_thenNotFound() {
        Optional<Agent> optional = agentRepository.findByPhone(UUID.randomUUID().toString());
        assertThat(optional).isNotNull();
        assertThat(optional).isNotPresent();
    }

    @Test
    void givenRepository_whenFindDeleted_thenNotFound() {
        entity.setDeleted(true);
        entity = agentRepository.saveAndFlush(entity);
        Optional<Agent> optional = agentRepository.findByEmail(entity.getEmail());
        assertThat(optional).isNotNull();
        assertThat(optional).isNotPresent();
    }


}
