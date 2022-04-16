package sn.ept.git.seminaire.cicd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import sn.ept.git.seminaire.cicd.dto.base.SocieteBaseDTO;

import java.util.Set;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocieteDTO extends SocieteBaseDTO {

    private Set<SiteDTO> sites;
    private Set<ExerciceDTO> exercices;
}
