package sn.ept.git.seminaire.cicd.data;

import sn.ept.git.seminaire.cicd.dto.AgentDTO;

public final class AgentDTOTestData extends TestData {

    public static AgentDTO defaultDTO() {
        return AgentDTO
                .builder()
                .id(Default.id)
                .createdDate(Default.createdDate)
                .lastModifiedDate(Default.lastModifiedDate)
                .version(Default.version)
                .deleted(Default.deleted)
                .enabled(Default.enabled)
                .firstName(Default.firstName)
                .lastName(Default.lastName)
                .address(Default.address)
                .phone(Default.phone)
                .email(Default.email)
                .build();
    }

    public static AgentDTO updatedDTO() {
        return AgentDTO
                .builder()
                .id(Default.id)
                .createdDate(Update.createdDate)
                .lastModifiedDate(Update.lastModifiedDate)
                .version(Update.version)
                .deleted(Update.deleted)
                .enabled(Update.enabled)
                .firstName(Update.firstName)
                .lastName(Update.lastName)
                .address(Update.address)
                .phone(Update.phone)
                .email(Update.email)
                .build();
    }
}
