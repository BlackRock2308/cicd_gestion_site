package sn.ept.git.seminaire.cicd.repository;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.ept.git.seminaire.cicd.data.TypeDTOTestData;
import sn.ept.git.seminaire.cicd.dto.TypeDTO;
import sn.ept.git.seminaire.cicd.mappers.TypeMapper;
import sn.ept.git.seminaire.cicd.models.Societe;
import sn.ept.git.seminaire.cicd.models.Type;
import sn.ept.git.seminaire.cicd.repositories.TypeRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TypeRepositoryTest extends RepositoryBaseTest {

    @Autowired
    @Valid
    TypeRepository typeRepository;
    @Autowired
    @Valid
    TypeMapper mapper;
    TypeDTO dto;

    Type entity;



    @BeforeEach
    void setUp() {
        dto = TypeDTOTestData.defaultDTO();
        //entity =
        entity = TypeDTOTestData.defaultEntity(entity);
        typeRepository.deleteAll();
        entity = typeRepository.saveAndFlush(entity);
    }

    @Test
    void givenRepository_save_shouldSaveType() {
        //dto =service.save(vm);
        entity = typeRepository.save(entity);
        assertThat(entity)
                .isNotNull();
        //.hasNoNullFieldsOrProperties();
    }

    @Test
    void givenRepository_whenFindAll_thenResult(){
        List<Type> types = typeRepository.findAll();
        assertThat(types.size()).isGreaterThan(0);
    }

    @Test
    void givenRepository_whenFindByName_thenResult() {
        Optional<Type> optional = typeRepository.findByName(entity.getName());
        assertThat(optional).isNotNull();
        assertThat(optional).isPresent();
    }

    @Test
    @Order(1)
    void givenRepository_whenFindByBadName_thenNotFound() {
        Optional<Type> optional = typeRepository.findByName(UUID.randomUUID().toString());
        assertThat(optional).isNotNull();
        assertThat(optional).isNotPresent();
    }

    @Test
    void givenRepository_whenFindDeleted_thenNotFound() {
        entity.setDeleted(true);
        entity = typeRepository.saveAndFlush(entity);
        Optional<Type> optional = typeRepository.findByName(entity.getName());
        assertThat(optional).isNotNull();
        assertThat(optional).isNotPresent();
    }


}
