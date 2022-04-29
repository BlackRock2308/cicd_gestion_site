package sn.ept.git.seminaire.cicd.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import sn.ept.git.seminaire.cicd.data.ExerciceVMTestData;

import sn.ept.git.seminaire.cicd.dto.ExerciceDTO;
import sn.ept.git.seminaire.cicd.dto.vm.ExerciceVM;
import sn.ept.git.seminaire.cicd.exceptions.ItemNotFoundException;
import sn.ept.git.seminaire.cicd.mappers.ExerciceMapper;
import sn.ept.git.seminaire.cicd.mappers.vm.ExerciceVMMapper;
import sn.ept.git.seminaire.cicd.models.Exercice;
import sn.ept.git.seminaire.cicd.repositories.ExerciceRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Slf4j
@SpringBootTest
@Transactional()
class ExerciceServiceTest extends ServiceBaseTest {

    @Autowired
    protected ExerciceMapper mapper;
    @Autowired
    protected ExerciceVMMapper vmMapper;
    @Autowired
    ExerciceRepository exerciceRepository;
    @Autowired
    IExerciceService service;

    private static Exercice exercice;
    static  ExerciceVM vm ;
    ExerciceDTO dto;


    @BeforeAll
    static void beforeAll(){
        log.info(" before all");
        vm = ExerciceVMTestData.defaultVM();
        exercice =  ExerciceVMTestData.defaultEntity(exercice);
    }

    @BeforeEach
    void beforeEach(){
        log.info(" before each");
    }

    @AfterEach
    void afterEach(){
        service.deleteAll();
    }

    @Test
    @Rollback(value = false)
    @Order(1)
    void save_shouldSaveExercice() {
        exercice = ExerciceVMTestData.defaultEntity(exercice);
        vm = vmMapper.asDTO(exercice);
        exercice = exerciceRepository.save(exercice);
        assertThat(exercice)
                .isNotNull();
                //.hasNoNullFieldsOrProperties();
    }


    @Test
    void findById_shouldReturnResult() {
        //dto =service.save(vm);
        exercice = exerciceRepository.save(exercice);
        final Optional<ExerciceDTO> optional = service.findById(exercice.getId());
        assertThat(optional)
                .isNotNull()
                .isPresent()
                .get();
                //.hasNoNullFieldsOrProperties();
    }


    @Test
    void findById_withBadId_ShouldReturnNoResult() {
        final Optional<ExerciceDTO> optional = service.findById(UUID.randomUUID());
        assertThat(optional)
                .isNotNull()
                .isNotPresent();
    }


    @Test
    //@Order(2)
    void delete_shouldDeleteExercice() {
        //dto = exerciceRepository.save(vm);
        exercice = exerciceRepository.save(exercice);
        long oldCount = service.findAll().stream().count();
        service.delete(exercice.getId());
        long newCount = service.findAll().stream().count();
        assertThat(oldCount).isEqualTo(newCount+1);
    }



    @Test
    void delete_withBadId_ShouldThrowException() {
        assertThrows(
                ItemNotFoundException.class,
                () ->service.delete(UUID.randomUUID())
        );
    }

    @Test
    @Order(3)
    void findAll_shouldReturnResult(){
        exercice = ExerciceVMTestData.defaultEntity(exercice);
        exerciceRepository.save(exercice);
        List<Exercice> exercices = exerciceRepository.findAll();

        assertThat(exercices.stream().count()).isGreaterThan(0);
    }

    /*
    @Test
    void Update_shouldReturnResult(){
        vm = ExerciceVMTestData.defaultVM();
        dto = service.update(vm.getId(),vm);

        assertThat(service.findById(dto.getId())).isEqualTo(vm.getId());

    }



*/


}