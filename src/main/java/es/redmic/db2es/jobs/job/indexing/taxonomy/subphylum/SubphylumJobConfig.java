package es.redmic.db2es.jobs.job.indexing.taxonomy.subphylum;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.administrative.taxonomy.model.Subfilum;
import es.redmic.db.administrative.taxonomy.repository.SubfilumRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.taxonomy.TaxonomyJobName;
import es.redmic.es.administrative.taxonomy.service.SubfilumESService;
import es.redmic.models.es.administrative.taxonomy.dto.TaxonDTO;
import es.redmic.models.es.administrative.taxonomy.model.Taxon;

@Configuration
public class SubphylumJobConfig extends JobIndexingConfig<Subfilum, TaxonDTO, Taxon> {

	private static final String JOB_NAME = TaxonomyJobName.SUBPHYLUM.toString();

	@Autowired
	SubfilumRepository repository;

	@Autowired
	SubfilumESService service;

	public SubphylumJobConfig() {
		super(JOB_NAME);
	}

	@Bean
	public Job subphylumJob() {

		return createJobIndexing().start(subphylumStep()).build();
	}

	@Bean
	public Step subphylumStep() {

		return createStepIndexing(subphylumItemReader(), subphylumProccessor(), subphylumItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Subfilum> subphylumItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public ItemWriterBySave<Taxon> subphylumItemWriter() {

		return createItemWriter(service);
	}

	@Bean
	public SubphylumProcessor subphylumProccessor() {

		return new SubphylumProcessor();
	}
}
