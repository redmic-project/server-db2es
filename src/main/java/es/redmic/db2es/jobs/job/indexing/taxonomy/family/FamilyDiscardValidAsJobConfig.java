package es.redmic.db2es.jobs.job.indexing.taxonomy.family;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.administrative.taxonomy.model.Family;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.taxonomy.TaxonomyJobName;
import es.redmic.models.es.administrative.taxonomy.model.Taxon;

@Configuration
public class FamilyDiscardValidAsJobConfig extends FamilyCommonJobConfig {

	private static final String JOB_NAME = TaxonomyJobName.FAMILY_DISCARD_VALID_AS.toString();

	public FamilyDiscardValidAsJobConfig() {
		super(JOB_NAME);
	}

	@Bean
	public Job familyDiscardValidAsJob() {
		
		return createJobIndexing().start(familyDiscardValidAsStep()).build();
	}

	@Bean
	public Step familyDiscardValidAsStep() {
		
		return createStepIndexing(familyDiscardValidAsItemReader(), familyDiscardValidAsProccessor(),
				familyDiscardValidAsItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Family> familyDiscardValidAsItemReader() {
		
		return createItemReader(repository);
	}

	@Bean
	public ItemWriterBySave<Taxon> familyDiscardValidAsItemWriter() {
		
		return createItemWriter(service);
	}

	@Bean
	public FamilyDiscardValidAsProcessor familyDiscardValidAsProccessor() {
		
		return new FamilyDiscardValidAsProcessor();
	}
}
