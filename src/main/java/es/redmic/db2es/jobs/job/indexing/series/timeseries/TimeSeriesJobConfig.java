package es.redmic.db2es.jobs.job.indexing.series.timeseries;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.series.timeseries.model.TimeSeries;
import es.redmic.db.series.timeseries.repository.TimeSeriesRepository;
import es.redmic.db2es.config.ColumnRangePartitioner;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.series.ItemSeriesWriterBase;
import es.redmic.db2es.jobs.job.indexing.JobsNames;
import es.redmic.db2es.jobs.job.indexing.common.JobSeriesIndexingConfig;
import es.redmic.es.series.timeseries.service.TimeSeriesESService;
import es.redmic.models.es.series.timeseries.dto.TimeSeriesDTO;

@Configuration
public class TimeSeriesJobConfig extends
		JobSeriesIndexingConfig<TimeSeries, TimeSeriesDTO, es.redmic.models.es.series.timeseries.model.TimeSeries> {

	private static final String JOB_NAME = JobsNames.TIMESERIES.toString(), TABLE_DATA_SOURCE = "timeseries";

	@Autowired
	TimeSeriesRepository repository;

	@Autowired
	TimeSeriesESService service;

	public TimeSeriesJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job timeSeriesJob() {

		return createJobIndexing(JOB_NAME, timeSeriesPartitionStep());
	}

	@Bean
	public Step timeSeriesPartitionStep() {

		return createPartitionStep(JOB_NAME, timeSeriesPartitioner(), timeSeriesStep());
	}

	@Bean
	public Step timeSeriesStep() {

		return createStepIndexing(timeSeriesItemReader(null, null), timeSeriesProccessor(), timeSeriesItemWriter());
	}

	@Bean
	public ColumnRangePartitioner timeSeriesPartitioner() {

		return createPartitioner(TABLE_DATA_SOURCE);
	}

	@Bean
	@StepScope
	public ItemReaderBase<TimeSeries> timeSeriesItemReader(@Value("#{stepExecutionContext[start]}") Long start,
			@Value("#{stepExecutionContext[end]}") Long end) {

		return createItemReader(repository, start, end);
	}

	@Bean
	public ItemSeriesWriterBase<es.redmic.models.es.series.timeseries.model.TimeSeries> timeSeriesItemWriter() {

		return createItemWriter(service);
	}

	@Bean
	public TimeSeriesProcessor timeSeriesProccessor() {

		return new TimeSeriesProcessor();
	}
}
