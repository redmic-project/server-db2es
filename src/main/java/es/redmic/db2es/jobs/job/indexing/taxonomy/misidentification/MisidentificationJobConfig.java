package es.redmic.db2es.jobs.job.indexing.taxonomy.misidentification;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.administrative.taxonomy.model.Misidentification;
import es.redmic.db.administrative.taxonomy.repository.MisidentificationRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.taxonomy.TaxonomyJobName;
import es.redmic.es.administrative.taxonomy.service.MisidentificationESService;
import es.redmic.models.es.administrative.taxonomy.dto.MisidentificationDTO;

@Configuration
public class MisidentificationJobConfig extends
		JobIndexingConfig<Misidentification, MisidentificationDTO, es.redmic.models.es.administrative.taxonomy.model.Misidentification> {

	private static final String JOB_NAME = TaxonomyJobName.MISIDENTIFICATION.toString();

	@Autowired
	MisidentificationRepository repository;

	@Autowired
	MisidentificationESService service;

	public MisidentificationJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job misidentificationJob() {

		return createJobIndexing().start(misidentificationStep()).build();
	}

	@Bean
	public Step misidentificationStep() {

		return createStepIndexing(misidentificationItemReader(), misidentificationProccessor(),
				misidentificationItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Misidentification> misidentificationItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public MisidentificationProcessor misidentificationProccessor() {

		return new MisidentificationProcessor();
	}

	@Bean
	public ItemWriterBySave<es.redmic.models.es.administrative.taxonomy.model.Misidentification> misidentificationItemWriter() {

		return createItemWriter(service);
	}
}
