package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.taxon.status;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.taxonomy.model.Status;
import es.redmic.db.maintenance.taxonomy.repository.StatusRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.TaxonomyDomainJobName;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.StatusESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.taxonomy.dto.StatusDTO;

@Configuration
public class StatusJobConfig extends JobIndexingConfig<Status, StatusDTO, DomainES> {

	private static final String JOB_NAME = TaxonomyDomainJobName.STATUS.toString();

	@Autowired
	StatusRepository repository;

	@Autowired
	StatusESService service;

	public StatusJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job statusJob() {

		return createJobIndexing().start(statusStep()).build();
	}

	@Bean
	@StepScope
	public Step statusStep() {

		return createStepIndexing(statusItemReader(), statusProccessor(), statusItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Status> statusItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public StatusProcessor statusProccessor() {

		return new StatusProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> statusItemWriter() {

		return createItemWriter(service);
	}
}
