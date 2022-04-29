package sn.ept.git.seminaire.cicd.repository;

import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit4.SpringRunner;
import sn.ept.git.seminaire.cicd.data.SocieteDTOTestData;
import sn.ept.git.seminaire.cicd.data.TestData;
import sn.ept.git.seminaire.cicd.dto.SocieteDTO;
import sn.ept.git.seminaire.cicd.mappers.SocieteMapper;
import sn.ept.git.seminaire.cicd.models.Societe;
import sn.ept.git.seminaire.cicd.repositories.SocieteRepository;
import sn.ept.git.seminaire.cicd.services.ISocieteService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest()
@RunWith(SpringRunner.class)
@ContextConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SocieteRepositoryTest extends RepositoryBaseTest {

    @Autowired
    @Valid
    SocieteRepository societeRepository;
    @Autowired
    @Valid
    SocieteMapper mapper;
    SocieteDTO dto;
    @Autowired
    @Valid
    ISocieteService service;
    Societe entity;


    @BeforeEach
    void setUp() {
        dto = SocieteDTOTestData.defaultDTO();
        //entity = mapper.asEntity(dto);
        entity = SocieteDTOTestData.defaultEntity(entity);
        societeRepository.deleteAll();
        entity = societeRepository.saveAndFlush(entity);
    }

    @AfterEach
    public void destroyAll(){
        societeRepository.deleteAll();
    }

    @Test
    @Rollback(value = false)
    void givenRepository_whenFindByName_thenResult() {
        dto = SocieteDTOTestData.defaultDTO();
        //entity = mapper.asEntity(dto);
        //entity = societeRepository.saveAndFlush(entity);
        Optional<Societe> optional = societeRepository.findByName(entity.getName());
        assertThat(optional).isNotNull();
        assertThat(optional).isPresent();
    }

    @Test
    void givenRepository_save_shouldSaveSociete() {
        //dto =service.save(vm);
        entity = societeRepository.save(entity);
        assertThat(entity)
                .isNotNull();
        //.hasNoNullFieldsOrProperties();
    }

    @Test
    @Order(3)
    void givenRepository_whenFindById_thenResult(){
        Optional<Societe> optional = societeRepository.findById(entity.getId());
        assertThat(optional).isNotNull();
        assertThat(optional).isPresent();
    }

    @Test
    @Order(2)
    void givenRepository_whenFindAll_thenResult(){
        List<Societe> societes = societeRepository.findAll();
        assertThat(societes.size()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    void givenRepository_whenFindByBadName_thenNotFound() {
        //entity = societeRepository.saveAndFlush(entity);
        Optional<Societe> optional = societeRepository.findByName(UUID.randomUUID().toString());
        assertThat(optional).isNotNull();
        assertThat(optional).isNotPresent();
    }

    @Test
    void givenRepository_whenFindDeleted_thenNotFound() {
        entity.setDeleted(true);
        entity = societeRepository.saveAndFlush(entity);
        Optional<Societe> optional = societeRepository.findByName(entity.getName());
        assertThat(optional).isNotNull();
        assertThat(optional).isNotPresent();
    }

    @Test
    void givenRepository_whenFindByEmail_thenResult() {
        Optional<Societe> optional = societeRepository.findByEmail(entity.getEmail());
        assertThat(optional).isNotNull();
        assertThat(optional).isPresent();
    }

    @Test
    void givenRepository_whenFindByBadEmail_thenNotFound() {
        Optional<Societe> optional = societeRepository.findByEmail(UUID.randomUUID().toString());
        assertThat(optional).isNotNull();
        assertThat(optional).isNotPresent();
    }


    @Test
    void givenRepository_whenFindByPhone_thenResult() {
        Optional<Societe> optional = societeRepository.findByPhone(entity.getPhone());
        assertThat(optional).isNotNull();
        assertThat(optional).isPresent();
    }

    @Test
    void givenRepository_whenFindByBadPhone_thenNotFound() {
        Optional<Societe> optional = societeRepository.findByPhone(UUID.randomUUID().toString());
        assertThat(optional).isNotNull();
        assertThat(optional).isNotPresent();
    }

}
