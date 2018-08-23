package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.taxon.confidence;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.geodata.common.domain.model.Confidence;
import es.redmic.db.geodata.common.domain.repository.ConfidenceRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.TaxonomyDomainJobName;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.ConfidenceESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.geojson.common.domain.dto.ConfidenceDTO;

@Configuration
public class ConfidenceJobConfig extends JobIndexingConfig<Confidence, ConfidenceDTO, DomainES> {

	private static final String JOB_NAME = TaxonomyDomainJobName.CONFIDENCE.toString();

	@Autowired
	ConfidenceRepository repository;

	@Autowired
	ConfidenceESService service;

	public ConfidenceJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job confidenceJob() {

		return createJobIndexing().start(confidenceStep()).build();
	}

	@Bean
	@StepScope
	public Step confidenceStep() {

		return createStepIndexing(confidenceItemReader(), confidenceProccessor(), confidenceItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Confidence> confidenceItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public ConfidenceProcessor confidenceProccessor() {

		return new ConfidenceProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> confidenceItemWriter() {

		return createItemWriter(service);
	}
}
