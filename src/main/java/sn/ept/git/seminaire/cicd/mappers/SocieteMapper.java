package sn.ept.git.seminaire.cicd.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import sn.ept.git.seminaire.cicd.dto.SocieteDTO;
import sn.ept.git.seminaire.cicd.mappers.generic.GenericMapper;
import sn.ept.git.seminaire.cicd.models.Societe;

@Mapper(componentModel = "spring")
public interface SocieteMapper extends GenericMapper<Societe, SocieteDTO> {

}