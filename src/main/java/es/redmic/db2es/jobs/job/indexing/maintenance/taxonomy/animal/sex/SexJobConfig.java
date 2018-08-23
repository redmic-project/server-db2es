package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.animal.sex;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.animal.model.Sex;
import es.redmic.db.maintenance.animal.repository.SexRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.TaxonomyDomainJobName;
import es.redmic.es.maintenance.animal.service.SexESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.animal.dto.SexDTO;

@Configuration
public class SexJobConfig extends JobIndexingConfig<Sex, SexDTO, DomainES> {

	private static final String JOB_NAME = TaxonomyDomainJobName.SEX.toString();

	@Autowired
	SexRepository repository;

	@Autowired
	SexESService service;

	public SexJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job sexJob() {

		return createJobIndexing().start(sexStep()).build();
	}

	@Bean
	@StepScope
	public Step sexStep() {

		return createStepIndexing(sexItemReader(), sexProccessor(), sexItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Sex> sexItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public SexProcessor sexProccessor() {

		return new SexProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> sexItemWriter() {

		return createItemWriter(service);
	}
}
