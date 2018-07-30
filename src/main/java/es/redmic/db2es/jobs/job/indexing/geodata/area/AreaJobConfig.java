package es.redmic.db2es.jobs.job.indexing.geodata.area;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.geodata.area.model.Area;
import es.redmic.db.geodata.area.repository.AreaRepository;
import es.redmic.db2es.config.ColumnRangePartitioner;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.geo.ItemGeoWriterBase;
import es.redmic.db2es.jobs.job.indexing.JobsNames;
import es.redmic.db2es.jobs.job.indexing.common.JobGeoIndexingConfig;
import es.redmic.es.geodata.area.service.AreaESService;
import es.redmic.models.es.geojson.area.dto.AreaDTO;
import es.redmic.models.es.geojson.common.model.GeoMultiPolygonData;

@Configuration
public class AreaJobConfig extends JobGeoIndexingConfig<Area, AreaDTO, GeoMultiPolygonData> {

	private static final String JOB_NAME = JobsNames.AREA.toString(), TABLE_DATA_SOURCE = "area";

	@Autowired
	AreaRepository repository;

	@Autowired
	AreaESService service;

	public AreaJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job areaJob() {

		return createJobIndexing(JOB_NAME, areaPartitionStep());
	}

	@Bean
	public Step areaPartitionStep() {

		return createPartitionStep(JOB_NAME, areaPartitioner(), areaStep());
	}

	@Bean
	public Step areaStep() {

		return createStepIndexing(areaItemReader(null, null), areaProccessor(), areaItemWriter());
	}

	@Bean
	public ColumnRangePartitioner areaPartitioner() {

		return createPartitioner(TABLE_DATA_SOURCE);
	}

	@Bean
	@StepScope
	public ItemReaderBase<Area> areaItemReader(@Value("#{stepExecutionContext[start]}") Long start,
			@Value("#{stepExecutionContext[end]}") Long end) {

		return createItemReader(repository, start, end);
	}

	@Bean
	public ItemGeoWriterBase<GeoMultiPolygonData> areaItemWriter() {

		return createItemWriter(service);
	}

	@Bean
	public AreaProcessor areaProccessor() {

		return new AreaProcessor();
	}
}
