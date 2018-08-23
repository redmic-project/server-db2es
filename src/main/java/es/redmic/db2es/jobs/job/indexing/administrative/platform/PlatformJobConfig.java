package es.redmic.db2es.jobs.job.indexing.administrative.platform;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.administrative.model.Platform;
import es.redmic.db.administrative.repository.PlatformRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.administrative.AdministrativeJobName;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.es.administrative.service.PlatformESService;
import es.redmic.models.es.administrative.dto.PlatformDTO;

@Configuration
public class PlatformJobConfig extends JobIndexingConfig<Platform, PlatformDTO, es.redmic.models.es.administrative.model.Platform> {

	private static final String JOB_NAME = AdministrativeJobName.PLATFORM.toString();

	@Autowired
	PlatformRepository repository;
	
	@Autowired
	PlatformESService service;
	
	public PlatformJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job platformJob() {
		
		return createJobIndexing().start(platformStep()).build();
	}

	@Bean
	public Step platformStep() {
		
		return createStepIndexing(platformItemReader(), platformProccessor(), platformItemWriter());
	}
	
	@Bean
	@StepScope
	public ItemReaderBase<Platform> platformItemReader() {
		
		return createItemReader(repository);
	}
	
	@Bean
	public PlatformProcessor platformProccessor() {
		
		return new PlatformProcessor();
	}

	@Bean
	public ItemWriterBySave<es.redmic.models.es.administrative.model.Platform> platformItemWriter() {
		
		return createItemWriter(service);
	}
}
