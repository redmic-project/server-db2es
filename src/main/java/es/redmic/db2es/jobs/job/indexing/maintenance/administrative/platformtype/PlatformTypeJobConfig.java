package es.redmic.db2es.jobs.job.indexing.maintenance.administrative.platformtype;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.administrative.model.PlatformType;
import es.redmic.db.maintenance.administrative.repository.PlatformTypeRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.administrative.AdministrativeDomainJobName;
import es.redmic.es.maintenance.domain.administrative.service.PlatformTypeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.administrative.dto.PlatformTypeDTO;

@Configuration
public class PlatformTypeJobConfig extends JobIndexingConfig<PlatformType, PlatformTypeDTO, DomainES> {

	private static final String JOB_NAME = AdministrativeDomainJobName.PLATFORM_TYPE.toString();

	@Autowired
	PlatformTypeRepository repository;
	
	@Autowired
	PlatformTypeESService service;
	
	public PlatformTypeJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job platformTypeJob() {
		
		return createJobIndexing().start(platformTypeStep()).build();
	}

	@Bean
	@StepScope
	public Step platformTypeStep() {
		
		return createStepIndexing(platformTypeItemReader(), platformTypeProccessor(), platformTypeItemWriter());
	}
	
	@Bean
	@StepScope
	public ItemReaderBase<PlatformType> platformTypeItemReader() {
		
		return createItemReader(repository);
	}
	
	@Bean
	public PlatformTypeProcessor platformTypeProccessor() {
		
		return new PlatformTypeProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> platformTypeItemWriter() {
		
		return createItemWriter(service);
	}
}
