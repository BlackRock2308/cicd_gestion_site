package sn.ept.git.seminaire.cicd.mappers;

import org.mapstruct.Mapper;
import sn.ept.git.seminaire.cicd.dto.ExerciceDTO;
import sn.ept.git.seminaire.cicd.mappers.generic.GenericMapper;
import sn.ept.git.seminaire.cicd.models.Exercice;

@Mapper(componentModel = "spring")
public interface ExerciceMapper extends GenericMapper<Exercice, ExerciceDTO> {

}
