package es.redmic.db2es.jobs.job.indexing.maintenance.administrative.scope;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.administrative.model.Scope;
import es.redmic.db.maintenance.administrative.repository.ScopeRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.administrative.AdministrativeDomainJobName;
import es.redmic.es.maintenance.domain.administrative.service.ScopeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.administrative.dto.ScopeDTO;

@Configuration
public class ScopeJobConfig extends JobIndexingConfig<Scope, ScopeDTO, DomainES> {

	private static final String JOB_NAME = AdministrativeDomainJobName.SCOPE.toString();

	@Autowired
	ScopeRepository repository;

	@Autowired
	ScopeESService service;

	public ScopeJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job scopeJob() {

		return createJobIndexing().start(scopeStep()).build();
	}

	@Bean
	@StepScope
	public Step scopeStep() {

		return createStepIndexing(scopeItemReader(), scopeProccessor(), scopeItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Scope> scopeItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public ScopeProcessor scopeProccessor() {

		return new ScopeProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> scopeItemWriter() {

		return createItemWriter(service);
	}
}
