package es.redmic.db2es.jobs.job.indexing.maintenance.parameters.parameter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.parameter.model.Parameter;
import es.redmic.db.maintenance.parameter.repository.ParameterRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.parameters.ParametersDomainJobName;
import es.redmic.es.maintenance.parameter.service.ParameterESService;
import es.redmic.models.es.maintenance.parameter.dto.ParameterDTO;

@Configuration
public class ParameterJobConfig
		extends JobIndexingConfig<Parameter, ParameterDTO, es.redmic.models.es.maintenance.parameter.model.Parameter> {

	private static final String JOB_NAME = ParametersDomainJobName.PARAMETER.toString();

	@Autowired
	ParameterRepository repository;

	@Autowired
	ParameterESService service;

	public ParameterJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job parameterJob() {

		return createJobIndexing().start(parameterStep()).build();
	}

	@Bean
	@StepScope
	public Step parameterStep() {

		return createStepIndexing(parameterItemReader(), parameterProccessor(), parameterItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Parameter> parameterItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public ParameterProcessor parameterProccessor() {

		return new ParameterProcessor();
	}

	@Bean
	public ItemWriterBySave<es.redmic.models.es.maintenance.parameter.model.Parameter> parameterItemWriter() {

		return createItemWriter(service);
	}
}
