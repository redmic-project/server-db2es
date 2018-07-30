package es.redmic.db2es.jobs.job.indexing.maintenance.administrative.country;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.administrative.model.Country;
import es.redmic.db.administrative.repository.CountryRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.administrative.AdministrativeDomainJobName;
import es.redmic.es.maintenance.domain.administrative.service.CountryESService;
import es.redmic.models.es.maintenance.administrative.dto.CountryDTO;

@Configuration
public class CountryJobConfig
		extends JobIndexingConfig<Country, CountryDTO, es.redmic.models.es.maintenance.administrative.model.Country> {

	private static final String JOB_NAME = AdministrativeDomainJobName.COUNTRY.toString();
	
	@Autowired
	CountryRepository repository;
	
	@Autowired
	CountryESService service;
	
	public CountryJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job countryJob() {
		
		return createJobIndexing().start(countryStep()).build();
	}

	@Bean
	@StepScope
	public Step countryStep() {
		
		return createStepIndexing(countryItemReader(), countryProccessor(), countryItemWriter());
	}
	
	@Bean
	@StepScope
	public ItemReaderBase<Country> countryItemReader() {
		
		return createItemReader(repository);
	}
	
	@Bean
	public CountryProcessor countryProccessor() {
		
		return new CountryProcessor();
	}

	@Bean
	public ItemWriterBySave<es.redmic.models.es.maintenance.administrative.model.Country> countryItemWriter() {
		
		return createItemWriter(service);
	}
}
