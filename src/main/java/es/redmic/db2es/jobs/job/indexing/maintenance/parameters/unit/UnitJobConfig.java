package es.redmic.db2es.jobs.job.indexing.maintenance.parameters.unit;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.parameter.model.Unit;
import es.redmic.db.maintenance.parameter.repository.UnitRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.parameters.ParametersDomainJobName;
import es.redmic.es.maintenance.parameter.service.UnitESService;
import es.redmic.models.es.maintenance.parameter.dto.UnitDTO;

@Configuration
public class UnitJobConfig
		extends JobIndexingConfig<Unit, UnitDTO, es.redmic.models.es.maintenance.parameter.model.Unit> {

	private static final String JOB_NAME = ParametersDomainJobName.UNIT.toString();

	@Autowired
	UnitRepository repository;

	@Autowired
	UnitESService service;

	public UnitJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job unitJob() {

		return createJobIndexing().start(unitStep()).build();
	}

	@Bean
	@StepScope
	public Step unitStep() {

		return createStepIndexing(unitItemReader(), unitProccessor(), unitItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Unit> unitItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public UnitProcessor unitProccessor() {

		return new UnitProcessor();
	}

	@Bean
	public ItemWriterBySave<es.redmic.models.es.maintenance.parameter.model.Unit> unitItemWriter() {

		return createItemWriter(service);
	}
}
