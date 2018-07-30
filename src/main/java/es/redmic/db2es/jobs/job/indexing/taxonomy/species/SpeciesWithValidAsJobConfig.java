package es.redmic.db2es.jobs.job.indexing.taxonomy.species;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.administrative.taxonomy.model.AbstractSpecies;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterByUpdate;
import es.redmic.db2es.jobs.job.indexing.taxonomy.TaxonomyJobName;

@Configuration
public class SpeciesWithValidAsJobConfig extends SpeciesCommonJobConfig {

	private static final String JOB_NAME = TaxonomyJobName.SPECIES_WITH_VALID_AS
			.toString();


	public SpeciesWithValidAsJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job speciesWithValidAsJob() {

		return createJobIndexing().start(speciesWithValidAsStep()).build();
	}

	@Bean
	public Step speciesWithValidAsStep() {

		return createStepIndexing(speciesWithValidAsItemReader(),
				speciesWithValidAsProccessor(), speciesItemWriterByUpdate());
	}

	@Bean
	@StepScope
	public ItemReaderBase<AbstractSpecies> speciesWithValidAsItemReader() {

		ItemReaderBase<AbstractSpecies> itemReader = createItemReader(repository);
		itemReader.setMethodName("findSpeciesWithValidAs");

		return itemReader;
	}

	@Bean
	public SpeciesWithValidAsProcessor speciesWithValidAsProccessor() {

		return new SpeciesWithValidAsProcessor();
	}
	
	@Bean
	public ItemWriterByUpdate<es.redmic.models.es.administrative.taxonomy.model.Species> 
			speciesItemWriterByUpdate() {

		return new ItemWriterByUpdate<es.redmic.models.es.administrative.taxonomy.model.Species>(service);
	}

}
