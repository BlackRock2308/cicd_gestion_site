package sn.ept.git.seminaire.cicd.data;

import sn.ept.git.seminaire.cicd.dto.AttachementDTO;
import sn.ept.git.seminaire.cicd.models.Attachement;

public final class AttachementDTOTestData extends TestData {

    public static AttachementDTO defaultDTO() {
        return AttachementDTO
                .builder()
                .id(Default.id)
                .createdDate(Default.createdDate)
                .lastModifiedDate(Default.lastModifiedDate)
                .version(Default.version)
                .deleted(Default.deleted)
                .enabled(Default.enabled)
                .name(Default.name)
                .description(Default.description)
                .location(Default.location)
                .hash(Default.hash)
                .build();
    }

    public static AttachementDTO updatedDTO() {
        return AttachementDTO
                .builder()
                .id(Default.id)
                .createdDate(Update.createdDate)
                .lastModifiedDate(Update.lastModifiedDate)
                .version(Update.version)
                .deleted(Update.deleted)
                .enabled(Update.enabled)
                .name(Default.name)
                .description(Default.description)
                .location(Default.location)
                .hash(Default.hash)
                .build();
    }

    public static Attachement defaultEntity(Attachement entity) {
        return Attachement
                .builder()
                .id(Default.id)
                .createdDate(Default.createdDate)
                .lastModifiedDate(Default.lastModifiedDate)
                .version(Default.version)
                .deleted(Default.deleted)
                .enabled(Default.enabled)
                .name(Default.name)
                .description(Default.description)
                .location(Default.location)
                .hash(Default.hash)
                .build();
    }
}
