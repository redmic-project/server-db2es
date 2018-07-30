package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.taxon.trophicregime;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.taxonomy.model.TrophicRegime;
import es.redmic.db.maintenance.taxonomy.repository.TrophicRegimeRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.TaxonomyDomainJobName;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.TrophicRegimeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.taxonomy.dto.TrophicRegimeDTO;

@Configuration
public class TrophicRegimeJobConfig extends JobIndexingConfig<TrophicRegime, TrophicRegimeDTO, DomainES> {

	private static final String JOB_NAME = TaxonomyDomainJobName.TROPHIC_REGIME.toString();

	@Autowired
	TrophicRegimeRepository repository;

	@Autowired
	TrophicRegimeESService service;

	public TrophicRegimeJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job trophicRegimeJob() {

		return createJobIndexing().start(trophicRegimeStep()).build();
	}

	@Bean
	@StepScope
	public Step trophicRegimeStep() {

		return createStepIndexing(trophicRegimeItemReader(), trophicRegimeProccessor(), trophicRegimeItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<TrophicRegime> trophicRegimeItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public TrophicRegimeProcessor trophicRegimeProccessor() {

		return new TrophicRegimeProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> trophicRegimeItemWriter() {

		return createItemWriter(service);
	}
}
