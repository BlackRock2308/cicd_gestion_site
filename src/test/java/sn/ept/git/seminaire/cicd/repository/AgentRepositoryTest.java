package sn.ept.git.seminaire.cicd.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.ept.git.seminaire.cicd.data.AgentDTOTestData;
import sn.ept.git.seminaire.cicd.dto.AgentDTO;
import sn.ept.git.seminaire.cicd.mappers.AgentMapper;
import sn.ept.git.seminaire.cicd.models.Agent;
import sn.ept.git.seminaire.cicd.repositories.AgentRepository;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration

class AgentRepositoryTest extends RepositoryBaseTest {

    @Autowired
    @Valid
    AgentRepository repository;
    @Autowired
    @Valid
    AgentMapper mapper;
    AgentDTO dto;

    Agent entity;



    @BeforeEach
    void setUp() {
        dto = AgentDTOTestData.defaultDTO();
        entity = mapper.asEntity(dto);
        repository.deleteAll();
        entity = repository.saveAndFlush(entity);
    }



    @Test
    void givenRepository_whenFindByEmail_thenResult() {
        Optional<Agent> optional = repository.findByEmail(entity.getEmail());
        assertThat(optional).isNotNull();
        assertThat(optional).isPresent();
    }

    @Test
    void givenRepository_whenFindByBadEmail_thenNotFound() {
        Optional<Agent> optional = repository.findByEmail(UUID.randomUUID().toString());
        assertThat(optional).isNotNull();
        assertThat(optional).isNotPresent();
    }



    @Test
    void givenRepository_whenFindByPhone_thenResult() {
        Optional<Agent> optional = repository.findByPhone(entity.getPhone());
        assertThat(optional).isNotNull();
        assertThat(optional).isPresent();
    }

    @Test
    void givenRepository_whenFindByBadPhone_thenNotFound() {
        Optional<Agent> optional = repository.findByPhone(UUID.randomUUID().toString());
        assertThat(optional).isNotNull();
        assertThat(optional).isNotPresent();
    }
}
