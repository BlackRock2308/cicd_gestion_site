package sn.ept.git.seminaire.cicd.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.ept.git.seminaire.cicd.data.TypeDTOTestData;
import sn.ept.git.seminaire.cicd.dto.TypeDTO;
import sn.ept.git.seminaire.cicd.mappers.TypeMapper;
import sn.ept.git.seminaire.cicd.models.Type;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration
@SpringBootTest
@RunWith(SpringRunner.class)

class TypeDTOMapperTest extends  MapperBaseTest {

    @Autowired
    private TypeMapper mapper;
    TypeDTO dto;
    Type entity;


    @BeforeEach
    void setUp() {
        dto = TypeDTOTestData.defaultDTO();
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
        assertThat(entity.getDescription()).isEqualTo(dto.getDescription());

    }

    @Test
    void toDTO() {
        entity = mapper.asEntity(dto);
        dto = mapper.asDTO(entity);
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getCreatedDate()).isEqualTo(entity.getCreatedDate());
        assertThat(dto.getLastModifiedDate()).isEqualTo(entity.getLastModifiedDate());
        assertThat(dto.getVersion()).isEqualTo(entity.getVersion());
        assertThat(dto.isDeleted()).isEqualTo(entity.isDeleted());
        assertThat(dto.isEnabled()).isEqualTo(entity.isEnabled());
        assertThat(dto.getName()).isEqualTo(entity.getName());
        assertThat(dto.getDescription()).isEqualTo(entity.getDescription());
    }
}
