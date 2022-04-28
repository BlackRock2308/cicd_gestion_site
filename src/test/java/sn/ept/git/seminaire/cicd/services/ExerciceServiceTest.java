package sn.ept.git.seminaire.cicd.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import sn.ept.git.seminaire.cicd.data.ExerciceVMTestData;
import sn.ept.git.seminaire.cicd.data.TestData;
import sn.ept.git.seminaire.cicd.dto.ExerciceDTO;
import sn.ept.git.seminaire.cicd.dto.vm.ExerciceVM;
import sn.ept.git.seminaire.cicd.exceptions.ItemExistsException;
import sn.ept.git.seminaire.cicd.exceptions.ItemNotFoundException;
import sn.ept.git.seminaire.cicd.mappers.ExerciceMapper;
import sn.ept.git.seminaire.cicd.mappers.vm.ExerciceVMMapper;
import sn.ept.git.seminaire.cicd.models.Exercice;
import sn.ept.git.seminaire.cicd.repositories.ExerciceRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/*@SqlGroup({
        @Sql("classpath:0_Exercice_data_test.sql"),
        @Sql("classpath:1_Exercice_data_test.sql"),
        @Sql("classpath:2_Exercice_data_test.sql"),
})*/
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
    Exercice dto;


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

    @Test
    @Rollback(value = false)
    void save_shouldSaveExercice() {
        dto = exerciceRepository.save(exercice);
        assertThat(dto)
                .isNotNull();
                //.hasNoNullFieldsOrProperties();
    }


    @Test
    void findById_shouldReturnResult() {
        //dto =service.save(vm);
        dto = exerciceRepository.save(exercice);
        final Optional<Exercice> optional = exerciceRepository.findById(dto.getId());
        assertThat(optional)
                .isNotNull()
                .isPresent()
                .get();
                //.hasNoNullFieldsOrProperties();
    }


    @Test
    void findById_withBadId_ShouldReturnNoResult() {
        final Optional<Exercice> optional = exerciceRepository.findById(UUID.randomUUID());
        assertThat(optional)
                .isNotNull()
                .isNotPresent();
    }


    @Test
    void delete_shouldDeleteExercice() {
        //dto = exerciceRepository.save(vm);
        dto = exerciceRepository.save(exercice);
        long oldCount = exerciceRepository.count();
        service.delete(dto.getId());
        long newCount = exerciceRepository.count();
        assertThat(oldCount).isEqualTo(newCount+1);
    }



    @Test
    void delete_withBadId_ShouldThrowException() {
        assertThrows(
                ItemNotFoundException.class,
                () ->service.delete(UUID.randomUUID())
        );
    }

/*
    findAll
    update
*/


}