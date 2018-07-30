package es.redmic.db2es.jobs.job.indexing.taxonomy.animal;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.administrative.taxonomy.model.Animal;
import es.redmic.db.administrative.taxonomy.repository.AnimalRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.taxonomy.TaxonomyJobName;
import es.redmic.es.administrative.taxonomy.service.AnimalESService;
import es.redmic.models.es.administrative.taxonomy.dto.AnimalDTO;

@Configuration
public class AnimalJobConfig
		extends JobIndexingConfig<Animal, AnimalDTO, es.redmic.models.es.administrative.taxonomy.model.Animal> {

	private static final String JOB_NAME = TaxonomyJobName.ANIMAL.toString();

	@Autowired
	AnimalRepository repository;

	@Autowired
	AnimalESService service;

	public AnimalJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job animalJob() {

		return createJobIndexing().start(animalStep()).build();
	}

	@Bean
	public Step animalStep() {

		return createStepIndexing(animalItemReader(), animalProccessor(), animalItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Animal> animalItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public AnimalProcessor animalProccessor() {

		return new AnimalProcessor();
	}

	@Bean
	public ItemWriterBySave<es.redmic.models.es.administrative.taxonomy.model.Animal> animalItemWriter() {

		return createItemWriter(service);
	}
}
