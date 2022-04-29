package sn.ept.git.seminaire.cicd.repository;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.ept.git.seminaire.cicd.data.AttachementDTOTestData;
import sn.ept.git.seminaire.cicd.dto.AttachementDTO;
import sn.ept.git.seminaire.cicd.mappers.AttachementMapper;
import sn.ept.git.seminaire.cicd.models.Attachement;
import sn.ept.git.seminaire.cicd.repositories.AttachementRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AttachementRepositoryTest extends RepositoryBaseTest {

    @Autowired
    @Valid
    AttachementRepository attachementRepository;
    @Autowired
    @Valid
    AttachementMapper mapper;
    AttachementDTO dto;

    Attachement entity;


    @BeforeEach
    void setUp() {
        dto = AttachementDTOTestData.defaultDTO();
        //entity = mapper.asEntity(dto);
        entity = AttachementDTOTestData.defaultEntity(entity);
        //attachementRepository.deleteAll();
        entity = attachementRepository.saveAndFlush(entity);
    }

    @AfterEach
    public void destroyAll(){
        attachementRepository.deleteAll();
    }

    @Test
    @Rollback(value = false)
    void givenRepository_save_shouldSaveAttachement() {
        entity = attachementRepository.save(entity);
        assertThat(entity)
                .isNotNull();
        //.hasNoNullFieldsOrProperties();
    }

    @Test
    @Order(3)
    void givenRepository_whenFindById_thenResult(){
        Optional<Attachement> optional = attachementRepository.findById(entity.getId());
        assertThat(optional).isNotNull();
        assertThat(optional).isPresent();
    }


    @Test
    @Order(2)
    void givenRepository_whenFindAll_thenResult(){
        List<Attachement> interventions = attachementRepository.findAll();
        assertThat(interventions.size()).isGreaterThan(0);
    }


    @Test
    void givenRepository_whenFindByEmail_thenResult() {
        Optional<Attachement> optional = attachementRepository.findById(entity.getId());
        assertThat(optional).isNotNull();
        assertThat(optional).isPresent();
    }


}

