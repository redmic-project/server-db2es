package es.redmic.db2es.jobs.job.indexing.taxonomy.classs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.administrative.taxonomy.model.Classs;
import es.redmic.db.administrative.taxonomy.repository.ClassRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.taxonomy.TaxonomyJobName;
import es.redmic.es.administrative.taxonomy.service.ClassESService;
import es.redmic.models.es.administrative.taxonomy.dto.TaxonDTO;
import es.redmic.models.es.administrative.taxonomy.model.Taxon;

@Configuration
public class ClassJobConfig extends
		JobIndexingConfig<Classs, TaxonDTO, Taxon> {

	private static final String JOB_NAME = TaxonomyJobName.CLASSS.toString();

	@Autowired
	ClassRepository repository;

	@Autowired
	ClassESService service;

	public ClassJobConfig() {
		super(JOB_NAME);
	}

	@Bean
	public Job classsJob() {
		
		return createJobIndexing().start(classsStep()).build();
	}

	@Bean
	public Step classsStep() {
		
		return createStepIndexing(classsItemReader(), classsProccessor(),
				classsItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Classs> classsItemReader() {
		
		return createItemReader(repository);
	}

	@Bean
	public ItemWriterBySave<Taxon> classsItemWriter() {
		
		return createItemWriter(service);
	}

	@Bean
	public ClassProcessor classsProccessor() {
		
		return new ClassProcessor();
	}
}
