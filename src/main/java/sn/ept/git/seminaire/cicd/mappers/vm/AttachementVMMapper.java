package sn.ept.git.seminaire.cicd.mappers.vm;

import org.mapstruct.Mapper;
import sn.ept.git.seminaire.cicd.dto.vm.AttachementVM;
import sn.ept.git.seminaire.cicd.mappers.generic.GenericMapper;
import sn.ept.git.seminaire.cicd.models.Attachement;

@Mapper(componentModel = "spring")
public interface AttachementVMMapper extends GenericMapper<Attachement, AttachementVM> {

}
