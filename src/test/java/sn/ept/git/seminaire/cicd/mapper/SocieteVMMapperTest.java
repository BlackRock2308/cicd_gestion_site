package sn.ept.git.seminaire.cicd.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sn.ept.git.seminaire.cicd.data.SocieteVMTestData;
import sn.ept.git.seminaire.cicd.dto.vm.SocieteVM;
import sn.ept.git.seminaire.cicd.mappers.vm.SocieteVMMapper;
import sn.ept.git.seminaire.cicd.models.Societe;

import static org.assertj.core.api.Assertions.assertThat;

class SocieteDTOMapperTest extends  MapperBaseTest{

    SocieteVM vm;
    Societe entity;

    @Autowired
    private SocieteVMMapper mapper;


    @BeforeEach
    void setUp() {
        vm = SocieteVMTestData.defaultVM();
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
        assertThat(entity.getAddress()).isEqualTo(vm.getAddress());
        assertThat(entity.getEmail()).isEqualTo(vm.getEmail());
        assertThat(entity.getLongitude()).isEqualTo(vm.getLongitude());
        assertThat(entity.getLatitude()).isEqualTo(vm.getLatitude());
        assertThat(entity.getPhone()).isEqualTo(vm.getPhone());
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
        assertThat(vm.getAddress()).isEqualTo(entity.getAddress());
        assertThat(vm.getEmail()).isEqualTo(entity.getEmail());
        assertThat(vm.getLongitude()).isEqualTo(entity.getLongitude());
        assertThat(vm.getLatitude()).isEqualTo(entity.getLatitude());
        assertThat(vm.getPhone()).isEqualTo(entity.getPhone());
    }
}
