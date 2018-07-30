package es.redmic.db2es.jobs.job.indexing.taxonomy.phylum;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.administrative.taxonomy.model.Filum;
import es.redmic.db.administrative.taxonomy.repository.FilumRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.taxonomy.TaxonomyJobName;
import es.redmic.es.administrative.taxonomy.service.FilumESService;
import es.redmic.models.es.administrative.taxonomy.dto.TaxonDTO;
import es.redmic.models.es.administrative.taxonomy.model.Taxon;

@Configuration
public class PhylumJobConfig extends JobIndexingConfig<Filum, TaxonDTO, Taxon> {

	private static final String JOB_NAME = TaxonomyJobName.PHYLUM.toString();

	@Autowired
	FilumRepository repository;

	@Autowired
	FilumESService service;

	public PhylumJobConfig() {
		super(JOB_NAME);
	}

	@Bean
	public Job phylumJob() {

		return createJobIndexing().start(phylumStep()).build();
	}

	@Bean
	public Step phylumStep() {

		return createStepIndexing(phylumItemReader(), phylumProccessor(), phylumItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Filum> phylumItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public ItemWriterBySave<Taxon> phylumItemWriter() {

		return createItemWriter(service);
	}

	@Bean
	public PhylumProcessor phylumProccessor() {

		return new PhylumProcessor();
	}
}
