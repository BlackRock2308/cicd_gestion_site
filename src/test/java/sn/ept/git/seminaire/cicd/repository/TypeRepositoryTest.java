package sn.ept.git.seminaire.cicd.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.ept.git.seminaire.cicd.data.TypeDTOTestData;
import sn.ept.git.seminaire.cicd.dto.TypeDTO;
import sn.ept.git.seminaire.cicd.mappers.TypeMapper;
import sn.ept.git.seminaire.cicd.models.Type;
import sn.ept.git.seminaire.cicd.repositories.TypeRepository;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration

class TypeRepositoryTest extends RepositoryBaseTest {

    @Autowired
    @Valid
    TypeRepository repository;
    @Autowired
    @Valid
    TypeMapper mapper;
    TypeDTO dto;

    Type entity;



    @BeforeEach
    void setUp() {
        dto = TypeDTOTestData.defaultDTO();
        entity = mapper.asEntity(dto);
        repository.deleteAll();
        entity = repository.saveAndFlush(entity);
    }

    @Test
    void givenRepository_whenFindByName_thenResult() {
        Optional<Type> optional = repository.findByName(entity.getName());
        assertThat(optional).isNotNull();
        assertThat(optional).isPresent();
    }

    @Test
    void givenRepository_whenFindByBadName_thenNotFound() {
        Optional<Type> optional = repository.findByName(UUID.randomUUID().toString());
        assertThat(optional).isNotNull();
        assertThat(optional).isNotPresent();
    }

    @Test
    void givenRepository_whenFindDeleted_thenNotFound() {
        entity.setDeleted(true);
        entity = repository.saveAndFlush(entity);
        Optional<Type> optional = repository.findByName(entity.getName());
        assertThat(optional).isNotNull();
        assertThat(optional).isNotPresent();
    }


}
