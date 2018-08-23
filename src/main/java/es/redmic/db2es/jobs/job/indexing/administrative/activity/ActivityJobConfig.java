package es.redmic.db2es.jobs.job.indexing.administrative.activity;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.administrative.model.Activity;
import es.redmic.db.administrative.repository.ActivityRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.administrative.AdministrativeJobName;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.es.administrative.service.ActivityESService;
import es.redmic.models.es.administrative.dto.ActivityDTO;

@Configuration
public class ActivityJobConfig
		extends JobIndexingConfig<Activity, ActivityDTO, es.redmic.models.es.administrative.model.Activity> {

	private static final String JOB_NAME = AdministrativeJobName.ACTIVITY.toString();

	@Autowired
	ActivityRepository repository;

	@Autowired
	ActivityESService service;

	public ActivityJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job activityJob() {
		
		return createJobIndexing().start(activityStep()).build();
	}

	@Bean
	public Step activityStep() {
		
		return createStepIndexing(activityItemReader(), activityProccessor(), activityItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Activity> activityItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public ActivityProcessor activityProccessor() {

		return new ActivityProcessor();
	}

	@Bean
	public ItemWriterBySave<es.redmic.models.es.administrative.model.Activity> activityItemWriter() {

		return createItemWriter(service);
	}
}
