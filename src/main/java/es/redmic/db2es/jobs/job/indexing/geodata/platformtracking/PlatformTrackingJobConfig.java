package es.redmic.db2es.jobs.job.indexing.geodata.platformtracking;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.geodata.tracking.platform.model.PlatformTracking;
import es.redmic.db.geodata.tracking.platform.repository.PlatformTrackingRepository;
import es.redmic.db2es.config.ColumnRangePartitioner;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.geo.ItemGeoWriterBase;
import es.redmic.db2es.jobs.job.indexing.JobsNames;
import es.redmic.db2es.jobs.job.indexing.common.JobGeoIndexingConfig;
import es.redmic.es.geodata.tracking.platform.service.PlatformTrackingESService;
import es.redmic.models.es.geojson.common.model.GeoPointData;
import es.redmic.models.es.geojson.tracking.platform.dto.PlatformTrackingDTO;

@Configuration
public class PlatformTrackingJobConfig
		extends JobGeoIndexingConfig<PlatformTracking, PlatformTrackingDTO, GeoPointData> {

	private static final String JOB_NAME = JobsNames.PLATFORM_TRACKING.toString(),
			TABLE_DATA_SOURCE = "platformtracking";

	@Autowired
	PlatformTrackingRepository repository;

	@Autowired
	PlatformTrackingESService service;

	public PlatformTrackingJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job platformTrackingJob() {

		return createJobIndexing(JOB_NAME, platformTrackingPartitionStep());
	}

	@Bean
	public Step platformTrackingPartitionStep() {

		return createPartitionStep(JOB_NAME, platformTrackingPartitioner(), platformTrackingStep());
	}

	@Bean
	public Step platformTrackingStep() {

		return createStepIndexing(platformTrackingItemReader(null, null), platformTrackingProccessor(),
				platformTrackingItemWriter());
	}

	@Bean
	public ColumnRangePartitioner platformTrackingPartitioner() {

		return createPartitioner(TABLE_DATA_SOURCE);
	}

	@Bean
	@StepScope
	public ItemReaderBase<PlatformTracking> platformTrackingItemReader(
			@Value("#{stepExecutionContext[start]}") Long start, @Value("#{stepExecutionContext[end]}") Long end) {

		return createItemReader(repository, start, end);
	}

	@Bean
	public ItemGeoWriterBase<GeoPointData> platformTrackingItemWriter() {

		return createItemWriter(service);
	}

	@Bean
	public PlatformTrackingProcessor platformTrackingProccessor() {

		return new PlatformTrackingProcessor();
	}
}
