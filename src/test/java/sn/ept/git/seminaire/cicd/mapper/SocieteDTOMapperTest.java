package sn.ept.git.seminaire.cicd.mapper;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sn.ept.git.seminaire.cicd.data.SocieteDTOTestData;
import sn.ept.git.seminaire.cicd.dto.SocieteDTO;
import sn.ept.git.seminaire.cicd.mappers.SocieteMapper;
import sn.ept.git.seminaire.cicd.mappers.SocieteMapperImpl;
import sn.ept.git.seminaire.cicd.mappers.vm.SocieteVMMapperImpl;
import sn.ept.git.seminaire.cicd.models.Societe;

import static org.assertj.core.api.Assertions.assertThat;



@ExtendWith(SpringExtension.class) // JUnit 5
@ContextConfiguration(classes = {
        SocieteVMMapperImpl.class,
        SocieteMapperImpl.class
})
class SocieteVMMapperTest extends  MapperBaseTest{

    SocieteDTO dto;
    Societe entity;

    @Autowired
    private  SocieteMapper mapper;




    @BeforeEach
    void setUp() {
        dto = SocieteDTOTestData.defaultDTO();
    }


    @Test
    void toEntity() {
        entity = mapper.asEntity(dto);
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(dto.getId());
        assertThat(entity.getCreatedDate()).isEqualTo(dto.getCreatedDate());
        assertThat(entity.getLastModifiedDate()).isEqualTo(dto.getLastModifiedDate());
        assertThat(entity.getVersion()).isEqualTo(dto.getVersion());
        assertThat(entity.isDeleted()).isEqualTo(dto.isDeleted());
        assertThat(entity.isEnabled()).isEqualTo(dto.isEnabled());
        assertThat(entity.getName()).isEqualTo(dto.getName());
        assertThat(entity.getAddress()).isEqualTo(dto.getAddress());
        assertThat(entity.getEmail()).isEqualTo(dto.getEmail());
        assertThat(entity.getLongitude()).isEqualTo(dto.getLongitude());
        assertThat(entity.getLatitude()).isEqualTo(dto.getLatitude());
        assertThat(entity.getPhone()).isEqualTo(dto.getPhone());
    }

    @Test
    void toVM() {
        entity = mapper.asEntity(dto);
        dto =mapper.asDTO(entity);
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getCreatedDate()).isEqualTo(entity.getCreatedDate());
        assertThat(dto.getLastModifiedDate()).isEqualTo(entity.getLastModifiedDate());
        assertThat(dto.getVersion()).isEqualTo(entity.getVersion());
        assertThat(dto.isDeleted()).isEqualTo(entity.isDeleted());
        assertThat(dto.isEnabled()).isEqualTo(entity.isEnabled());
        assertThat(dto.getName()).isEqualTo(entity.getName());
        assertThat(dto.getAddress()).isEqualTo(entity.getAddress());
        assertThat(dto.getEmail()).isEqualTo(entity.getEmail());
        assertThat(dto.getLongitude()).isEqualTo(entity.getLongitude());
        assertThat(dto.getLatitude()).isEqualTo(entity.getLatitude());
        assertThat(dto.getPhone()).isEqualTo(entity.getPhone());
    }
}
