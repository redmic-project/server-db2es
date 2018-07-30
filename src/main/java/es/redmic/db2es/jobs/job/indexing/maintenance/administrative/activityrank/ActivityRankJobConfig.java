package es.redmic.db2es.jobs.job.indexing.maintenance.administrative.activityrank;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.administrative.model.ActivityRank;
import es.redmic.db.maintenance.administrative.repository.ActivityRankRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.administrative.AdministrativeDomainJobName;
import es.redmic.es.maintenance.domain.administrative.service.ActivityRankESService;
import es.redmic.models.es.common.dto.DomainDTO;
import es.redmic.models.es.common.model.DomainES;

@Configuration
public class ActivityRankJobConfig extends JobIndexingConfig<ActivityRank, DomainDTO, DomainES> {

	private static final String JOB_NAME = AdministrativeDomainJobName.ACTIVITY_RANK.toString();

	@Autowired
	ActivityRankRepository repository;
	
	@Autowired
	ActivityRankESService service;
	
	public ActivityRankJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job activityRankJob() {
		
		return createJobIndexing().start(activityRankStep()).build();
	}

	@Bean
	@StepScope
	public Step activityRankStep() {
		
		return createStepIndexing(activityRankItemReader(), activityRankProccessor(), activityRankItemWriter());
	}
	
	@Bean
	@StepScope
	public ItemReaderBase<ActivityRank> activityRankItemReader() {
		
		return createItemReader(repository);
	}
	
	@Bean
	public ActivityRankProcessor activityRankProccessor() {
		
		return new ActivityRankProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> activityRankItemWriter() {
		
		return createItemWriter(service);
	}
}
