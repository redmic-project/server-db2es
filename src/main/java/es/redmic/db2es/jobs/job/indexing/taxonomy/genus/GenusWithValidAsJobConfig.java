package es.redmic.db2es.jobs.job.indexing.taxonomy.genus;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.administrative.taxonomy.model.Genus;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterByUpdate;
import es.redmic.db2es.jobs.job.indexing.taxonomy.TaxonomyJobName;
import es.redmic.models.es.administrative.taxonomy.model.Taxon;

@Configuration
public class GenusWithValidAsJobConfig extends GenusCommonJobConfig {

	private static final String JOB_NAME = TaxonomyJobName.GENUS_WITH_VALID_AS.toString();

	public GenusWithValidAsJobConfig() {
		super(JOB_NAME);
	}

	@Bean
	public Job genusWithValidAsJob() {

		return createJobIndexing().start(genusWithValidAsStep()).build();
	}

	@Bean
	public Step genusWithValidAsStep() {

		return createStepIndexing(genusWithValidAsItemReader(),
				genusWithValidAsProccessor(), genusItemWriterByUpdate());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Genus> genusWithValidAsItemReader() {

		ItemReaderBase<Genus> itemReader = createItemReader(repository);
		itemReader.setMethodName("findGenusWithValidAs");

		return itemReader;
	}

	@Bean
	public GenusWithValidAsProcessor genusWithValidAsProccessor() {

		return new GenusWithValidAsProcessor();
	}
	
	@Bean
	public ItemWriterByUpdate<Taxon> genusItemWriterByUpdate() {

		return new ItemWriterByUpdate<Taxon>(service);
	}
}
