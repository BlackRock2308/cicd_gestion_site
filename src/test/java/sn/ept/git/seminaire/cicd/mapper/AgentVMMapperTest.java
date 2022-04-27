package sn.ept.git.seminaire.cicd.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.ept.git.seminaire.cicd.data.AgentDTOTestData;
import sn.ept.git.seminaire.cicd.dto.AgentDTO;
import sn.ept.git.seminaire.cicd.mappers.AgentMapper;
import sn.ept.git.seminaire.cicd.models.Agent;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration
@SpringBootTest
@RunWith(SpringRunner.class)

class AgentVMMapperTest extends  MapperBaseTest{

    @Autowired
    private AgentMapper mapper;
    AgentDTO dto;
    Agent entity;




    @BeforeEach
    void setUp() {
        dto = AgentDTOTestData.defaultDTO();
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
        assertThat(entity.getFirstName()).isEqualTo(dto.getFirstName());
        assertThat(entity.getEmail()).isEqualTo(dto.getEmail());
        assertThat(entity.getLastName()).isEqualTo(dto.getLastName());
        assertThat(entity.getAddress()).isEqualTo(dto.getAddress());
        assertThat(entity.getPhone()).isEqualTo(dto.getPhone());
    }

    @Test
    void toDTO() {
        entity = mapper.asEntity(dto);
        dto =mapper.asDTO(entity);
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getCreatedDate()).isEqualTo(entity.getCreatedDate());
        assertThat(dto.getLastModifiedDate()).isEqualTo(entity.getLastModifiedDate());
        assertThat(dto.getVersion()).isEqualTo(entity.getVersion());
        assertThat(dto.isDeleted()).isEqualTo(entity.isDeleted());
        assertThat(dto.isEnabled()).isEqualTo(entity.isEnabled());
        assertThat(dto.getFirstName()).isEqualTo(entity.getFirstName());
        assertThat(dto.getEmail()).isEqualTo(entity.getEmail());
        assertThat(dto.getLastName()).isEqualTo(entity.getLastName());
        assertThat(dto.getAddress()).isEqualTo(entity.getAddress());
        assertThat(dto.getPhone()).isEqualTo(entity.getPhone());
    }
}
