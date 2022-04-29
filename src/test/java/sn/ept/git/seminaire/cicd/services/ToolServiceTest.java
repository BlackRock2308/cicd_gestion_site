package sn.ept.git.seminaire.cicd.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import sn.ept.git.seminaire.cicd.data.TestData;
import sn.ept.git.seminaire.cicd.data.ToolVMTestData;
import sn.ept.git.seminaire.cicd.dto.ToolDTO;
import sn.ept.git.seminaire.cicd.dto.vm.ToolVM;
import sn.ept.git.seminaire.cicd.exceptions.ItemExistsException;
import sn.ept.git.seminaire.cicd.exceptions.ItemNotFoundException;
import sn.ept.git.seminaire.cicd.mappers.SocieteMapper;
import sn.ept.git.seminaire.cicd.mappers.vm.ToolVMMapper;
import sn.ept.git.seminaire.cicd.models.Tool;
import sn.ept.git.seminaire.cicd.repositories.ToolRepository;

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
public class ToolServiceTest extends ServiceBaseTest {

    @Autowired
    protected SocieteMapper mapper;
    @Autowired
    protected ToolVMMapper vmMapper;
    @Autowired
    ToolRepository toolRepository;
    @Autowired
    @Valid
    private IToolService service;

    private static Tool tool;
    static ToolVM vm ;
    ToolDTO dto;


    @BeforeAll
    static void beforeAll(){
        log.info(" before all");
        vm = ToolVMTestData.defaultVM();
        tool = ToolVMTestData.defaultEntity(tool);
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
        tool = toolRepository.save(tool);
        tool.setDescription(TestData.Update.description);
        assertThrows(
                ItemExistsException.class,
                () -> service.save(vm)
        );
    }



    @Test
    void findById_shouldReturnResult() {
        tool = toolRepository.save(tool); //save a tool
        vm = vmMapper.asDTO(tool); // change the too to DTO
        final Optional<ToolDTO> optional = service.findById(tool.getId());
        assertThat(optional)
                .isNotNull()
                .isPresent()
                .get();
        //.hasNoNullFieldsOrProperties();
    }

    @Test
    void findById_withBadId_ShouldReturnNoResult() {
        final Optional<ToolDTO> optional = service.findById(UUID.randomUUID());
        assertThat(optional)
                .isNotNull()
                .isNotPresent();
    }

    @Test
    void delete_shouldDeleteTool() {
        tool = toolRepository.save(tool); //save a tool
        long oldCount = service.findAll().stream().count();
        service.delete(tool.getId());
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
        vm = ToolVMTestData.defaultVM();
        dto = service.save(vm);

        List<ToolDTO> tools = service.findAll();
        assertThat(tools.stream().count()).isGreaterThan(0);
    }


/*

    update
*/


}
