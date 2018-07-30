package es.redmic.db2es.jobs.job.indexing.taxonomy.genus;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.administrative.taxonomy.model.Genus;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.taxonomy.TaxonomyJobName;
import es.redmic.models.es.administrative.taxonomy.model.Taxon;

@Configuration
public class GenusDiscardValidAsJobConfig extends GenusCommonJobConfig {

	private static final String JOB_NAME = TaxonomyJobName.GENUS_DISCARD_VALID_AS
			.toString();

	public GenusDiscardValidAsJobConfig() {
		super(JOB_NAME);
	}

	@Bean
	public Job genusDiscardValidAsJob() {

		return createJobIndexing().start(genusDiscardValidAsStep()).build();
	}

	@Bean
	public Step genusDiscardValidAsStep() {

		return createStepIndexing(genusDiscardValidAsItemReader(),
				genusDiscardValidAsProccessor(), genusItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Genus> genusDiscardValidAsItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public GenusDiscardValidAsProcessor genusDiscardValidAsProccessor() {

		return new GenusDiscardValidAsProcessor();
	}
	
	@Bean
	public ItemWriterBySave<Taxon> genusItemWriter() {
		
		return createItemWriter(service);
	}
}
