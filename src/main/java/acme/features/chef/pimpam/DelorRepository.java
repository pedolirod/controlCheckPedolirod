package acme.features.chef.pimpam;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.artifact.Artifact;
import acme.entities.delor.Delor;
import acme.entities.systemSetting.SystemSettings;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface DelorRepository extends AbstractRepository{
	
	@Query("select a from Delor a where a.id = :id")
	Delor findOneDelorById(int id);
	
	@Query("select artifact from Artifact artifact")
	List<Artifact> findAllArtifact();
	
	@Query("select pimpam from Delor pimpam")
	List<Delor> findAllDelor();
	
	@Query("select artifact from Artifact artifact where artifact.id = :id")
	Artifact findArtifactById(int id);
	
	@Query("select a from Artifact a LEFT JOIN Delor c ON c.artifact=a WHERE c IS NULL")
	List<Artifact> findArtifactList();
	
	@Query("select a from Delor a where a.keylet = :code")
	Delor findOneDelorByCode(String code);

	@Query("select a from Delor a")
	Collection<Delor> findManyDelor();
	
	@Query("select a from Delor a where a.artifact.id = :i")
	Collection<Delor> findManyDelorByArtifact(Integer i);

	@Query("select s from SystemSettings s")
	SystemSettings findConfiguration();

}
