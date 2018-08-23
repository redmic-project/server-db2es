package es.redmic.db2es.jobs.job.indexing.taxonomy.kingdom;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.administrative.taxonomy.model.Kingdom;
import es.redmic.db.administrative.taxonomy.repository.KingdomRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.taxonomy.TaxonomyJobName;
import es.redmic.es.administrative.taxonomy.service.KingdomESService;
import es.redmic.models.es.administrative.taxonomy.dto.KingdomDTO;
import es.redmic.models.es.administrative.taxonomy.model.Taxon;

@Configuration
public class KingdomJobConfig extends JobIndexingConfig<Kingdom, KingdomDTO, Taxon> {

	private static final String JOB_NAME = TaxonomyJobName.KINGDOM.toString();

	@Autowired
	KingdomRepository repository;

	@Autowired
	KingdomESService service;

	public KingdomJobConfig() {
		super(JOB_NAME);
	}

	@Bean
	public Job kingdomJob() {

		return createJobIndexing().start(kingdomStep()).build();
	}

	@Bean
	public Step kingdomStep() {

		return createStepIndexing(kingdomItemReader(), kingdomProccessor(), kingdomItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Kingdom> kingdomItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public ItemWriterBySave<Taxon> kingdomItemWriter() {

		return createItemWriter(service);
	}

	@Bean
	public KingdomProcessor kingdomProccessor() {

		return new KingdomProcessor();
	}
}
