package sn.ept.git.seminaire.cicd.data;

import sn.ept.git.seminaire.cicd.dto.ExerciceDTO;
import sn.ept.git.seminaire.cicd.models.Exercice;

public final class ExerciceDTOTestData extends TestData {

    public static ExerciceDTO defaultDTO() {
        return ExerciceDTO
                .builder()
                .id(Default.id)
                .createdDate(Default.createdDate)
                .lastModifiedDate(Default.lastModifiedDate)
                .version(Default.version)
                .deleted(Default.deleted)
                .enabled(Default.enabled)
                .name(Default.name)
                .build();
    }

    public static ExerciceDTO updatedDTO() {
        return ExerciceDTO
                .builder()
                .id(Default.id)
                .createdDate(Update.createdDate)
                .lastModifiedDate(Update.lastModifiedDate)
                .version(Update.version)
                .deleted(Update.deleted)
                .enabled(Update.enabled)
                .name(Update.name)
                .build();
    }

    public static Exercice defaultEntity(ExerciceDTO dto) {
        {
            return Exercice
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
    }
}
