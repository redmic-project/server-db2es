package es.redmic.db2es.jobs.job.indexing.taxonomy.species;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.administrative.taxonomy.model.AbstractSpecies;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.taxonomy.TaxonomyJobName;

@Configuration
public class SpeciesDiscardValidAsJobConfig extends SpeciesCommonJobConfig {

	private static final String JOB_NAME = TaxonomyJobName.SPECIES_DISCARD_VALIDAS
			.toString();

	public SpeciesDiscardValidAsJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job speciesDiscardValidAsJob() {

		return createJobIndexing().start(speciesDiscardValidAsStep()).build();
	}

	@Bean
	public Step speciesDiscardValidAsStep() {

		return createStepIndexing(speciesDiscardValidAsItemReader(),
				speciesDiscardValidAsProccessor(), speciesItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<AbstractSpecies> speciesDiscardValidAsItemReader() {

		ItemReaderBase<AbstractSpecies> itemReader = createItemReader(repository);

		itemReader.setMethodName("findSpecies");

		return itemReader;
	}

	@Bean
	public SpeciesDiscardValidAsProcessor speciesDiscardValidAsProccessor() {

		return new SpeciesDiscardValidAsProcessor();
	}
	
	@Bean
	public ItemWriterBySave<es.redmic.models.es.administrative.taxonomy.model.Species> 
			speciesItemWriter() {

		return createItemWriter(service);
	}

}
