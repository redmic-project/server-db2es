package es.redmic.db2es.jobs.job.indexing.geodata.survey.timeseries;

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
import es.redmic.es.geodata.geofixedstation.service.GeoFixedTimeSeriesESService;
import es.redmic.models.es.geojson.common.model.GeoPointData;
import es.redmic.models.es.geojson.geofixedstation.dto.GeoFixedTimeSeriesDTO;

@Configuration
public class FixedSurveyJobConfig extends JobGeoIndexingConfig<FixedSurvey, GeoFixedTimeSeriesDTO, GeoPointData> {

	private static final String JOB_NAME = JobsNames.TIMESERIES_SURVEY.toString(), TABLE_DATA_SOURCE = "fixedsurvey",
			WHERE_CLAUSE = "prefixtype LIKE 'ft'";

	@Autowired
	FixedSurveyRepository repository;

	@Autowired
	GeoFixedTimeSeriesESService service;

	public FixedSurveyJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job timeseriesSurveyJob() {

		return createJobIndexing(JOB_NAME, timeseriesSurveyPartitionStep());
	}

	@Bean
	public Step timeseriesSurveyPartitionStep() {

		return createPartitionStep(JOB_NAME, timeseriesSurveyPartitioner(), timeseriesSurveyStep());
	}

	@Bean
	public Step timeseriesSurveyStep() {
		return createStepIndexing(timeseriesSurveyItemReader(null, null), timeseriesSurveyProccessor(),
				timeseriesSurveyItemWriter());
	}

	@Bean
	public ColumnRangePartitioner timeseriesSurveyPartitioner() {

		return createPartitioner(TABLE_DATA_SOURCE, WHERE_CLAUSE);
	}

	@Bean
	@StepScope
	public ItemReaderBase<FixedSurvey> timeseriesSurveyItemReader(@Value("#{stepExecutionContext[start]}") Long start,
			@Value("#{stepExecutionContext[end]}") Long end) {

		ItemReaderBase<FixedSurvey> itemReader = createItemReader(repository, start, end);
		itemReader.setMethodName("findTimeSeriesSurvey");

		return itemReader;
	}

	@Bean
	public FixedSurveyProcessor timeseriesSurveyProccessor() {

		return new FixedSurveyProcessor();
	}

	@Bean
	public ItemGeoWriterBase<GeoPointData> timeseriesSurveyItemWriter() {

		return createItemWriter(service);
	}
}
