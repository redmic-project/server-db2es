package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.taxon.origin;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.taxonomy.model.Origin;
import es.redmic.db.maintenance.taxonomy.repository.OriginRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.TaxonomyDomainJobName;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.OriginESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.taxonomy.dto.OriginDTO;

@Configuration
public class OriginJobConfig extends JobIndexingConfig<Origin, OriginDTO, DomainES> {

	private static final String JOB_NAME = TaxonomyDomainJobName.ORIGIN.toString();

	@Autowired
	OriginRepository repository;
	
	@Autowired
	OriginESService service;
	
	public OriginJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job originJob() {
		
		return createJobIndexing().start(getOriginStep()).build();
	}

	@Bean
	@StepScope
	public Step getOriginStep() {
		
		return createStepIndexing(originItemReader(), originProccessor(), originItemWriter());
	}
	
	@Bean
	@StepScope
	public ItemReaderBase<Origin> originItemReader() {
		
		return createItemReader(repository);
	}
	
	@Bean
	public OriginProcessor originProccessor() {
		
		return new OriginProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> originItemWriter() {
		
		return createItemWriter(service);
	}
}
