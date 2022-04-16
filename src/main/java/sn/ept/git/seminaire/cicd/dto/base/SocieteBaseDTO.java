package sn.ept.git.seminaire.cicd.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import sn.ept.git.seminaire.cicd.dto.BaseDTO;
import sn.ept.git.seminaire.cicd.utils.SizeMapping;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocieteBaseDTO extends BaseDTO {


    @NotBlank
    @Size(min = SizeMapping.Name.MIN,max = SizeMapping.Name.MIN)
    private  String name;
    @Size(min = SizeMapping.Adresse.MIN,max = SizeMapping.Adresse.MIN)
    private  String address;
    @Size(min = SizeMapping.Phone.MIN,max = SizeMapping.Phone.MIN)
    private  String phone;
    @Size(min = SizeMapping.Email.MIN,max = SizeMapping.Email.MIN)
    private String email;
    private float longitude;
    private float latitude;

}
