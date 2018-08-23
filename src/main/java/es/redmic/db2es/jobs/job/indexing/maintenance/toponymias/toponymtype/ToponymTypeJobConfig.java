package es.redmic.db2es.jobs.job.indexing.maintenance.toponymias.toponymtype;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.toponym.model.ToponymType;
import es.redmic.db.maintenance.toponym.repository.ToponymTypeRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.toponymias.ToponymiasJobName;
import es.redmic.es.maintenance.toponym.service.ToponymTypeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.toponym.dto.ToponymTypeDTO;

@Configuration
public class ToponymTypeJobConfig extends JobIndexingConfig<ToponymType, ToponymTypeDTO, DomainES> {

	private static final String JOB_NAME = ToponymiasJobName.TOPONYM_TYPE.toString();

	@Autowired
	ToponymTypeRepository repository;

	@Autowired
	ToponymTypeESService service;

	public ToponymTypeJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job toponymTypeJob() {

		return createJobIndexing().start(toponymTypeStep()).build();
	}

	@Bean
	public Step toponymTypeStep() {

		return createStepIndexing(toponymTypeItemReader(), toponymTypeProccessor(), toponymTypeItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<ToponymType> toponymTypeItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public ToponymTypeProcessor toponymTypeProccessor() {

		return new ToponymTypeProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> toponymTypeItemWriter() {

		return createItemWriter(service);
	}
}
