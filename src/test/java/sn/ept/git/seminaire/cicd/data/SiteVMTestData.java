package sn.ept.git.seminaire.cicd.data;

import sn.ept.git.seminaire.cicd.dto.vm.SiteVM;
import sn.ept.git.seminaire.cicd.models.Site;

public final class SiteVMTestData extends TestData {
    public static SiteVM defaultVM() {
        return SiteVM
                .builder()
                .id(TestData.Default.id)
                .createdDate(TestData.Default.createdDate)
                .lastModifiedDate(TestData.Default.lastModifiedDate)
                .version(TestData.Default.version)
                .deleted(TestData.Default.deleted)
                .enabled(TestData.Default.enabled)
                .name(TestData.Default.name)
                .phone(TestData.Default.phone)
                .email(TestData.Default.email)
                .longitude(TestData.Default.longitude)
                .latitude(TestData.Default.latitude)
                .build();
    }

    public static SiteVM updatedVM() {
        return SiteVM
                .builder()
                .id(TestData.Default.id)
                .createdDate(TestData.Update.createdDate)
                .lastModifiedDate(TestData.Update.lastModifiedDate)
                .version(TestData.Update.version)
                .deleted(TestData.Update.deleted)
                .enabled(TestData.Update.enabled)
                .name(TestData.Update.name)
                .phone(TestData.Update.phone)
                .email(TestData.Update.email)
                .longitude(TestData.Update.longitude)
                .latitude(TestData.Update.latitude)
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
                .name(Default.name)
                .societe(Default.societe)
                .build();

    }
}
