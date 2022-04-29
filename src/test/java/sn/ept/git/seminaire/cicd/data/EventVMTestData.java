package sn.ept.git.seminaire.cicd.data;

import sn.ept.git.seminaire.cicd.dto.vm.EventVM;
import sn.ept.git.seminaire.cicd.models.Event;

public final class EventVMTestData extends TestData {

    public static EventVM defaultVM() {
        return EventVM
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

    public static EventVM updatedVM() {
        return EventVM
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

    public static Event defaultEntity(Event entity) {
        return Event
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
}
