package es.redmic.db2es.jobs.job.indexing.maintenance.classifications.areatype;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.areas.model.AreaType;
import es.redmic.db.maintenance.areas.repository.AreaTypeRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.classifications.ClassificationsDomainJobName;
import es.redmic.es.maintenance.area.service.AreaTypeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.areas.dto.AreaTypeDTO;

@Configuration
public class AreaTypeJobConfig extends JobIndexingConfig<AreaType, AreaTypeDTO, DomainES> {

	private static final String JOB_NAME = ClassificationsDomainJobName.AREA_TYPE.toString();

	@Autowired
	AreaTypeRepository repository;

	@Autowired
	AreaTypeESService service;

	public AreaTypeJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job areaTypeJob() {

		return createJobIndexing().start(areaTypeStep()).build();
	}

	@Bean
	@StepScope
	public Step areaTypeStep() {

		return createStepIndexing(areaTypeItemReader(), areaTypeProccessor(), areaTypeItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<AreaType> areaTypeItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public AreaTypeProcessor areaTypeProccessor() {

		return new AreaTypeProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> areaTypeItemWriter() {

		return createItemWriter(service);
	}
}
