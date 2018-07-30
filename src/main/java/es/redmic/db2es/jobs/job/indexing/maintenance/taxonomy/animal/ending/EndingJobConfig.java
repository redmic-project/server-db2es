package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.animal.ending;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.animal.model.Ending;
import es.redmic.db.maintenance.animal.repository.EndingRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.TaxonomyDomainJobName;
import es.redmic.es.maintenance.animal.service.EndingESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.animal.dto.EndingDTO;

@Configuration
public class EndingJobConfig extends JobIndexingConfig<Ending, EndingDTO, DomainES> {

	private static final String JOB_NAME = TaxonomyDomainJobName.ENDING.toString();

	@Autowired
	EndingRepository repository;

	@Autowired
	EndingESService service;

	public EndingJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job endingJob() {

		return createJobIndexing().start(endingStep()).build();
	}

	@Bean
	@StepScope
	public Step endingStep() {

		return createStepIndexing(endingItemReader(), endingProccessor(), endingItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Ending> endingItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public EndingProcessor endingProccessor() {

		return new EndingProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> endingItemWriter() {

		return createItemWriter(service);
	}
}
