package sn.ept.git.seminaire.cicd.data;

import org.apache.commons.lang.math.RandomUtils;
import sn.ept.git.seminaire.cicd.dto.vm.ExerciceVM;
import sn.ept.git.seminaire.cicd.enums.StatusExercice;
import sn.ept.git.seminaire.cicd.models.Exercice;
import sn.ept.git.seminaire.cicd.models.Intervention;

import java.time.Instant;
import java.util.Set;

public final class ExerciceVMTestData extends TestData {

    public static ExerciceVM defaultVM() {
        return ExerciceVM
                .builder()
                .id(Default.id)
                .createdDate(Default.createdDate)
                .lastModifiedDate(Default.lastModifiedDate)
                .version(Default.version)
                .deleted(Default.deleted)
                .enabled(Default.enabled)
                .start(Default.start)
                .end(Default.end)
                .status(Default.status)


                .build();
    }

    public static ExerciceVM updatedVM() {
        return ExerciceVM
                .builder()
                .id(Default.id)
                .createdDate(Update.createdDate)
                .lastModifiedDate(Update.lastModifiedDate)
                .version(Update.version)
                .deleted(Update.deleted)
                .enabled(Update.enabled)
                .start(Default.start)
                .end(Default.end)
                .status(Default.status)
                .build();
    }

    public static Exercice defaultEntity(Exercice entity) {
        return Exercice
                .builder()
                .id(Default.id)
                .name(Default.name)
                .createdDate(Default.createdDate)
                .lastModifiedDate(Default.lastModifiedDate)
                .version(Default.version)
                .deleted(Default.deleted)
                .enabled(Default.enabled)
                .start(Default.start)
                .end(Default.end)
                .name(Default.name)
                .societe(Default.societe)
                .status(Default.status)
                .build();
    }
}
