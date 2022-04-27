package sn.ept.git.seminaire.cicd.data;

import sn.ept.git.seminaire.cicd.dto.vm.InterventionVM;

public final class InterventionVMTestData extends TestData {

    public static InterventionVM defaultVM() {
        return InterventionVM
                .builder()
                .id(Default.id)
                .createdDate(Default.createdDate)
                .lastModifiedDate(Default.lastModifiedDate)
                .version(Default.version)
                .deleted(Default.deleted)
                .enabled(Default.enabled)
                .start(Default.start)
                .end(Default.end)
                .commentIn(Default.commentIn)
                .commentOut(Default.commentOut)
                .status(Default.statusIntervention)
                .build();
    }

    public static InterventionVM updatedVM() {
        return InterventionVM
                .builder()
                .id(Default.id)
                .createdDate(Update.createdDate)
                .lastModifiedDate(Update.lastModifiedDate)
                .version(Update.version)
                .deleted(Update.deleted)
                .enabled(Update.enabled)
                .start(Default.start)
                .end(Default.end)
                .commentIn(Default.commentIn)
                .commentOut(Default.commentOut)
                .status(Default.statusIntervention)
                .build();
    }
}
