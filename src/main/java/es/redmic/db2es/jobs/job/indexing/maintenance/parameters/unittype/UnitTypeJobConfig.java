package es.redmic.db2es.jobs.job.indexing.maintenance.parameters.unittype;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.parameter.model.UnitType;
import es.redmic.db.maintenance.parameter.repository.UnitTypeRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.parameters.ParametersDomainJobName;
import es.redmic.es.maintenance.parameter.service.UnitTypeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.parameter.dto.UnitTypeDTO;

@Configuration
public class UnitTypeJobConfig extends JobIndexingConfig<UnitType, UnitTypeDTO, DomainES> {

	private static final String JOB_NAME = ParametersDomainJobName.UNIT_TYPE.toString();

	@Autowired
	UnitTypeRepository repository;
	
	@Autowired
	UnitTypeESService service;
	
	public UnitTypeJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job unitTypeJob() {
		
		return createJobIndexing().start(unitTypeStep()).build();
	}

	@Bean
	@StepScope
	public Step unitTypeStep() {
		
		return createStepIndexing(unitTypeItemReader(), unitTypeProccessor(), unitTypeItemWriter());
	}
	
	@Bean
	@StepScope
	public ItemReaderBase<UnitType> unitTypeItemReader() {
		
		return createItemReader(repository);
	}
	
	@Bean
	public UnitTypeProcessor unitTypeProccessor() {
		
		return new UnitTypeProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> unitTypeItemWriter() {
		
		return createItemWriter(service);
	}
}
