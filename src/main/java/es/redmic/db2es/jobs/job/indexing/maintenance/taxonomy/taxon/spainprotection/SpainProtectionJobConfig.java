package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.taxon.spainprotection;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.taxonomy.model.SpainProtection;
import es.redmic.db.maintenance.taxonomy.repository.SpainProtectionRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.TaxonomyDomainJobName;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.SpainProtectionESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.taxonomy.dto.SpainProtectionDTO;

@Configuration
public class SpainProtectionJobConfig extends JobIndexingConfig<SpainProtection, SpainProtectionDTO, DomainES> {

	private static final String JOB_NAME = TaxonomyDomainJobName.SPAIN_PROTECTION.toString();

	@Autowired
	SpainProtectionRepository repository;

	@Autowired
	SpainProtectionESService service;

	public SpainProtectionJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job spainProtectionJob() {

		return createJobIndexing().start(spainProtectionStep()).build();
	}

	@Bean
	@StepScope
	public Step spainProtectionStep() {

		return createStepIndexing(spainProtectionItemReader(), spainProtectionProccessor(),
				spainProtectionItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<SpainProtection> spainProtectionItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public SpainProtectionProcessor spainProtectionProccessor() {

		return new SpainProtectionProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> spainProtectionItemWriter() {

		return createItemWriter(service);
	}
}
