package sn.ept.git.seminaire.cicd.data;
import sn.ept.git.seminaire.cicd.dto.SiteDTO;
import sn.ept.git.seminaire.cicd.models.Site;
import sn.ept.git.seminaire.cicd.models.Societe;


public final class SiteDTOTestData extends TestData{
    public static SiteDTO defaultDTO() {
        return SiteDTO
                .builder()
                .id(Default.id)
                .createdDate(Default.createdDate)
                .lastModifiedDate(Default.lastModifiedDate)
                .version(Default.version)
                .deleted(Default.deleted)
                .enabled(Default.enabled)
                .name(Default.name)
                .phone(Default.phone)
                .email(Default.email)
                .longitude(Default.longitude)
                .latitude(Default.latitude)
                .build();
    }

    public static SiteDTO updatedDTO() {
        return SiteDTO
                .builder()
                .id(Default.id)
                .createdDate(Update.createdDate)
                .lastModifiedDate(Update.lastModifiedDate)
                .version(Update.version)
                .deleted(Update.deleted)
                .enabled(Update.enabled)
                .name(Update.name)
                .phone(Update.phone)
                .email(Update.email)
                .longitude(Update.longitude)
                .latitude(Update.latitude)
                .build();
    }


    public static Site defaultEntity(Site site) {
        return Site
                .builder()
                .id(Default.id)
                .name(Default.name)
                .phone(Default.phone)
                .email(Default.email)
                .longitude(Default.longitude)
                .latitude(Default.latitude)
                .build();

    }
}
