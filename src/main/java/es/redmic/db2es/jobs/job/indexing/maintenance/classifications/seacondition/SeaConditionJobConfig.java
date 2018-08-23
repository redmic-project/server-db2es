package es.redmic.db2es.jobs.job.indexing.maintenance.classifications.seacondition;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.strech.model.SeaCondition;
import es.redmic.db.maintenance.strech.repository.SeaConditionRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.classifications.ClassificationsDomainJobName;
import es.redmic.es.maintenance.strech.service.SeaConditionESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.strech.dto.SeaConditionDTO;

@Configuration
public class SeaConditionJobConfig extends JobIndexingConfig<SeaCondition, SeaConditionDTO, DomainES> {

	private static final String JOB_NAME = ClassificationsDomainJobName.SEA_CONDITION.toString();

	@Autowired
	SeaConditionRepository repository;
	
	@Autowired
	SeaConditionESService service;
	
	public SeaConditionJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job seaConditionJob() {
		
		return createJobIndexing().start(getSeaConditionStep()).build();
	}

	@Bean
	@StepScope
	public Step getSeaConditionStep() {
		
		return createStepIndexing(seaConditionItemReader(), seaConditionProccessor(), seaConditionItemWriter());
	}
	
	@Bean
	@StepScope
	public ItemReaderBase<SeaCondition> seaConditionItemReader() {
		
		return createItemReader(repository);
	}
	
	@Bean
	public SeaConditionProcessor seaConditionProccessor() {
		
		return new SeaConditionProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> seaConditionItemWriter() {
		
		return createItemWriter(service);
	}
}
