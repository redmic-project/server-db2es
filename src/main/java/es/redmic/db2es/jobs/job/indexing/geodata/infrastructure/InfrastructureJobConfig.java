package es.redmic.db2es.jobs.job.indexing.geodata.infrastructure;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.geodata.infrastructure.model.Infrastructure;
import es.redmic.db.geodata.infrastructure.repository.InfrastructureRepository;
import es.redmic.db2es.config.ColumnRangePartitioner;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.geo.ItemGeoWriterBase;
import es.redmic.db2es.jobs.job.indexing.JobsNames;
import es.redmic.db2es.jobs.job.indexing.common.JobGeoIndexingConfig;
import es.redmic.es.geodata.infrastructure.service.InfrastructureESService;
import es.redmic.models.es.geojson.common.model.GeoPointData;
import es.redmic.models.es.geojson.infrastructure.dto.InfrastructureDTO;

@Configuration
public class InfrastructureJobConfig extends JobGeoIndexingConfig<Infrastructure, InfrastructureDTO, GeoPointData> {

	private static final String JOB_NAME = JobsNames.INFRASTRUCTURE.toString(),
			TABLE_DATA_SOURCE = "infrastructurepoint";

	@Autowired
	InfrastructureRepository repository;

	@Autowired
	InfrastructureESService service;

	public InfrastructureJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job infrastructureJob() {

		return createJobIndexing(JOB_NAME, infrastructurePartitionStep());
	}

	@Bean
	public Step infrastructurePartitionStep() {

		return createPartitionStep(JOB_NAME, infrastructurePartitioner(), infrastructureStep());
	}

	@Bean
	public Step infrastructureStep() {

		return createStepIndexing(infrastructureItemReader(null, null), infrastructureProccessor(), infrastructureItemWriter());
	}

	@Bean
	public ColumnRangePartitioner infrastructurePartitioner() {

		return createPartitioner(TABLE_DATA_SOURCE);
	}

	@Bean
	@StepScope
	public ItemReaderBase<Infrastructure> infrastructureItemReader(@Value("#{stepExecutionContext[start]}") Long start,
			@Value("#{stepExecutionContext[end]}") Long end) {

		return createItemReader(repository, start, end);
	}

	@Bean
	public ItemGeoWriterBase<GeoPointData> infrastructureItemWriter() {

		return createItemWriter(service);
	}

	@Bean
	public InfrastructureProcessor infrastructureProccessor() {

		return new InfrastructureProcessor();
	}
}
