package sn.ept.git.seminaire.cicd.data;

import sn.ept.git.seminaire.cicd.dto.EventDTO;

public final class EventDTOTestData extends TestData {

    public static EventDTO defaultDTO() {
        return EventDTO
                .builder()
                .id(Default.id)
                .createdDate(Default.createdDate)
                .lastModifiedDate(Default.lastModifiedDate)
                .version(Default.version)
                .deleted(Default.deleted)
                .enabled(Default.enabled)
                .title(Default.title)
                .description(Default.description)
                .latitude(Default.latitude)
                .longitude(Default.longitude)
                .build();
    }

    public static EventDTO updatedDTO() {
        return EventDTO
                .builder()
                .id(Default.id)
                .createdDate(Update.createdDate)
                .lastModifiedDate(Update.lastModifiedDate)
                .version(Update.version)
                .deleted(Update.deleted)
                .enabled(Update.enabled)
                .title(Default.title)
                .description(Default.description)
                .latitude(Default.latitude)
                .longitude(Default.longitude)
                .build();
    }
}
