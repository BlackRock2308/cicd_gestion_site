package sn.ept.git.seminaire.cicd.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import sn.ept.git.seminaire.cicd.data.InterventionVMTestData;
import sn.ept.git.seminaire.cicd.dto.InterventionDTO;
import sn.ept.git.seminaire.cicd.dto.vm.InterventionVM;
import sn.ept.git.seminaire.cicd.exceptions.ItemNotFoundException;
import sn.ept.git.seminaire.cicd.mappers.InterventionMapper;
import sn.ept.git.seminaire.cicd.mappers.vm.InterventionVMMapper;
import sn.ept.git.seminaire.cicd.models.Intervention;
import sn.ept.git.seminaire.cicd.repositories.InterventionRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
@Transactional()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InterventionServiceTest extends ServiceBaseTest {

    @Autowired
    protected InterventionMapper mapper;
    @Autowired
    protected InterventionVMMapper vmMapper;
    @Autowired
    InterventionRepository interventionRepository;
    @Autowired
    @Valid
    private IInterventionService service;

    private static Intervention intervention;
    static InterventionVM vm ;
    InterventionDTO dto;


    @BeforeAll
    static void beforeAll(){
        log.info(" before all");
        vm = InterventionVMTestData.defaultVM();
        intervention = InterventionVMTestData.defaultEntity(intervention);
    }

    @BeforeEach
    void beforeEach(){
        log.info(" before each");
        //service.save(vm);
    }

    @AfterEach
    void afterEach(){
        service.deleteAll();
    }

    @Test
    @Rollback(value = false)
    void save_shouldSaveIntervention() {
        intervention = InterventionVMTestData.defaultEntity(intervention);
        vm = vmMapper.asDTO(intervention);
        assertThat(vm)
                .isNotNull();
        //.hasNoNullFieldsOrProperties();
    }

/**
    @Test
    @Order(1)
    void save_withSameName_shouldThrowException() {
        intervention = InterventionVMTestData.defaultEntity(intervention);
        intervention = interventionRepository.save(intervention);
        vm.setIdExercice(TestData.Update.id);
        assertThrows(
                ItemExistsException.class,
                () -> service.save(vm)
        );
    }

**/

    @Test
    void findById_shouldReturnResult() {
        intervention = InterventionVMTestData.defaultEntity(intervention);
        intervention = interventionRepository.save(intervention); //save a tool// change the too to DTO
        final Optional<InterventionDTO> optional = service.findById(intervention.getId());
        assertThat(optional)
                .isNotNull()
                .isPresent()
                .get();
        //.hasNoNullFieldsOrProperties();
    }

    @Test
    void findById_withBadId_ShouldReturnNoResult() {
        intervention = InterventionVMTestData.defaultEntity(intervention);
        intervention = interventionRepository.save(intervention); //save a tool
        final Optional<InterventionDTO> optional = service.findById(UUID.randomUUID());
        assertThat(optional)
                .isNotNull()
                .isNotPresent();
    }

    @Test
    void delete_shouldDeleteTool() {
        intervention = InterventionVMTestData.defaultEntity(intervention);
        intervention = interventionRepository.save(intervention); //save a tool
        long oldCount = service.findAll().stream().count();
        service.delete(intervention.getId());
        long newCount = service.findAll().stream().count();
        assertThat(oldCount).isEqualTo(newCount+1);
    }

    @Test
    void delete_withBadId_ShouldThrowException() {
        intervention = InterventionVMTestData.defaultEntity(intervention);
        assertThrows(
                ItemNotFoundException.class,
                () ->service.delete(UUID.randomUUID())
        );
    }

    @Test
    void findAll_shouldReturnResult(){
        intervention = InterventionVMTestData.defaultEntity(intervention);
        intervention = interventionRepository.save(intervention);

        List<InterventionDTO> tools = service.findAll();
        assertThat(tools.stream().count()).isGreaterThan(0);
    }


/*

    update
*/


}

