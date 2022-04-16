package sn.ept.git.seminaire.cicd.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import sn.ept.git.seminaire.cicd.dto.base.EventBaseDTO;
import sn.ept.git.seminaire.cicd.dto.base.TypeBaseDTO;

import java.util.Set;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeDTO extends TypeBaseDTO {

    private Set<EventBaseDTO> events;

}