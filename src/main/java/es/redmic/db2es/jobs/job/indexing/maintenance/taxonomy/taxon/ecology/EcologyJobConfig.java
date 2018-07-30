package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.taxon.ecology;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.taxonomy.model.Ecology;
import es.redmic.db.maintenance.taxonomy.repository.EcologyRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.TaxonomyDomainJobName;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.EcologyESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.taxonomy.dto.EcologyDTO;

@Configuration
public class EcologyJobConfig extends JobIndexingConfig<Ecology, EcologyDTO, DomainES> {

	private static final String JOB_NAME = TaxonomyDomainJobName.ECOLOGY.toString();

	@Autowired
	EcologyRepository repository;
	
	@Autowired
	EcologyESService service;
	
	public EcologyJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job ecologyJob() {
		
		return createJobIndexing().start(ecologyStep()).build();
	}

	@Bean
	@StepScope
	public Step ecologyStep() {
		
		return createStepIndexing(ecologyItemReader(), ecologyProccessor(), ecologyItemWriter());
	}
	
	@Bean
	@StepScope
	public ItemReaderBase<Ecology> ecologyItemReader() {
		
		return createItemReader(repository);
	}
	
	@Bean
	public EcologyProcessor ecologyProccessor() {
		
		return new EcologyProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> ecologyItemWriter() {
		
		return createItemWriter(service);
	}
}
