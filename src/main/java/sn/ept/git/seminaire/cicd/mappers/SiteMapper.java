package sn.ept.git.seminaire.cicd.mappers;

import org.mapstruct.Mapper;
import sn.ept.git.seminaire.cicd.dto.SiteDTO;
import sn.ept.git.seminaire.cicd.mappers.generic.GenericMapper;
import sn.ept.git.seminaire.cicd.models.Site;

@Mapper(componentModel = "spring")
public interface SiteMapper extends GenericMapper<Site, SiteDTO> {

}