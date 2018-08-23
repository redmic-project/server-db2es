package es.redmic.db2es.jobs.job.indexing.geodata.survey.objects;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.geodata.properties.fixedsurvey.model.FixedSurvey;
import es.redmic.db.geodata.properties.fixedsurvey.repository.FixedSurveyRepository;
import es.redmic.db2es.config.ColumnRangePartitioner;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.geo.ItemGeoWriterBase;
import es.redmic.db2es.jobs.job.indexing.JobsNames;
import es.redmic.db2es.jobs.job.indexing.common.JobGeoIndexingConfig;
import es.redmic.es.geodata.geofixedstation.service.GeoFixedObjectCollectingSeriesESService;
import es.redmic.models.es.geojson.common.model.GeoLineStringData;
import es.redmic.models.es.geojson.geofixedstation.dto.GeoFixedObjectCollectingSeriesDTO;

@Configuration
public class ObjectCollectingSurveyJobConfig
		extends JobGeoIndexingConfig<FixedSurvey, GeoFixedObjectCollectingSeriesDTO, GeoLineStringData> {

	private static final String JOB_NAME = JobsNames.OBJECT_COLLECTING_SURVEY.toString(),
			TABLE_DATA_SOURCE = "fixedsurvey", WHERE_CLAUSE = "prefixtype LIKE 'oc'";

	@Autowired
	FixedSurveyRepository repository;

	@Autowired
	GeoFixedObjectCollectingSeriesESService service;

	public ObjectCollectingSurveyJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job objectCollectingSurveyJob() {

		return createJobIndexing(JOB_NAME, objectCollectingSurveyPartitionStep());
	}

	@Bean
	public Step objectCollectingSurveyPartitionStep() {

		return createPartitionStep(JOB_NAME, objectCollectingSurveyPartitioner(), objectCollectingSurveyStep());
	}

	@Bean
	public Step objectCollectingSurveyStep() {

		return createStepIndexing(objectCollectingSurveyItemReader(null, null), objectCollectingSurveyProccessor(),
				objectCollectingSurveyItemWriter());
	}

	@Bean
	public ColumnRangePartitioner objectCollectingSurveyPartitioner() {

		return createPartitioner(TABLE_DATA_SOURCE, WHERE_CLAUSE);
	}

	@Bean
	@StepScope
	public ItemReaderBase<FixedSurvey> objectCollectingSurveyItemReader(
			@Value("#{stepExecutionContext[start]}") Long start, @Value("#{stepExecutionContext[end]}") Long end) {

		ItemReaderBase<FixedSurvey> itemReader = createItemReader(repository, start, end);
		itemReader.setMethodName("findObjectCollectingSeriesSurvey");

		return itemReader;
	}

	@Bean
	public ItemGeoWriterBase<GeoLineStringData> objectCollectingSurveyItemWriter() {

		return createItemWriter(service);
	}

	@Bean
	public ObjectCollectingSurveyProcessor objectCollectingSurveyProccessor() {

		return new ObjectCollectingSurveyProcessor();
	}
}
