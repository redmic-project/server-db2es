package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.taxon.interest;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.taxonomy.model.Interest;
import es.redmic.db.maintenance.taxonomy.repository.InterestRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.TaxonomyDomainJobName;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.InterestESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.taxonomy.dto.InterestDTO;

@Configuration
public class InterestJobConfig extends JobIndexingConfig<Interest, InterestDTO, DomainES> {

	private static final String JOB_NAME = TaxonomyDomainJobName.INTEREST.toString();

	@Autowired
	InterestRepository repository;

	@Autowired
	InterestESService service;

	public InterestJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job interestJob() {

		return createJobIndexing().start(interestStep()).build();
	}

	@Bean
	@StepScope
	public Step interestStep() {

		return createStepIndexing(interestItemReader(), interestProccessor(), interestItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Interest> interestItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public InterestProcessor interestProccessor() {

		return new InterestProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> interestItemWriter() {

		return createItemWriter(service);
	}
}
