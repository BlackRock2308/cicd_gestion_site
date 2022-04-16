package sn.ept.git.seminaire.cicd.mappers.vm;

import org.mapstruct.Mapper;
import sn.ept.git.seminaire.cicd.dto.vm.AgentVM;
import sn.ept.git.seminaire.cicd.mappers.generic.GenericMapper;
import sn.ept.git.seminaire.cicd.models.Agent;

@Mapper(componentModel = "spring")
public interface AgentVMMapper extends GenericMapper<Agent, AgentVM> {

}
