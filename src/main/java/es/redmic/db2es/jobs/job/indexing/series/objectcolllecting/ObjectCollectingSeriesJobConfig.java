package es.redmic.db2es.jobs.job.indexing.series.objectcolllecting;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.series.objectcollecting.model.ObjectCollecting;
import es.redmic.db.series.objectcollecting.repository.ObjectCollectingRepository;
import es.redmic.db2es.config.ColumnRangePartitioner;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.series.ItemSeriesWriterBase;
import es.redmic.db2es.jobs.job.indexing.JobsNames;
import es.redmic.db2es.jobs.job.indexing.common.JobSeriesIndexingConfig;
import es.redmic.es.series.objectcollecting.service.ObjectCollectingSeriesESService;
import es.redmic.models.es.series.objectcollecting.dto.ObjectCollectingSeriesDTO;
import es.redmic.models.es.series.objectcollecting.model.ObjectCollectingSeries;

@Configuration
public class ObjectCollectingSeriesJobConfig
		extends JobSeriesIndexingConfig<ObjectCollecting, ObjectCollectingSeriesDTO, ObjectCollectingSeries> {

	private static final String JOB_NAME = JobsNames.OBJECT_COLLECTINGSERIES.toString(),
			TABLE_DATA_SOURCE = "objectcollecting";

	@Autowired
	ObjectCollectingRepository repository;

	@Autowired
	ObjectCollectingSeriesESService service;

	public ObjectCollectingSeriesJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job objectCollectingSeriesJob() {

		return createJobIndexing(JOB_NAME, objectCollectingPartitionStep());
	}

	@Bean
	public Step objectCollectingPartitionStep() {

		return createPartitionStep(JOB_NAME, objectCollectingSeriesProcessor(), objectSeriesStep());
	}

	@Bean
	public Step objectSeriesStep() {

		return createStepIndexing(objectCollectingSeriesItemReader(null, null), objectCollectingSeriesProccessor(),
				objectCollectingSeriesItemWriter());
	}

	@Bean
	public ColumnRangePartitioner objectCollectingSeriesProcessor() {

		return createPartitioner(TABLE_DATA_SOURCE);
	}

	@Bean
	@StepScope
	public ItemReaderBase<ObjectCollecting> objectCollectingSeriesItemReader(
			@Value("#{stepExecutionContext[start]}") Long start, @Value("#{stepExecutionContext[end]}") Long end) {

		return createItemReader(repository, start, end);
	}

	@Bean
	public ObjectCollectingSeriesProcessor objectCollectingSeriesProccessor() {

		return new ObjectCollectingSeriesProcessor();
	}

	@Bean
	public ItemSeriesWriterBase<es.redmic.models.es.series.objectcollecting.model.ObjectCollectingSeries> objectCollectingSeriesItemWriter() {

		return createItemWriter(service);
	}
}
