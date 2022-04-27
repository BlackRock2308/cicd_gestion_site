package sn.ept.git.seminaire.cicd.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.ept.git.seminaire.cicd.data.AttachementVMTestData;
import sn.ept.git.seminaire.cicd.dto.vm.AttachementVM;
import sn.ept.git.seminaire.cicd.mappers.vm.AttachementVMMapper;
import sn.ept.git.seminaire.cicd.mappers.AttachementMapper;
import sn.ept.git.seminaire.cicd.models.Attachement;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration
@SpringBootTest
@RunWith(SpringRunner.class)

class AttachementVMMapperTest extends  MapperBaseTest{

    @Autowired
    private AttachementVMMapper mapper;
    AttachementVM vm;
    Attachement entity;




    @BeforeEach
    void setUp() {
        vm = AttachementVMTestData.defaultVM();
    }


    @Test
    void toEntity() {
        entity = mapper.asEntity(vm);
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(vm.getId());
        assertThat(entity.getCreatedDate()).isEqualTo(vm.getCreatedDate());
        assertThat(entity.getLastModifiedDate()).isEqualTo(vm.getLastModifiedDate());
        assertThat(entity.getVersion()).isEqualTo(vm.getVersion());
        assertThat(entity.isDeleted()).isEqualTo(vm.isDeleted());
        assertThat(entity.isEnabled()).isEqualTo(vm.isEnabled());
        assertThat(entity.getName()).isEqualTo(vm.getName());
        assertThat(entity.getDescription()).isEqualTo(vm.getDescription());
        assertThat(entity.getLocation()).isEqualTo(vm.getLocation());
        assertThat(entity.getHash()).isEqualTo(vm.getHash());

    }

    @Test
    void toVM() {
        entity = mapper.asEntity(vm);
        vm =mapper.asDTO(entity);
        assertThat(vm).isNotNull();
        assertThat(vm.getId()).isEqualTo(entity.getId());
        assertThat(vm.getCreatedDate()).isEqualTo(entity.getCreatedDate());
        assertThat(vm.getLastModifiedDate()).isEqualTo(entity.getLastModifiedDate());
        assertThat(vm.getVersion()).isEqualTo(entity.getVersion());
        assertThat(vm.isDeleted()).isEqualTo(entity.isDeleted());
        assertThat(vm.isEnabled()).isEqualTo(entity.isEnabled());
        assertThat(vm.getName()).isEqualTo(entity.getName());
        assertThat(vm.getDescription()).isEqualTo(entity.getDescription());
        assertThat(vm.getLocation()).isEqualTo(entity.getLocation());
        assertThat(vm.getHash()).isEqualTo(entity.getHash());
    }
}
