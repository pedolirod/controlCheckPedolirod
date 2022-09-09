package acme.features.chef.pimpam;



import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.artifact.Artifact;
import acme.entities.artifact.ArtifactType;
import acme.entities.delor.Delor;
import acme.entities.systemSetting.SystemSettings;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractCreateService;
import acme.roles.Chef;

@Service
public class DelorChefCreateService implements AbstractCreateService<Chef, Delor>{
	
	@Autowired
	protected DelorRepository repository;
		
	// AbstractCreateService<Patron, Patronage> interface ---------------------
	
	@Override
	public boolean authorise(final Request<Delor> request) {
		assert request != null;
		
		return true;
	}

	@Override
	public void bind(final Request<Delor> request, final Delor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		
		
		request.bind(entity, errors, "subject", "explanation", "startPeriod", "finishPeriod", "income", "moreInfo");
		
		Model model;
		Artifact selectedArtifact;

		model = request.getModel();
		selectedArtifact = this.repository.findArtifactById(Integer.parseInt(model.getString("artifacts")));

		entity.setArtifact(selectedArtifact);

	}

	@Override
	public void unbind(final Request<Delor> request, final Delor entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		List<Artifact> artifacts;
		
		final List<Delor> lp=this.repository.findAllDelor();
		final Set<Artifact> la= new HashSet<Artifact>();
		for(final Delor p:lp) {
			if(p.getArtifact().getType().equals(ArtifactType.INGREDIENT) && !p.getArtifact().isPublished()) {
				la.add(p.getArtifact());
			}
			
		}
		
		artifacts=this.repository.findAllArtifact();	
	
		request.unbind(entity, model, "subject", "explanation", "startPeriod", "finishPeriod", "income", "moreInfo");
		
		model.setAttribute("isNew", true);
		model.setAttribute("artifacts", artifacts.stream().filter(x->!x.isPublished()).filter(y->!la.contains(y)).collect(Collectors.toList()));
	}

	@Override
	public Delor instantiate(final Request<Delor> request) {
		assert request != null;
		
		Delor result;
		
		
		
		result = new Delor();
		final LocalDate localDate = LocalDate.now();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
		final int random = (int) (Math.random()*(9999-1000+1)+1000);
		final String formattedString = localDate.format(formatter);
		final String keylet=""+random+formatter;
		result.setKeylet(keylet);
		result.setInstantiationMoment(Calendar.getInstance().getTime());
		
		return result;
	}

	@Override
	public void validate(final Request<Delor> request, final Delor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		final Calendar d=Calendar.getInstance();
		d.setTime(entity.getInstantiationMoment());
		d.add(Calendar.MONTH, 1);
		
		
		if (!errors.hasErrors("startPeriod")) {


			errors.state(request, entity.getStartPeriod().after(d.getTime()), "startPeriod",
					"chef.pimpam.error.month.startPeriod");
		}
		
		final Calendar ds=Calendar.getInstance();
		if(entity.getStartPeriod()!=null ) {
		ds.setTime(entity.getStartPeriod());
		}
		ds.add(Calendar.DAY_OF_YEAR, 7);
		
		
		if (!errors.hasErrors("finishPeriod")) {


			errors.state(request, entity.getFinishPeriod().after(ds.getTime()), "finishPeriod",
					"chef.pimpam.error.week.finishPeriod");
		}
		
		
		
		

		
		final Money money=entity.getIncome();
		final SystemSettings c = this.repository.findConfiguration();
		if (!errors.hasErrors("budget")) {


			errors.state(request, money.getAmount()>=0., "budget",
					"chef.pimpam.error.budget");
			
			errors.state(request, c.getAcceptedCurrencies().contains(money.getCurrency()) ,
					  "budget", "chef.pimpam.not-able-currency");
		}
		

		
		
	}

	@Override
	public void create(final Request<Delor> request, final Delor entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);
	}
	

}
