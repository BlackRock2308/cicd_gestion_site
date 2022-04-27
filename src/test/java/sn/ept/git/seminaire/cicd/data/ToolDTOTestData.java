package sn.ept.git.seminaire.cicd.data;

import sn.ept.git.seminaire.cicd.dto.ToolDTO;

public final class ToolDTOTestData extends TestData {

    public static ToolDTO defaultDTO() {
        return ToolDTO
                .builder()
                .id(Default.id)
                .createdDate(Default.createdDate)
                .lastModifiedDate(Default.lastModifiedDate)
                .version(Default.version)
                .deleted(Default.deleted)
                .enabled(Default.enabled)
                .name(Default.name)
                .description(Default.description)
                .build();
    }

    public static ToolDTO updatedDTO() {
        return ToolDTO
                .builder()
                .id(Default.id)
                .createdDate(Update.createdDate)
                .lastModifiedDate(Update.lastModifiedDate)
                .version(Update.version)
                .deleted(Update.deleted)
                .enabled(Update.enabled)
                .name(Update.name)
                .description(Update.description)
                .build();
    }
}
