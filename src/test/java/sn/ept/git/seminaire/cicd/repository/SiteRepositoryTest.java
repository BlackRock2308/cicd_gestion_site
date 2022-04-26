package sn.ept.git.seminaire.cicd.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.ept.git.seminaire.cicd.data.SiteDTOTestData;
import sn.ept.git.seminaire.cicd.dto.SiteDTO;
import sn.ept.git.seminaire.cicd.mappers.SiteMapper;
import sn.ept.git.seminaire.cicd.models.Site;
import sn.ept.git.seminaire.cicd.repositories.SiteRepository;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration

class SiteRepositoryTest extends RepositoryBaseTest {

    @Autowired
    @Valid
    SiteRepository repository;
    @Autowired
    @Valid
    SiteMapper mapper;
    SiteDTO dto;

    Site entity;



    @BeforeEach
    void setUp() {
        dto = SiteDTOTestData.defaultDTO();
        entity = mapper.asEntity(dto);
        repository.deleteAll();
        entity = repository.saveAndFlush(entity);
    }

    @Test
    void givenRepository_whenFindByName_thenResult() {
        Optional<Site> optional = repository.findByName(entity.getName());
        assertThat(optional).isNotNull();
        assertThat(optional).isPresent();
    }

    @Test
    void givenRepository_whenFindByBadName_thenNotFound() {
        Optional<Site> optional = repository.findByName(UUID.randomUUID().toString());
        assertThat(optional).isNotNull();
        assertThat(optional).isNotPresent();
    }

    @Test
    void givenRepository_whenFindDeleted_thenNotFound() {
        entity.setDeleted(true);
        entity = repository.saveAndFlush(entity);
        Optional<Site> optional = repository.findByName(entity.getName());
        assertThat(optional).isNotNull();
        assertThat(optional).isNotPresent();
    }

}
