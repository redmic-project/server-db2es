package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.taxon.permanence;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.taxonomy.model.Permanence;
import es.redmic.db.maintenance.taxonomy.repository.PermanenceRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.TaxonomyDomainJobName;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.PermanenceESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.taxonomy.dto.PermanenceDTO;

@Configuration
public class PermanenceJobConfig extends JobIndexingConfig<Permanence, PermanenceDTO, DomainES> {

	private static final String JOB_NAME = TaxonomyDomainJobName.PERMANENCE.toString();

	@Autowired
	PermanenceRepository repository;

	@Autowired
	PermanenceESService service;

	public PermanenceJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job permanenceJob() {

		return createJobIndexing().start(getPermanenceStep()).build();
	}

	@Bean
	@StepScope
	public Step getPermanenceStep() {

		return createStepIndexing(permanenceItemReader(), permanenceProccessor(), permanenceItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Permanence> permanenceItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public PermanenceProcessor permanenceProccessor() {

		return new PermanenceProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> permanenceItemWriter() {

		return createItemWriter(service);
	}
}
