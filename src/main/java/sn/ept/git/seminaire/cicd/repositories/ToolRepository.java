package sn.ept.git.seminaire.cicd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.ept.git.seminaire.cicd.models.Tool;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ToolRepository extends JpaRepository<Tool, UUID> {

    @Query("select t from Tool  t where t.name=:name")
    Optional<Tool> findByName(@Param("name") String name);
}
