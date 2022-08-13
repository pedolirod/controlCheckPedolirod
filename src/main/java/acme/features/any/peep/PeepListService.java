package acme.features.any.peep;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.peep.Peep;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;

@Service
public class PeepListService implements AbstractListService<Any, Peep>{
	
	// Internal state ---------------------------------------------------------

			@Autowired
			protected PeepRepository repository;

			// AbstractListService<Anonymous, Announcement>  interface -------------------------


			@Override
			public boolean authorise(final Request<Peep> request) {
				assert request != null;

				return true;
			}
			
			@Override
			public Collection<Peep> findMany(final Request<Peep> request) {
				assert request != null;

				Collection<Peep> result;

				result = this.repository.findManyPeep();

				return result;
			}
			
			@Override
			public void unbind(final Request<Peep> request, final Peep entity, final Model model) {
				assert request != null;
				assert entity != null;
				assert model != null;

				request.unbind(entity, model, "heading", "writer");
			}

}
