package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.animal.destiny;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.animal.model.Destiny;
import es.redmic.db.maintenance.animal.repository.DestinyRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.TaxonomyDomainJobName;
import es.redmic.es.maintenance.animal.service.DestinyESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.animal.dto.DestinyDTO;

@Configuration
public class DestinyJobConfig extends JobIndexingConfig<Destiny, DestinyDTO, DomainES> {

	private static final String JOB_NAME = TaxonomyDomainJobName.DESTINY.toString();

	@Autowired
	DestinyRepository repository;

	@Autowired
	DestinyESService service;

	public DestinyJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job destinyJob() {

		return createJobIndexing().start(destinyStep()).build();
	}

	@Bean
	@StepScope
	public Step destinyStep() {
		return createStepIndexing(destinyItemReader(), destinyProccessor(), destinyItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Destiny> destinyItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public DestinyProcessor destinyProccessor() {

		return new DestinyProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> destinyItemWriter() {

		return createItemWriter(service);
	}
}
