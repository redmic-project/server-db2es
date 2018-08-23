package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.taxon.rank;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.taxonomy.model.Rank;
import es.redmic.db.maintenance.taxonomy.repository.RankRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.TaxonomyDomainJobName;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.TaxonRankESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.taxonomy.dto.RankDTO;

@Configuration
public class RankJobConfig extends JobIndexingConfig<Rank, RankDTO, DomainES> {

	private static final String JOB_NAME = TaxonomyDomainJobName.RANK.toString();

	@Autowired
	RankRepository repository;
	
	@Autowired
	TaxonRankESService service;
	
	public RankJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job rankJob() {
		
		return createJobIndexing().start(getRankStep()).build();
	}

	@Bean
	@StepScope
	public Step getRankStep() {
		
		return createStepIndexing(rankItemReader(), rankProccessor(), rankItemWriter());
	}
	
	@Bean
	@StepScope
	public ItemReaderBase<Rank> rankItemReader() {
		
		return createItemReader(repository);
	}
	
	@Bean
	public RankProcessor rankProccessor() {
		
		return new RankProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> rankItemWriter() {
		
		return createItemWriter(service);
	}
}
