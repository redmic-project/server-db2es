package es.redmic.db2es.jobs.job.indexing.taxonomy.species;

import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.administrative.taxonomy.model.AbstractSpecies;
import es.redmic.db.administrative.taxonomy.repository.SpeciesRepository;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.es.administrative.taxonomy.service.SpeciesESService;
import es.redmic.models.es.administrative.taxonomy.dto.SpeciesDTO;

public abstract class SpeciesCommonJobConfig extends
		JobIndexingConfig<AbstractSpecies, SpeciesDTO, 
						es.redmic.models.es.administrative.taxonomy.model.Species> {

	@Autowired
	SpeciesRepository repository;

	@Autowired
	SpeciesESService service;

	public SpeciesCommonJobConfig(String name) {

		super(name);
	}
}
