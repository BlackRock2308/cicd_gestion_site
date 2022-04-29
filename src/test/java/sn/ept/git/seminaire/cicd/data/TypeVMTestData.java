package sn.ept.git.seminaire.cicd.data;

import sn.ept.git.seminaire.cicd.dto.vm.TypeVM;
import sn.ept.git.seminaire.cicd.models.Type;

public final class TypeVMTestData extends TestData {

    public static TypeVM defaultVM() {
        return TypeVM
                .builder()
                .id(Default.id)
                .createdDate(Default.createdDate)
                .lastModifiedDate(Default.lastModifiedDate)
                .version(Default.version)
                .deleted(Default.deleted)
                .enabled(Default.enabled)
                .description(Default.description)
                .name(Default.name)
                .build();
    }

    public static TypeVM updatedVM() {
        return TypeVM
                .builder()
                .id(Default.id)
                .createdDate(Update.createdDate)
                .lastModifiedDate(Update.lastModifiedDate)
                .version(Update.version)
                .deleted(Update.deleted)
                .enabled(Update.enabled)
                .description(Default.description)
                .name(Default.name)
                .build();
    }

    public static Type defaultEntity(Type entity) {
        return Type
                .builder()
                .id(Default.id)
                .createdDate(Update.createdDate)
                .lastModifiedDate(Update.lastModifiedDate)
                .version(Update.version)
                .deleted(Update.deleted)
                .enabled(Update.enabled)
                .description(Default.description)
                .name(Default.name)
                .build();
    }
}
