package es.redmic.db2es.jobs.job.indexing.geodata.isolines;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.geodata.isolines.model.Isolines;
import es.redmic.db.geodata.isolines.repository.IsolinesRepository;
import es.redmic.db2es.config.ColumnRangePartitioner;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.geo.ItemGeoWriterBase;
import es.redmic.db2es.jobs.job.indexing.JobsNames;
import es.redmic.db2es.jobs.job.indexing.common.JobGeoIndexingConfig;
import es.redmic.es.geodata.isolines.service.IsolinesESService;
import es.redmic.models.es.geojson.common.model.GeoMultiLineStringData;
import es.redmic.models.es.geojson.isolines.dto.IsolinesDTO;

@Configuration
public class IsolinesJobConfig
		extends JobGeoIndexingConfig<Isolines, IsolinesDTO, GeoMultiLineStringData> {

	private static final String JOB_NAME = JobsNames.ISOLINES.toString(),
			TABLE_DATA_SOURCE = "isolines";

	@Autowired
	IsolinesRepository repository;

	@Autowired
	IsolinesESService service;

	public IsolinesJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job isolinesJob() {
		return createJobIndexing(JOB_NAME, isolinesPartitionStep());
	}

	@Bean
	public Step isolinesPartitionStep() {

		return createPartitionStep(JOB_NAME, isolinesPartitioner(), isolinesStep());
	}

	@Bean
	public Step isolinesStep() {
		return createStepIndexing(isolinesItemReader(null, null), isolinesProccessor(),
				isolinesItemWriter());
	}

	@Bean
	public ColumnRangePartitioner isolinesPartitioner() {

		return createPartitioner(TABLE_DATA_SOURCE);
	}

	@Bean
	@StepScope
	public ItemReaderBase<Isolines> isolinesItemReader(
			@Value("#{stepExecutionContext[start]}") Long start,
			@Value("#{stepExecutionContext[end]}") Long end) {

		return createItemReader(repository, start, end);
	}

	@Bean
	public ItemGeoWriterBase<GeoMultiLineStringData> isolinesItemWriter() {

		return createItemWriter(service);
	}

	@Bean
	public IsolinesProcessor isolinesProccessor() {

		return new IsolinesProcessor();
	}
}
