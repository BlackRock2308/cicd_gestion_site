package sn.ept.git.seminaire.cicd.repository;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.ept.git.seminaire.cicd.data.InterventionDTOTestData;
import sn.ept.git.seminaire.cicd.dto.InterventionDTO;
import sn.ept.git.seminaire.cicd.mappers.InterventionMapper;
import sn.ept.git.seminaire.cicd.models.Intervention;
import sn.ept.git.seminaire.cicd.repositories.InterventionRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InterventionRepositoryTest extends RepositoryBaseTest {

    @Autowired
    @Valid
    InterventionRepository interventionRepository;
    @Autowired
    @Valid
    InterventionMapper mapper;
    InterventionDTO dto;

    Intervention entity;


    @BeforeEach
    void setUp() {
        dto = InterventionDTOTestData.defaultDTO();
        //entity = mapper.asEntity(dto);
        entity = InterventionDTOTestData.defaultEntity(entity);
        interventionRepository.deleteAll();
        entity = interventionRepository.saveAndFlush(entity);
    }

    @AfterEach
    public void destroyAll(){
        //agentRepository.deleteAll();
    }

    @Test
    void givenRepository_save_shouldSaveAgent() {
        //dto =service.save(vm);
        entity = interventionRepository.save(entity);
        assertThat(entity)
                .isNotNull();
        //.hasNoNullFieldsOrProperties();
    }

    @Test
    @Order(2)
    void givenRepository_whenFindById_thenResult(){
        Optional<Intervention> optional = interventionRepository.findById(entity.getId());
        assertThat(optional).isNotNull();
        assertThat(optional).isPresent();
    }


    @Test
    @Order(1)
    void givenRepository_whenFindAll_thenResult(){
        List<Intervention> interventions = interventionRepository.findAll();
        assertThat(interventions.size()).isGreaterThan(0);
    }


    @Test
    void givenRepository_whenFindByEmail_thenResult() {
        Optional<Intervention> optional = interventionRepository.findById(entity.getId());
        assertThat(optional).isNotNull();
        assertThat(optional).isPresent();
    }




}

