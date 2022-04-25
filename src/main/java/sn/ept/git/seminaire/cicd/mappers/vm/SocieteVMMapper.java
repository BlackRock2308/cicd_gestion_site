package sn.ept.git.seminaire.cicd.mappers.vm;


import org.mapstruct.Mapper;
import sn.ept.git.seminaire.cicd.dto.vm.SocieteVM;
import sn.ept.git.seminaire.cicd.mappers.generic.GenericMapper;
import sn.ept.git.seminaire.cicd.models.Societe;
import sn.ept.git.seminaire.cicd.services.impl.SocieteServiceImpl;

@Mapper(componentModel = "spring", uses = SocieteServiceImpl.class)
public interface SocieteVMMapper extends GenericMapper<Societe, SocieteVM> {

}
