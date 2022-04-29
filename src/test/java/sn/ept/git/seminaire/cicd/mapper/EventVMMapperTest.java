package sn.ept.git.seminaire.cicd.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.ept.git.seminaire.cicd.data.EventVMTestData;
import sn.ept.git.seminaire.cicd.dto.vm.EventVM;
import sn.ept.git.seminaire.cicd.mappers.vm.EventVMMapper;
import sn.ept.git.seminaire.cicd.models.Event;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration
@SpringBootTest
@RunWith(SpringRunner.class)

class EventvmMapperTest extends  MapperBaseTest{

    @Autowired
    private EventVMMapper mapper;
    EventVM vm;
    Event entity;




    @BeforeEach
    void setUp() {
        vm = EventVMTestData.defaultVM();
        entity = EventVMTestData.defaultEntity(entity);
    }


    @Test
    void toEntity() {
        vm = mapper.asDTO(entity);
        entity = mapper.asEntity(vm);
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(vm.getId());
        assertThat(entity.getCreatedDate()).isEqualTo(vm.getCreatedDate());
        assertThat(entity.getLastModifiedDate()).isEqualTo(vm.getLastModifiedDate());
        assertThat(entity.getVersion()).isEqualTo(vm.getVersion());
        assertThat(entity.isDeleted()).isEqualTo(vm.isDeleted());
        assertThat(entity.isEnabled()).isEqualTo(vm.isEnabled());
        assertThat(entity.getTitle()).isEqualTo(vm.getTitle());
        assertThat(entity.getDescription()).isEqualTo(vm.getDescription());
        assertThat(entity.getLongitude()).isEqualTo(vm.getLongitude());
        assertThat(entity.getLatitude()).isEqualTo(vm.getLatitude());
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
        assertThat(entity.getTitle()).isEqualTo(vm.getTitle());
        assertThat(entity.getDescription()).isEqualTo(vm.getDescription());
        assertThat(vm.getLongitude()).isEqualTo(entity.getLongitude());
        assertThat(vm.getLatitude()).isEqualTo(entity.getLatitude());
    }
}
