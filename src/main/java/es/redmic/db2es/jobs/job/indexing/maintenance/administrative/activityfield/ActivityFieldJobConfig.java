package es.redmic.db2es.jobs.job.indexing.maintenance.administrative.activityfield;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.administrative.model.ActivityField;
import es.redmic.db.maintenance.administrative.repository.ActivityFieldRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.administrative.AdministrativeDomainJobName;
import es.redmic.es.maintenance.domain.administrative.service.ActivityFieldESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.administrative.dto.ActivityFieldDTO;

@Configuration
public class ActivityFieldJobConfig extends JobIndexingConfig<ActivityField, ActivityFieldDTO, DomainES> {

	private static final String JOB_NAME = AdministrativeDomainJobName.ACTIVITY_FIELD.toString();

	@Autowired
	ActivityFieldRepository repository;

	@Autowired
	ActivityFieldESService service;

	public ActivityFieldJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job activityFieldJob() {

		return createJobIndexing().start(activityFieldStep()).build();
	}

	@Bean
	@StepScope
	public Step activityFieldStep() {

		return createStepIndexing(activityFieldItemReader(), activityFieldProccessor(), activityFieldItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<ActivityField> activityFieldItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public ActivityFieldProcessor activityFieldProccessor() {

		return new ActivityFieldProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> activityFieldItemWriter() {

		return createItemWriter(service);
	}
}
