package es.redmic.db2es.jobs.job.indexing.maintenance.classifications.thematictype;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.areas.model.ThematicType;
import es.redmic.db.maintenance.areas.repository.ThematicTypeRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.classifications.ClassificationsDomainJobName;
import es.redmic.es.maintenance.area.service.ThematicTypeESService;
import es.redmic.models.es.maintenance.areas.dto.ThematicTypeDTO;

@Configuration
public class ThematicTypeJobConfig extends
		JobIndexingConfig<ThematicType, ThematicTypeDTO, es.redmic.models.es.maintenance.areas.model.ThematicType> {

	private static final String JOB_NAME = ClassificationsDomainJobName.THEMATIC_TYPE.toString();

	@Autowired
	ThematicTypeRepository repository;

	@Autowired
	ThematicTypeESService service;

	public ThematicTypeJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job thematicTypeJob() {

		return createJobIndexing().start(thematicTypeStep()).build();
	}

	@Bean
	public Step thematicTypeStep() {

		return createStepIndexing(thematicTypeItemReader(), thematicTypeProccessor(), thematicTypeItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<ThematicType> thematicTypeItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public ThematicTypeProcessor thematicTypeProccessor() {

		return new ThematicTypeProcessor();
	}

	@Bean
	public ItemWriterBySave<es.redmic.models.es.maintenance.areas.model.ThematicType> thematicTypeItemWriter() {

		return createItemWriter(service);
	}
}
