package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.taxon.endemicity;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.taxonomy.model.Endemicity;
import es.redmic.db.maintenance.taxonomy.repository.EndemicityRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.TaxonomyDomainJobName;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.EndemicityESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.taxonomy.dto.EndemicityDTO;

@Configuration
public class EndemicityJobConfig extends JobIndexingConfig<Endemicity, EndemicityDTO, DomainES> {

	private static final String JOB_NAME = TaxonomyDomainJobName.ENDEMICITY.toString();

	@Autowired
	EndemicityRepository repository;

	@Autowired
	EndemicityESService service;

	public EndemicityJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job endemicityJob() {

		return createJobIndexing().start(endemicityStep()).build();
	}

	@Bean
	@StepScope
	public Step endemicityStep() {

		return createStepIndexing(endemicityItemReader(), endemicityProccessor(), endemicityItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Endemicity> endemicityItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public EndemicityProcessor endemicityProccessor() {

		return new EndemicityProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> endemicityItemWriter() {

		return createItemWriter(service);
	}
}
