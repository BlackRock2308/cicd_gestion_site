package sn.ept.git.seminaire.cicd.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.ept.git.seminaire.cicd.data.InterventionDTOTestData;
import sn.ept.git.seminaire.cicd.data.InterventionVMTestData;
import sn.ept.git.seminaire.cicd.dto.InterventionDTO;
import sn.ept.git.seminaire.cicd.dto.vm.InterventionVM;
import sn.ept.git.seminaire.cicd.mappers.InterventionMapper;
import sn.ept.git.seminaire.cicd.mappers.vm.InterventionVMMapper;
import sn.ept.git.seminaire.cicd.models.Intervention;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration
@SpringBootTest
@RunWith(SpringRunner.class)

class InterventionDTOMapperTest extends  MapperBaseTest{

    @Autowired
    private InterventionVMMapper mapper;  //change InterventionMapper to InterventionVMMapper
    InterventionVM vm; //Use InterventionVM instead of InterventionDTO
    Intervention entity;



    @BeforeEach
    void setUp() {
        vm = InterventionVMTestData.defaultVM();
        entity = InterventionVMTestData.defaultEntity(entity);
    }

    @Test
    void toEntity() {

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(vm.getId());
        assertThat(entity.getCreatedDate()).isEqualTo(vm.getCreatedDate());
        assertThat(entity.getLastModifiedDate()).isEqualTo(vm.getLastModifiedDate());
        assertThat(entity.getVersion()).isEqualTo(vm.getVersion());
        assertThat(entity.isDeleted()).isEqualTo(vm.isDeleted());
        assertThat(entity.isEnabled()).isEqualTo(vm.isEnabled());
        assertThat(entity.getStatus()).isEqualTo(vm.getStatus());
        assertThat(entity.getStart()).isEqualTo(vm.getStart());
        assertThat(entity.getEnd()).isEqualTo(vm.getEnd());
        assertThat(entity.getCommentIn()).isEqualTo(vm.getCommentIn());
        assertThat(entity.getCommentOut()).isEqualTo(vm.getCommentOut());
    }

    @Test
    void toVM() {
        assertThat(vm).isNotNull();
        assertThat(vm.getId()).isEqualTo(entity.getId());
        assertThat(vm.getCreatedDate()).isEqualTo(entity.getCreatedDate());
        assertThat(vm.getLastModifiedDate()).isEqualTo(entity.getLastModifiedDate());
        assertThat(vm.getVersion()).isEqualTo(entity.getVersion());
        assertThat(vm.isDeleted()).isEqualTo(entity.isDeleted());
        assertThat(vm.isEnabled()).isEqualTo(entity.isEnabled());
        assertThat(vm.getStatus()).isEqualTo(entity.getStatus());
        assertThat(vm.getStart()).isEqualTo(entity.getStart());
        assertThat(vm.getEnd()).isEqualTo(entity.getEnd());
        assertThat(vm.getCommentIn()).isEqualTo(entity.getCommentIn());
        assertThat(vm.getCommentOut()).isEqualTo(entity.getCommentOut());
    }
}
