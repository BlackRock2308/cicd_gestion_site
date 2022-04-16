package sn.ept.git.seminaire.cicd.mappers;

import org.mapstruct.Mapper;
import sn.ept.git.seminaire.cicd.dto.AgentDTO;
import sn.ept.git.seminaire.cicd.mappers.generic.GenericMapper;
import sn.ept.git.seminaire.cicd.models.Agent;

@Mapper(componentModel = "spring")
public interface AgentMapper extends GenericMapper<Agent, AgentDTO> {

}
