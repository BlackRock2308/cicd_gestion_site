package sn.ept.git.seminaire.cicd.mappers;


import org.mapstruct.Mapper;
import sn.ept.git.seminaire.cicd.dto.AttachementDTO;
import sn.ept.git.seminaire.cicd.mappers.generic.GenericMapper;
import sn.ept.git.seminaire.cicd.models.Attachement;

@Mapper(componentModel = "spring")
public interface AttachementMapper extends GenericMapper<Attachement, AttachementDTO> {

}