package sn.ept.git.seminaire.cicd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import sn.ept.git.seminaire.cicd.dto.base.SiteBaseDTO;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteDTO extends SiteBaseDTO {

    private SocieteDTO societe;

}