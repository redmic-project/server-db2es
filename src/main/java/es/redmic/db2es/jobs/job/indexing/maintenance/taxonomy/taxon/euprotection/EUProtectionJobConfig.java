package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.taxon.euprotection;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.taxonomy.model.EUProtection;
import es.redmic.db.maintenance.taxonomy.repository.EUProtectionRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.TaxonomyDomainJobName;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.EUProtectionESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.taxonomy.dto.EUProtectionDTO;

@Configuration
public class EUProtectionJobConfig extends JobIndexingConfig<EUProtection, EUProtectionDTO, DomainES> {

	private static final String JOB_NAME = TaxonomyDomainJobName.EU_PROTECTION.toString();

	@Autowired
	EUProtectionRepository repository;

	@Autowired
	EUProtectionESService service;

	public EUProtectionJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job euProtectionJob() {

		return createJobIndexing().start(eUProtectionStep()).build();
	}

	@Bean
	@StepScope
	public Step eUProtectionStep() {

		return createStepIndexing(eUProtectionItemReader(), eUProtectionProccessor(), eUProtectionItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<EUProtection> eUProtectionItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public EUProtectionProcessor eUProtectionProccessor() {

		return new EUProtectionProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> eUProtectionItemWriter() {

		return createItemWriter(service);
	}
}
