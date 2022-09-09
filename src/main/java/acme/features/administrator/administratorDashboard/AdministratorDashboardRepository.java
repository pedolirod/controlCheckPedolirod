package acme.features.administrator.administratorDashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.artifact.Artifact;
import acme.entities.artifact.ArtifactType;
import acme.entities.delor.Delor;
import acme.entities.fineDish.StatusType;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {

	@Query("select count(a) from Artifact a where a.type = :type")
	Integer countArtifactByType(ArtifactType type);

	@Query("select s.acceptedCurrencies from SystemSettings s")
	String findAcceptedCurrencies();

	@Query("select avg(a.retailPrice.amount) from Artifact a where a.type = :type and a.retailPrice.currency = :currency")
	Double calcAverageArtifactRetailPriceByTypeAndCurrency(ArtifactType type, String currency);
	
	@Query("select stddev(a.retailPrice.amount) from Artifact a where a.type = :type and a.retailPrice.currency = :currency")
	Double calcDeviationArtifactRetailPriceByTypeAndCurrency(ArtifactType type, String currency);

	@Query("select min(a.retailPrice.amount) from Artifact a where a.type = :type and a.retailPrice.currency = :currency")
	Double calcMinimumArtifactRetailPriceByTypeAndCurrency(ArtifactType type, String currency);
	
	@Query("select max(a.retailPrice.amount) from Artifact a where a.type = :type and a.retailPrice.currency = :currency")
	Double calcMaximumArtifactRetailPriceByTypeAndCurrency(ArtifactType type, String currency);

	@Query("select count(f) from FineDish f where f.status = :status")
	Integer countFineDishByStatus(StatusType status);

	@Query("select avg(f.budget.amount) from FineDish f where f.status = :status")
	Double calcAverageFineDishBudgetByStatus(StatusType status);

	@Query("select stddev(f.budget.amount) from FineDish f where f.status = :status")
	Double calcDeviationFineDishBudgetByStatus(StatusType status);

	@Query("select max(f.budget.amount) from FineDish f where f.status = :status")
	Double calcMaximumFineDishBudgetByStatus(StatusType status);

	@Query("select min(f.budget.amount) from FineDish f where f.status = :status")
	Double calcMinimumFineDishBudgetByStatus(StatusType status);
	
	//PIMPAM
	
	@Query("select avg(f.income.amount) from Delor f where f.income.currency = :currency")
	Double calcAverageDelorBudgetByCurrency(String currency);

	@Query("select stddev(f.income.amount) from Delor f where f.income.currency = :currency")
	Double calcDeviationDelorBudgetByCurrency(String currency);

	@Query("select max(f.income.amount) from Delor f where f.income.currency = :currency")
	Double calcMaximumDelorBudgetByCurrency(String currency);

	@Query("select min(f.income.amount) from Delor f where f.income.currency = :currency")
	Double calcMinimumDelorBudgetByCurrency(String currency);
	
	@Query("select c from Delor c")
	Collection<Delor> findAllDelor();
	
	@Query("select a from Artifact a ")
	Collection<Artifact> findAllArtifact();

}
