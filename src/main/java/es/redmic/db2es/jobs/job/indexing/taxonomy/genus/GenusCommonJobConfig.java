package es.redmic.db2es.jobs.job.indexing.taxonomy.genus;

import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.administrative.taxonomy.model.Genus;
import es.redmic.db.administrative.taxonomy.repository.GenusRepository;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.es.administrative.taxonomy.service.GenusESService;
import es.redmic.models.es.administrative.taxonomy.dto.TaxonDTO;
import es.redmic.models.es.administrative.taxonomy.model.Taxon;

public abstract class GenusCommonJobConfig
		extends JobIndexingConfig<Genus, TaxonDTO, Taxon> {
	
	@Autowired
	GenusRepository repository;

	@Autowired
	GenusESService service;
	

	public GenusCommonJobConfig(String name) {
		super(name);
	}
}
