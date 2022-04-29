package sn.ept.git.seminaire.cicd.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import sn.ept.git.seminaire.cicd.data.TestData;
import sn.ept.git.seminaire.cicd.data.TypeVMTestData;
import sn.ept.git.seminaire.cicd.dto.TypeDTO;
import sn.ept.git.seminaire.cicd.dto.vm.TypeVM;
import sn.ept.git.seminaire.cicd.exceptions.ItemExistsException;
import sn.ept.git.seminaire.cicd.exceptions.ItemNotFoundException;
import sn.ept.git.seminaire.cicd.mappers.TypeMapper;
import sn.ept.git.seminaire.cicd.mappers.vm.TypeVMMapper;
import sn.ept.git.seminaire.cicd.models.Type;
import sn.ept.git.seminaire.cicd.repositories.TypeRepository;

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
public class TypeServiceTest extends ServiceBaseTest {

    @Autowired
    protected TypeMapper mapper;
    @Autowired
    protected TypeVMMapper vmMapper;
    @Autowired
    TypeRepository typeRepository;
    @Autowired
    @Valid
    private ITypeService service;

    private static Type type;
    static TypeVM vm ;
    TypeDTO dto;


    @BeforeAll
    static void beforeAll(){
        log.info(" before all");
        vm = TypeVMTestData.defaultVM();
        type = TypeVMTestData.defaultEntity(type);
    }

    @BeforeEach
    void beforeEach(){
        log.info(" before each");
        service.save(vm);
    }

    @AfterEach
    void afterEach(){
        service.deleteAll();
    }

    @Test
    @Rollback(value = false)
    void save_shouldSaveTool() {
        dto = service.save(vm);
        assertThat(dto)
                .isNotNull();
        //.hasNoNullFieldsOrProperties();
    }


    @Test
    @Order(1)
    void save_withSameName_shouldThrowException() {
        type = typeRepository.save(type);
        type.setDescription(TestData.Update.description);
        assertThrows(
                ItemExistsException.class,
                () -> service.save(vm)
        );
    }



    @Test
    void findById_shouldReturnResult() {
        type = typeRepository.save(type); //save a tool
        vm = vmMapper.asDTO(type); // change the too to DTO
        final Optional<TypeDTO> optional = service.findById(type.getId());
        assertThat(optional)
                .isNotNull()
                .isPresent()
                .get();
        //.hasNoNullFieldsOrProperties();
    }

    @Test
    void findById_withBadId_ShouldReturnNoResult() {
        final Optional<TypeDTO> optional = service.findById(UUID.randomUUID());
        assertThat(optional)
                .isNotNull()
                .isNotPresent();
    }

    @Test
    void delete_shouldDeleteTool() {
        type = typeRepository.save(type); //save a tool
        long oldCount = service.findAll().stream().count();
        service.delete(type.getId());
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
        vm = TypeVMTestData.defaultVM();
        dto = service.save(vm);

        List<TypeDTO> tools = service.findAll();
        assertThat(tools.stream().count()).isGreaterThan(0);
    }


/*

    update
*/


}

