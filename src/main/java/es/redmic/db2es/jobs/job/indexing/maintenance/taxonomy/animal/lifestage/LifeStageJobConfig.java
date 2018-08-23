package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.animal.lifestage;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.animal.model.LifeStage;
import es.redmic.db.maintenance.animal.repository.LifeStageRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.TaxonomyDomainJobName;
import es.redmic.es.maintenance.animal.service.LifeStageESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.animal.dto.LifeStageDTO;

@Configuration
public class LifeStageJobConfig extends JobIndexingConfig<LifeStage, LifeStageDTO, DomainES> {

	private static final String JOB_NAME = TaxonomyDomainJobName.LIFE_STAGE.toString();

	@Autowired
	LifeStageRepository repository;

	@Autowired
	LifeStageESService service;

	public LifeStageJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job lifeStageJob() {

		return createJobIndexing().start(lifeStageStep()).build();
	}

	@Bean
	@StepScope
	public Step lifeStageStep() {

		return createStepIndexing(lifeStageItemReader(), lifeStageProccessor(), lifeStageItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<LifeStage> lifeStageItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public LifeStageProcessor lifeStageProccessor() {

		return new LifeStageProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> lifeStageItemWriter() {

		return createItemWriter(service);
	}
}
