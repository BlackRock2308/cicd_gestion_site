package sn.ept.git.seminaire.cicd.repository;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.ept.git.seminaire.cicd.data.ExerciceDTOTestData;
import sn.ept.git.seminaire.cicd.data.ExerciceVMTestData;
import sn.ept.git.seminaire.cicd.data.SocieteVMTestData;
import sn.ept.git.seminaire.cicd.dto.ExerciceDTO;
import sn.ept.git.seminaire.cicd.dto.SocieteDTO;
import sn.ept.git.seminaire.cicd.dto.vm.ExerciceVM;
import sn.ept.git.seminaire.cicd.exceptions.ItemNotFoundException;
import sn.ept.git.seminaire.cicd.mappers.ExerciceMapper;
import sn.ept.git.seminaire.cicd.mappers.vm.ExerciceVMMapper;
import sn.ept.git.seminaire.cicd.models.Exercice;
import sn.ept.git.seminaire.cicd.models.Site;
import sn.ept.git.seminaire.cicd.repositories.ExerciceRepository;
import sn.ept.git.seminaire.cicd.services.IExerciceService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


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

    @Autowired
    @Valid
    ExerciceVMMapper vmMapper;
    ExerciceDTO dto;
    ExerciceVM vm;
    @Autowired
    IExerciceService service;

    Exercice exercice;


    @BeforeEach
    void setUp() {
        dto = ExerciceDTOTestData.defaultDTO();
        //entity = mapper.asEntity(dto);
        exercice = ExerciceDTOTestData.defaultEntityExercice(exercice);
        exerciceRepository.deleteAll();
        exercice = exerciceRepository.saveAndFlush(exercice);
    }

    /**
    @Test
    void givenRepository_whenFindByDate_thenResult() {
        exercice = ExerciceDTOTestData.defaultEntityExercice(exercice);
        Optional<Exercice> optional = exerciceRepository.findByDates(exercice.getStart());
        assertThat(optional).isNotNull();
        assertThat(optional).isPresent();
    }

    @Test
    void givenRepository_whenFindByBadDate_thenNotFound() {
        exercice = ExerciceVMTestData.defaultEntity(exercice);
        vm = vmMapper.asDTO(exercice);
        Optional<Exercice> optional = exerciceRepository.findByDates(vm.getStart());
        //assertThat(optional).isNotNull();
        assertThat(optional).isNotPresent();
    }
**/
    @Test
    void findAll_shouldReturnResult(){
        exercice = ExerciceVMTestData.defaultEntity(exercice);
        vm = vmMapper.asDTO(exercice);
        exercice = exerciceRepository.save(exercice);
        List<ExerciceDTO> societes = service.findAll();
        assertThat(societes.stream().count()).isGreaterThan(0);
    }

    @Test
    void delete_withBadId_ShouldThrowException() {
        assertThrows(
                ItemNotFoundException.class,
                () ->service.delete(UUID.randomUUID())
        );
    }


}
