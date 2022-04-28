package sn.ept.git.seminaire.cicd.dto.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import sn.ept.git.seminaire.cicd.dto.base.InterventionBaseDTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterventionVM extends InterventionBaseDTO {



    @JsonProperty("id_site")
    private UUID idSite;

    @JsonProperty("id_exercice")
    private UUID idExercice;

    @JsonProperty("ids_tools")
    private Set<UUID> idsTools;

    @JsonProperty("ids_agents_in")
    private Set<UUID> idsAgentsIn;

    @JsonProperty("id_agents_out")
    private Set<UUID> idsAgentsOut;

}