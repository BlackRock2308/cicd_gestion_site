package sn.ept.git.seminaire.cicd.mappers;

import org.mapstruct.Mapper;
import sn.ept.git.seminaire.cicd.dto.ToolDTO;
import sn.ept.git.seminaire.cicd.mappers.generic.GenericMapper;
import sn.ept.git.seminaire.cicd.models.Tool;

@Mapper(componentModel = "spring")
public interface ToolMapper extends GenericMapper<Tool, ToolDTO> {

}
