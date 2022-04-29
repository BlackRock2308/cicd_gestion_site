package sn.ept.git.seminaire.cicd.repository;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.ept.git.seminaire.cicd.data.SiteDTOTestData;
import sn.ept.git.seminaire.cicd.dto.SiteDTO;
import sn.ept.git.seminaire.cicd.mappers.SiteMapper;
import sn.ept.git.seminaire.cicd.models.Site;
import sn.ept.git.seminaire.cicd.models.Societe;
import sn.ept.git.seminaire.cicd.repositories.SiteRepository;
import sn.ept.git.seminaire.cicd.services.ISiteService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SiteRepositoryTest extends RepositoryBaseTest {

    @Autowired
    @Valid
    SiteRepository siteRepository;
    @Autowired
    @Valid
    SiteMapper mapper;
    SiteDTO dto;

    @Autowired
    @Valid
    ISiteService service;
    Site entity;


    @BeforeEach
    void setUp() {
        dto = SiteDTOTestData.defaultDTO();
        //entity = mapper.asEntity(dto);
        entity = SiteDTOTestData.defaultEntity(entity);
        siteRepository.deleteAll();
        entity = siteRepository.saveAndFlush(entity);
    }

    @Test
    @Order(3)
    void givenRepository_save_shouldSaveSite() {
        entity = siteRepository.save(entity);
        assertThat(entity)
                .isNotNull();
        //.hasNoNullFieldsOrProperties();
    }

    @Test
    @Order(2)
    void givenRepository_whenFindAll_thenResult(){
        List<Site> sites = siteRepository.findAll();
        assertThat(sites.size()).isGreaterThan(0);
    }


    @Test
    @Order(4)
    void givenRepository_whenFindById_thenResult(){
        Optional<Site> optional = siteRepository.findById(entity.getId());
        assertThat(optional).isNotNull();
        assertThat(optional).isPresent();
    }

    @Test
    void givenRepository_whenFindByName_thenResult() {
        Optional<Site> optional = siteRepository.findByName(entity.getName());
        assertThat(optional).isNotNull();
        assertThat(optional).isPresent();
    }

    @Test
    @Order(1)
    void givenRepository_whenFindByBadName_thenNotFound() {
        Optional<Site> optional = siteRepository.findByName(UUID.randomUUID().toString());
        assertThat(optional).isNotNull();
        assertThat(optional).isNotPresent();
    }

    @Test
    void givenRepository_whenFindDeleted_thenNotFound() {
        entity.setDeleted(true);
        entity = siteRepository.saveAndFlush(entity);
        Optional<Site> optional = siteRepository.findByName(entity.getName());
        assertThat(optional).isNotNull();
        assertThat(optional).isNotPresent();
    }

}
