package sn.ept.git.seminaire.cicd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import sn.ept.git.seminaire.cicd.dto.base.InterventionBaseDTO;
import sn.ept.git.seminaire.cicd.dto.base.ToolBaseDTO;

import java.util.Set;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolDTO extends ToolBaseDTO {

    private Set<InterventionBaseDTO> interventions;

}
