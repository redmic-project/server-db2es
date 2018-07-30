package es.redmic.db2es.jobs.job.indexing.taxonomy.order;

import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.administrative.taxonomy.model.Orderr;
import es.redmic.db.administrative.taxonomy.repository.OrderRepository;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.es.administrative.taxonomy.service.OrderESService;
import es.redmic.models.es.administrative.taxonomy.dto.TaxonDTO;
import es.redmic.models.es.administrative.taxonomy.model.Taxon;

public abstract class OrderCommonJobConfig extends JobIndexingConfig<Orderr, TaxonDTO, Taxon> {

	@Autowired
	OrderRepository repository;

	@Autowired
	OrderESService service;

	public OrderCommonJobConfig(String name) {

		super(name);
	}
}
