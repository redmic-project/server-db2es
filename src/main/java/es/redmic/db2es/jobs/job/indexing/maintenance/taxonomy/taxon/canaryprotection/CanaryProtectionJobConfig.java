package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.taxon.canaryprotection;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.taxonomy.model.CanaryProtection;
import es.redmic.db.maintenance.taxonomy.repository.CanaryProtectionRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.TaxonomyDomainJobName;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.CanaryProtectionESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.taxonomy.dto.CanaryProtectionDTO;

@Configuration
public class CanaryProtectionJobConfig extends JobIndexingConfig<CanaryProtection, CanaryProtectionDTO, DomainES> {

	private static final String JOB_NAME = TaxonomyDomainJobName.CANARY_PROTECTION.toString();

	@Autowired
	CanaryProtectionRepository repository;

	@Autowired
	CanaryProtectionESService service;

	public CanaryProtectionJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job canaryProtectionJob() {

		return createJobIndexing().start(canaryProtectionStep()).build();
	}

	@Bean
	@StepScope
	public Step canaryProtectionStep() {

		return createStepIndexing(canaryProtectionItemReader(), canaryProtectionProccessor(),
				canaryProtectionItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<CanaryProtection> canaryProtectionItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public CanaryProtectionProcessor canaryProtectionProccessor() {

		return new CanaryProtectionProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> canaryProtectionItemWriter() {

		return createItemWriter(service);
	}
}
