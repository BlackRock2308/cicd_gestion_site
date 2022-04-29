package sn.ept.git.seminaire.cicd.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.ept.git.seminaire.cicd.data.ExerciceDTOTestData;
import sn.ept.git.seminaire.cicd.dto.ExerciceDTO;
import sn.ept.git.seminaire.cicd.mappers.ExerciceMapper;
import sn.ept.git.seminaire.cicd.models.Exercice;
import sn.ept.git.seminaire.cicd.repositories.ExerciceRepository;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExerciceRepositoryTest extends RepositoryBaseTest {

    @Autowired
    @Valid
    ExerciceRepository exerciceRepository;
    @Autowired
    @Valid
    ExerciceMapper mapper;
    ExerciceDTO dto;

    Exercice entity;


    @BeforeEach
    void setUp() {
        dto = ExerciceDTOTestData.defaultDTO();
        //entity = mapper.asEntity(dto);
        entity = ExerciceDTOTestData.defaultEntityExercice(entity);
        exerciceRepository.deleteAll();
        entity = exerciceRepository.saveAndFlush(entity);
    }

    @Test
    void givenRepository_whenFindByDate_thenResult() {
        entity = ExerciceDTOTestData.defaultEntityExercice(entity);
        Optional<Exercice> optional = exerciceRepository.findByDates(entity.getStart());
        assertThat(optional).isNotNull();
        assertThat(optional).isPresent();
    }

    @Test
    void givenRepository_whenFindByBadDate_thenNotFound() {
        Optional<Exercice> optional = exerciceRepository.findByDates(entity.getStart());
        assertThat(optional).isNotNull();
        assertThat(optional).isNotPresent();
    }

}
