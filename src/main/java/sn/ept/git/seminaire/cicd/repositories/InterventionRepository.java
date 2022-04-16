package sn.ept.git.seminaire.cicd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.ept.git.seminaire.cicd.models.Intervention;

import java.util.UUID;

@Repository
public interface InterventionRepository extends JpaRepository<Intervention, UUID> {
}