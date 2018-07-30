package es.redmic.db2es.jobs.job.indexing.geodata.animaltracking;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.geodata.tracking.animal.model.AnimalTracking;
import es.redmic.db.geodata.tracking.animal.repository.AnimalTrackingRepository;
import es.redmic.db2es.config.ColumnRangePartitioner;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.geo.ItemGeoWriterBase;
import es.redmic.db2es.jobs.job.indexing.JobsNames;
import es.redmic.db2es.jobs.job.indexing.common.JobGeoIndexingConfig;
import es.redmic.es.geodata.tracking.animal.service.AnimalTrackingESService;
import es.redmic.models.es.geojson.common.model.GeoPointData;
import es.redmic.models.es.geojson.tracking.animal.dto.AnimalTrackingDTO;

@Configuration
public class AnimalTrackingJobConfig extends JobGeoIndexingConfig<AnimalTracking, AnimalTrackingDTO, GeoPointData> {

	private static final String JOB_NAME = JobsNames.ANIMAL_TRACKING.toString(),
			TABLE_DATA_SOURCE = "animaltracking";

	@Autowired
	AnimalTrackingRepository repository;

	@Autowired
	AnimalTrackingESService service;

	public AnimalTrackingJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job animalTrackingJob() {

		return createJobIndexing(JOB_NAME, animalTrackingPartitionStep());
	}

	@Bean
	public Step animalTrackingPartitionStep() {

		return createPartitionStep(JOB_NAME, animalTrackingPartitioner(), animalTrackingStep());
	}

	@Bean
	public Step animalTrackingStep() {
		return createStepIndexing(animalTrackingItemReader(null, null), animalTrackingProccessor(), animalTrackingItemWriter());
	}

	@Bean
	public ColumnRangePartitioner animalTrackingPartitioner() {

		return createPartitioner(TABLE_DATA_SOURCE);
	}

	@Bean
	@StepScope
	public ItemReaderBase<AnimalTracking> animalTrackingItemReader(@Value("#{stepExecutionContext[start]}") Long start,
			@Value("#{stepExecutionContext[end]}") Long end) {

		return createItemReader(repository, start, end);
	}

	@Bean
	public ItemGeoWriterBase<GeoPointData> animalTrackingItemWriter() {

		return createItemWriter(service);
	}

	@Bean
	public AnimalTrackingProcessor animalTrackingProccessor() {

		return new AnimalTrackingProcessor();
	}
}
