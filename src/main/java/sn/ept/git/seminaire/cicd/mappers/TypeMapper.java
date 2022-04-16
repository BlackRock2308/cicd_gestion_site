package sn.ept.git.seminaire.cicd.mappers;


import org.mapstruct.Mapper;
import sn.ept.git.seminaire.cicd.dto.TypeDTO;
import sn.ept.git.seminaire.cicd.mappers.generic.GenericMapper;
import sn.ept.git.seminaire.cicd.models.Type;

@Mapper(componentModel = "spring")
public interface TypeMapper extends GenericMapper<Type, TypeDTO> {

}
