package es.redmic.db2es.jobs.job.indexing.taxonomy.family;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.administrative.taxonomy.model.Family;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterByUpdate;
import es.redmic.db2es.jobs.job.indexing.taxonomy.TaxonomyJobName;
import es.redmic.models.es.administrative.taxonomy.model.Taxon;

@Configuration
public class FamilyWithValidAsJobConfig extends FamilyCommonJobConfig {

	private static final String JOB_NAME = TaxonomyJobName.FAMILY_WITH_VALID_AS.toString();

	public FamilyWithValidAsJobConfig() {
		super(JOB_NAME);
	}

	@Bean
	public Job familyWithValidAsJob() {

		return createJobIndexing().start(familyWithValidAsStep()).build();
	}

	@Bean
	public Step familyWithValidAsStep() {

		return createStepIndexing(familyWithValidAsItemReader(), familyWithValidAsProccessor(),
				familyWithValidAsItemByUpdate());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Family> familyWithValidAsItemReader() {

		ItemReaderBase<Family> itemReader = createItemReader(repository);
		itemReader.setMethodName("findFamilyWithValidAs");
		
		return createItemReader(repository);
	}

	@Bean
	public ItemWriterByUpdate<Taxon> familyWithValidAsItemByUpdate() {

		return new ItemWriterByUpdate<Taxon>(service);
	}

	@Bean
	public FamilyWithValidAsProcessor familyWithValidAsProccessor() {

		return new FamilyWithValidAsProcessor();
	}
}
