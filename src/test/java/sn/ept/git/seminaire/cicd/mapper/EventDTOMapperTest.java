package sn.ept.git.seminaire.cicd.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sn.ept.git.seminaire.cicd.data.EventDTOTestData;
import sn.ept.git.seminaire.cicd.dto.EventDTO;
import sn.ept.git.seminaire.cicd.mappers.EventMapper;
import sn.ept.git.seminaire.cicd.models.Event;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration
@SpringBootTest
@RunWith(SpringRunner.class)

class EventDTOMapperTest extends  MapperBaseTest{

    @Autowired
    private EventMapper mapper;
    EventDTO dto;
    Event entity;




    @BeforeEach
    void setUp() {
        dto = EventDTOTestData.defaultDTO();
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
        assertThat(entity.getTitle()).isEqualTo(dto.getTitle());
        assertThat(entity.getDescription()).isEqualTo(dto.getDescription());
        assertThat(entity.getLongitude()).isEqualTo(dto.getLongitude());
        assertThat(entity.getLatitude()).isEqualTo(dto.getLatitude());
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
        assertThat(dto.getTitle()).isEqualTo(entity.getTitle());
        assertThat(dto.getDescription()).isEqualTo(entity.getDescription());
        assertThat(dto.getLongitude()).isEqualTo(entity.getLongitude());
        assertThat(dto.getLatitude()).isEqualTo(entity.getLatitude());
    }
}
