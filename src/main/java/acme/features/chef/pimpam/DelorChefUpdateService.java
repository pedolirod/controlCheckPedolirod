package acme.features.chef.pimpam;





import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.delor.Delor;
import acme.entities.systemSetting.SystemSettings;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.HttpMethod;
import acme.framework.controllers.Request;
import acme.framework.controllers.Response;
import acme.framework.datatypes.Money;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Chef;

@Service
public class DelorChefUpdateService implements AbstractUpdateService<Chef, Delor>{
	
	@Autowired
	protected DelorRepository repository;
	
	// AbstractUpdateService<Patron, Patronage> interface ---------------------
	
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
		

		
		request.bind(entity, errors,"keylet", "instantiationMoment", "subject", "explanation" ,"startPeriod",
			"finishPeriod", "income", "moreInfo");
	
		
	}

	@Override
	public void unbind(final Request<Delor> request, final Delor entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		

		
		request.unbind(entity, model,"keylet", "instantiationMoment", "subject", "description" ,"startPeriod",
				"finishPeriod", "income", "moreInfo");

		model.setAttribute("artifactId", entity.getArtifact().getId());
		model.setAttribute("artifactPublish", entity.getArtifact().isPublished());
		
	}

	@Override
	public Delor findOne(final Request<Delor> request) {
		assert request != null;
		
		Delor finedish;
		int id;
		
		id = request.getModel().getInteger("id");
		finedish = this.repository.findOneDelorById(id);
		
		return finedish;
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
		if (!errors.hasErrors("income")) {


			errors.state(request, money.getAmount()>=0., "income",
					"chef.pimpam.error.budget");
			
			errors.state(request, c.getAcceptedCurrencies().contains(money.getCurrency()) ,
					  "income", "chef.pimpam.not-able-currency");
		}
		
		
	}

	@Override
	public void update(final Request<Delor> request, final Delor entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);
	}
	
	@Override
	public void onSuccess(final Request<Delor> request, final Response<Delor> response) {
		assert request != null;
		assert response != null;

		if (request.isMethod(HttpMethod.POST)) {
			PrincipalHelper.handleUpdate();
		}
	}
	
	

}
