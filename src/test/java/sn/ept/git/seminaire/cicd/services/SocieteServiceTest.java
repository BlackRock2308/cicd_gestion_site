package sn.ept.git.seminaire.cicd.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import sn.ept.git.seminaire.cicd.data.ExerciceVMTestData;
import sn.ept.git.seminaire.cicd.data.SocieteDTOTestData;
import sn.ept.git.seminaire.cicd.data.SocieteVMTestData;
import sn.ept.git.seminaire.cicd.data.TestData;
import sn.ept.git.seminaire.cicd.dto.ExerciceDTO;
import sn.ept.git.seminaire.cicd.dto.SocieteDTO;
import sn.ept.git.seminaire.cicd.dto.vm.SocieteVM;
import sn.ept.git.seminaire.cicd.exceptions.ItemExistsException;
import sn.ept.git.seminaire.cicd.exceptions.ItemNotFoundException;
import sn.ept.git.seminaire.cicd.mappers.SocieteMapper;
import sn.ept.git.seminaire.cicd.mappers.vm.SocieteVMMapper;
import sn.ept.git.seminaire.cicd.models.Societe;
import sn.ept.git.seminaire.cicd.repositories.SocieteRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/*@SqlGroup({
        @Sql("classpath:0_societe_data_test.sql"),
        @Sql("classpath:1_societe_data_test.sql"),
        @Sql("classpath:2_societe_data_test.sql"),
})*/
@Slf4j
@SpringBootTest
@Transactional()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SocieteServiceTest extends ServiceBaseTest {

    @Autowired
    protected SocieteMapper mapper;
    @Autowired
    protected SocieteVMMapper vmMapper;
    @Autowired
    SocieteRepository societeRepository;
    @Autowired
    ISocieteService service;

   Societe societe;
    static SocieteVM vm ;
    SocieteDTO dto;


    @BeforeEach
    static void beforeAll(){
        log.info(" before all");
        vm = SocieteVMTestData.defaultVM();
<<<<<<< HEAD
        //societe = SocieteVMTestData.defaultEntity(societe);
=======
>>>>>>> 1b5393037d1a7f0652de6893ac7ea8ab81623d79
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
    void save_shouldSaveSociete() {
        dto = service.save(vm);
        assertThat(dto)
                .isNotNull();
                //.hasNoNullFieldsOrProperties();
    }


    @Test
    @Order(1)
    void save_withSameName_shouldThrowException() {
        societe = societeRepository.save(societe);
        societe.setEmail(TestData.Update.email);
        societe.setPhone(TestData.Update.phone);
        assertThrows(
                ItemExistsException.class,
                () -> service.save(vm)
        );
    }

    @Test
    @Order(2)
    void save_withSamePhone_shouldThrowException() {
        societe = societeRepository.save(societe);
        societe.setEmail(TestData.Update.email);
        societe.setAddress(TestData.Update.address);
        assertThrows(
                ItemExistsException.class,
                () -> service.save(vm)
        );
    }

    @Test
    @Order(3)
    void save_withSameEmail_shouldThrowException() {
        societe = societeRepository.save(societe);
        societe.setPhone(TestData.Update.phone);
        societe.setAddress(TestData.Update.address);
        assertThrows(
                ItemExistsException.class,
                () -> service.save(vm)
        );
    }

    @Test
    void findById_shouldReturnResult() {
        societe = societeRepository.save(societe);
        vm = vmMapper.asDTO(societe);
        final Optional<SocieteDTO> optional = service.findById(societe.getId());
        assertThat(optional)
                .isNotNull()
                .isPresent()
                .get();
                //.hasNoNullFieldsOrProperties();
    }

    @Test
    void findById_withBadId_ShouldReturnNoResult() {
        final Optional<SocieteDTO> optional = service.findById(UUID.randomUUID());
        assertThat(optional)
                .isNotNull()
                .isNotPresent();
    }

    @Test
    void delete_shouldDeleteSociete() {
        societe = societeRepository.save(societe);
        long oldCount = service.findAll().stream().count();
        service.delete(societe.getId());
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
    void findAll_shouldReturnResult(){
        vm = SocieteVMTestData.defaultVM();
        dto = service.save(vm);

        List<SocieteDTO>  societes = service.findAll();
        assertThat(societes.stream().count()).isGreaterThan(0);
    }


/*

    update
*/


}