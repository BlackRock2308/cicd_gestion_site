package sn.ept.git.seminaire.cicd.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import sn.ept.git.seminaire.cicd.dto.BaseDTO;

@SuperBuilder
@Data(staticConstructor = "of")
@AllArgsConstructor
@NoArgsConstructor
public class TypeBaseDTO extends BaseDTO {
    private String name;
    private String description;

}