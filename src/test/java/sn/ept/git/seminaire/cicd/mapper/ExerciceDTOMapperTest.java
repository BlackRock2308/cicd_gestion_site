package sn.ept.git.seminaire.cicd.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.ept.git.seminaire.cicd.data.ExerciceVMTestData;
import sn.ept.git.seminaire.cicd.dto.vm.ExerciceVM;
import sn.ept.git.seminaire.cicd.mappers.vm.ExerciceVMMapper;
import sn.ept.git.seminaire.cicd.models.Exercice;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration
@SpringBootTest
@RunWith(SpringRunner.class)

class ExerciceDTOMapperTest extends  MapperBaseTest{

    @Autowired
    private ExerciceVMMapper mapper;
    ExerciceVM vm;
    Exercice entity;




    @BeforeEach
    void setUp() {
        vm = ExerciceVMTestData.defaultVM();
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
    }

    @Test
    void toDTO() {
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
    }
}
