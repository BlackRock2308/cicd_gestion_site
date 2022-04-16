package sn.ept.git.seminaire.cicd.mappers.vm;

import org.mapstruct.Mapper;
import sn.ept.git.seminaire.cicd.dto.vm.TypeVM;
import sn.ept.git.seminaire.cicd.mappers.generic.GenericMapper;
import sn.ept.git.seminaire.cicd.models.Type;

@Mapper(componentModel = "spring")
public interface TypeVMMapper extends GenericMapper<Type, TypeVM> {

}