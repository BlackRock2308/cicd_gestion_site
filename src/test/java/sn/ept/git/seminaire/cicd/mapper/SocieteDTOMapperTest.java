package sn.ept.git.seminaire.cicd.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sn.ept.git.seminaire.cicd.data.SocieteDTOTestData;
import sn.ept.git.seminaire.cicd.data.SocieteVMTestData;
import sn.ept.git.seminaire.cicd.dto.SocieteDTO;
import sn.ept.git.seminaire.cicd.dto.vm.SocieteVM;
import sn.ept.git.seminaire.cicd.mappers.SocieteMapper;
import sn.ept.git.seminaire.cicd.mappers.vm.SocieteVMMapper;
import sn.ept.git.seminaire.cicd.models.Societe;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class SocieteVMMapperTest extends  MapperBaseTest{


    SocieteVM vm;
    Societe entity;

    @Autowired
    private SocieteVMMapper mapper;


    @BeforeEach
    void setUp() {
        vm = SocieteVMTestData.defaultVM();
        entity = SocieteDTOTestData.defaultEntity(entity);
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
        assertThat(entity.getName()).isEqualTo(vm.getName());
        assertThat(entity.getAddress()).isEqualTo(vm.getAddress());
        assertThat(entity.getEmail()).isEqualTo(vm.getEmail());
        assertThat(entity.getLongitude()).isEqualTo(vm.getLongitude());
        assertThat(entity.getLatitude()).isEqualTo(vm.getLatitude());
        assertThat(entity.getPhone()).isEqualTo(vm.getPhone());
    }

    @Test
    void toVM() {
        entity = mapper.asEntity(vm);
        vm =mapper.asDTO(entity);
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(entity.getId());
        assertThat(entity.getCreatedDate()).isEqualTo(entity.getCreatedDate());
        assertThat(entity.getLastModifiedDate()).isEqualTo(entity.getLastModifiedDate());
        assertThat(entity.getVersion()).isEqualTo(entity.getVersion());
        assertThat(entity.isDeleted()).isEqualTo(entity.isDeleted());
        assertThat(entity.isEnabled()).isEqualTo(entity.isEnabled());
        assertThat(entity.getName()).isEqualTo(entity.getName());
        assertThat(entity.getAddress()).isEqualTo(entity.getAddress());
        assertThat(entity.getEmail()).isEqualTo(entity.getEmail());
        assertThat(entity.getLongitude()).isEqualTo(entity.getLongitude());
        assertThat(entity.getLatitude()).isEqualTo(entity.getLatitude());
        assertThat(entity.getPhone()).isEqualTo(entity.getPhone());
    }
}
