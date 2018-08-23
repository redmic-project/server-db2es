package es.redmic.db2es.jobs.job.indexing.maintenance.administrative.activitytype;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.administrative.model.ActivityType;
import es.redmic.db.maintenance.administrative.repository.ActivityTypeRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.administrative.AdministrativeDomainJobName;
import es.redmic.es.maintenance.domain.administrative.service.ActivityTypeESService;
import es.redmic.models.es.maintenance.administrative.dto.ActivityTypeDTO;

@Configuration
public class ActivityTypeJobConfig extends
		JobIndexingConfig<ActivityType, ActivityTypeDTO,
			es.redmic.models.es.maintenance.administrative.model.ActivityType> {

	private static final String JOB_NAME = AdministrativeDomainJobName.ACTIVITY_TYPE.toString();

	@Autowired
	ActivityTypeRepository repository;

	@Autowired
	ActivityTypeESService service;

	public ActivityTypeJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job activityTypeJob() {

		return createJobIndexing().start(activityTypeStep()).build();
	}

	@Bean
	@StepScope
	public Step activityTypeStep() {

		return createStepIndexing(activityTypeItemReader(), activityTypeProccessor(), activityTypeItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<ActivityType> activityTypeItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public ActivityTypeProcessor activityTypeProccessor() {

		return new ActivityTypeProcessor();
	}

	@Bean
	public ItemWriterBySave<es.redmic.models.es.maintenance.administrative.model.ActivityType> activityTypeItemWriter() {

		return createItemWriter(service);
	}
}
