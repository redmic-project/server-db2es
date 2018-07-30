package es.redmic.db2es.jobs.job.indexing.maintenance.parameters.parametertype;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.parameter.model.ParameterType;
import es.redmic.db.maintenance.parameter.repository.ParameterTypeRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.parameters.ParametersDomainJobName;
import es.redmic.es.maintenance.parameter.service.ParameterTypeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.parameter.dto.ParameterTypeDTO;

@Configuration
public class ParameterTypeJobConfig extends JobIndexingConfig<ParameterType, ParameterTypeDTO, DomainES> {

	private static final String JOB_NAME = ParametersDomainJobName.PARAMETER_TYPE.toString();

	@Autowired
	ParameterTypeRepository repository;

	@Autowired
	ParameterTypeESService service;

	public ParameterTypeJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job parameterTypeJob() {

		return createJobIndexing().start(parameterTypeStep()).build();
	}

	@Bean
	@StepScope
	public Step parameterTypeStep() {

		return createStepIndexing(parameterTypeItemReader(), parameterTypeProccessor(), parameterTypeItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<ParameterType> parameterTypeItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public ParameterTypeProcessor parameterTypeProccessor() {

		return new ParameterTypeProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> parameterTypeItemWriter() {

		return createItemWriter(service);
	}
}
