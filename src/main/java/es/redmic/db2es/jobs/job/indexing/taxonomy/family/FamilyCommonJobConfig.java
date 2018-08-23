package es.redmic.db2es.jobs.job.indexing.taxonomy.family;

import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.administrative.taxonomy.model.Family;
import es.redmic.db.administrative.taxonomy.repository.FamilyRepository;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.es.administrative.taxonomy.service.FamilyESService;
import es.redmic.models.es.administrative.taxonomy.dto.TaxonDTO;
import es.redmic.models.es.administrative.taxonomy.model.Taxon;

public abstract class FamilyCommonJobConfig extends JobIndexingConfig<Family, TaxonDTO, Taxon> {

	@Autowired
	FamilyRepository repository;

	@Autowired
	FamilyESService service;

	public FamilyCommonJobConfig(String name) {
		super(name);
	}

}
