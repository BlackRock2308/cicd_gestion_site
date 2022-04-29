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

class InterventionVMMapperTest extends  MapperBaseTest{

    @Autowired
    private InterventionMapper mapper;

    InterventionDTO dto;
    Intervention entity;



    @BeforeEach
    void setUp() {
        dto = InterventionDTOTestData.defaultDTO();
        entity = InterventionDTOTestData.defaultEntity(entity);
    }


    @Test
    void toEntity() {
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(dto.getId());
        assertThat(entity.getCreatedDate()).isEqualTo(dto.getCreatedDate());
        assertThat(entity.getLastModifiedDate()).isEqualTo(dto.getLastModifiedDate());
        assertThat(entity.getVersion()).isEqualTo(dto.getVersion());
        assertThat(entity.isDeleted()).isEqualTo(dto.isDeleted());
        assertThat(entity.isEnabled()).isEqualTo(dto.isEnabled());
        assertThat(entity.getStatus()).isEqualTo(dto.getStatus());
        assertThat(entity.getStart()).isEqualTo(dto.getStart());
        assertThat(entity.getEnd()).isEqualTo(dto.getEnd());
        assertThat(entity.getCommentIn()).isEqualTo(dto.getCommentIn());
        assertThat(entity.getCommentOut()).isEqualTo(dto.getCommentOut());
    }

    @Test
    void toVM() {
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getCreatedDate()).isEqualTo(entity.getCreatedDate());
        assertThat(dto.getLastModifiedDate()).isEqualTo(entity.getLastModifiedDate());
        assertThat(dto.getVersion()).isEqualTo(entity.getVersion());
        assertThat(dto.isDeleted()).isEqualTo(entity.isDeleted());
        assertThat(dto.isEnabled()).isEqualTo(entity.isEnabled());
        assertThat(dto.getStatus()).isEqualTo(entity.getStatus());
        assertThat(dto.getStart()).isEqualTo(entity.getStart());
        assertThat(dto.getEnd()).isEqualTo(entity.getEnd());
        assertThat(dto.getCommentIn()).isEqualTo(entity.getCommentIn());
        assertThat(dto.getCommentOut()).isEqualTo(entity.getCommentOut());

    }
}
