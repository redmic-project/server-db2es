package es.redmic.db2es.jobs.job.indexing.maintenance.classifications.infrastructuretype;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.point.model.InfrastructureType;
import es.redmic.db.maintenance.point.repository.InfrastructureTypeRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.classifications.ClassificationsDomainJobName;
import es.redmic.es.maintenance.point.service.InfrastructureTypeESService;
import es.redmic.models.es.maintenance.point.dto.InfrastructureTypeDTO;

@Configuration
public class InfrastructureTypeJobConfig extends
		JobIndexingConfig<InfrastructureType, InfrastructureTypeDTO, es.redmic.models.es.maintenance.point.model.InfrastructureType> {

	private static final String JOB_NAME = ClassificationsDomainJobName.INFRASTRUCTURE_TYPE.toString();

	@Autowired
	InfrastructureTypeRepository repository;

	@Autowired
	InfrastructureTypeESService service;

	public InfrastructureTypeJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job infrastructureTypeJob() {

		return createJobIndexing().start(getInfrastructureTypeStep()).build();
	}

	@Bean
	public Step getInfrastructureTypeStep() {

		return createStepIndexing(infrastructureTypeItemReader(), infrastructureTypeProccessor(),
				infrastructureTypeItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<InfrastructureType> infrastructureTypeItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public InfrastructureTypeProcessor infrastructureTypeProccessor() {

		return new InfrastructureTypeProcessor();
	}

	@Bean
	public ItemWriterBySave<es.redmic.models.es.maintenance.point.model.InfrastructureType> infrastructureTypeItemWriter() {

		return createItemWriter(service);
	}
}
