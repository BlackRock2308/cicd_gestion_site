package sn.ept.git.seminaire.cicd.data;

import sn.ept.git.seminaire.cicd.dto.vm.AttachementVM;

public final class AttachementVMTestData extends TestData {

    public static AttachementVM defaultVM() {
        return AttachementVM
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

    public static AttachementVM updatedVM() {
        return AttachementVM
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
}
