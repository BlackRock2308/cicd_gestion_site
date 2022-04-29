package sn.ept.git.seminaire.cicd.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import sn.ept.git.seminaire.cicd.data.ExerciceVMTestData;
import sn.ept.git.seminaire.cicd.data.SiteVMTestData;
import sn.ept.git.seminaire.cicd.data.TestData;
import sn.ept.git.seminaire.cicd.dto.ExerciceDTO;
import sn.ept.git.seminaire.cicd.dto.SiteDTO;
import sn.ept.git.seminaire.cicd.dto.vm.SiteVM;
import sn.ept.git.seminaire.cicd.exceptions.ItemExistsException;
import sn.ept.git.seminaire.cicd.exceptions.ItemNotFoundException;
import sn.ept.git.seminaire.cicd.mappers.SiteMapper;
import sn.ept.git.seminaire.cicd.mappers.vm.SiteVMMapper;
import sn.ept.git.seminaire.cicd.models.Site;
import sn.ept.git.seminaire.cicd.models.Societe;
import sn.ept.git.seminaire.cicd.repositories.SiteRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/*@SqlGroup({
        @Sql("classpath:0_site_data_test.sql"),
        @Sql("classpath:1_site_data_test.sql"),
        @Sql("classpath:2_site_data_test.sql"),
})*/
@Slf4j
@SpringBootTest
@Transactional()
class SiteServiceTest extends ServiceBaseTest {

    @Autowired
    protected SiteMapper mapper;
    @Autowired
    protected SiteVMMapper vmMapper;
    @Autowired
    SiteRepository siteRepository;
    @Autowired
    ISiteService service;

    private static  Site site ;
    static  SiteVM vm ;
    SiteDTO dto;


    @BeforeAll
    static void beforeAll(){
        log.info(" before all");
        vm = SiteVMTestData.defaultVM();
        site =  SiteVMTestData.defaultEntity(site);
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
    void save_shouldSaveSite() {
        //dto =service.save(vm);
        site = SiteVMTestData.defaultEntity(site);
        site = siteRepository.save(site);
        dto = mapper.asDTO(site);
        assertThat(dto)
                .isNotNull();
                //.hasNoNullFieldsOrProperties();
    }

    @Test
    void save_withSameName_shouldThrowException() {
        site = SiteVMTestData.defaultEntity(site);
        site = siteRepository.save(site);
        site.setEmail(TestData.Update.email);
        site.setPhone(TestData.Update.phone);
        assertThrows(
                ItemExistsException.class,
                () -> siteRepository.save(site)
        );
    }

    @Test
    void save_withSamePhone_shouldThrowException() {
        site = siteRepository.save(site);
        site.setEmail(TestData.Update.email);
        site.setName(TestData.Update.name);
        assertThrows(
                ItemExistsException.class,
                () -> service.save(vm)
        );
    }

    @Test
    @Order(1)
    void save_withSameEmail_shouldThrowException() {
        site = siteRepository.save(site);
        site.setPhone(TestData.Update.phone);
        site.setName(TestData.Update.name);
        assertThrows(
                ItemExistsException.class,
                () -> service.save(vm)
        );
    }

    @Test
    void findById_shouldReturnResult() {
        site = siteRepository.save(site);
        vm = vmMapper.asDTO(site);
        final Optional<SiteDTO> optional = service.findById(site.getId());
        assertThat(optional)
                .isNotNull()
                .isPresent()
                .get();
                //.hasNoNullFieldsOrProperties();
    }

    @Test
    void findById_withBadId_ShouldReturnNoResult() {
        final Optional<SiteDTO> optional = service.findById(UUID.randomUUID());
        assertThat(optional)
                .isNotNull()
                .isNotPresent();
    }

    @Test
    @Rollback(value = false)
    void delete_shouldDeleteSite() {
        site = siteRepository.save(site);
        long oldCount = siteRepository.count();
        service.delete(site.getId());
        long newCount = siteRepository.count();
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
        site = SiteVMTestData.defaultEntity(site);
        site = siteRepository.save(site);

        List<SiteDTO> sites = service.findAll();
        assertThat(sites.stream().count()).isGreaterThan(0);
    }


/*

    update
*/


}