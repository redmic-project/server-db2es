package es.redmic.db2es.jobs.job.indexing.series.infrastructureattributes;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.series.infrastructureattributes.model.InfrastructureAttributes;
import es.redmic.db.series.infrastructureattributes.repository.InfrastructureAttributesRepository;
import es.redmic.db2es.config.ColumnRangePartitioner;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.series.ItemSeriesWriterBase;
import es.redmic.db2es.jobs.job.indexing.JobsNames;
import es.redmic.db2es.jobs.job.indexing.common.JobSeriesIndexingConfig;
import es.redmic.es.series.attributeseries.service.AttributeSeriesESService;
import es.redmic.models.es.series.attributeseries.dto.AttributeSeriesDTO;
import es.redmic.models.es.series.attributeseries.model.AttributeSeries;

@Configuration
public class InfrastructureAttributeSeriesJobConfig
		extends JobSeriesIndexingConfig<InfrastructureAttributes, AttributeSeriesDTO, AttributeSeries> {

	private static final String JOB_NAME = JobsNames.ATTRIBUTESERIES.toString(),
			TABLE_DATA_SOURCE = "infrastructureattributes";

	@Autowired
	InfrastructureAttributesRepository repository;

	@Autowired
	AttributeSeriesESService service;

	public InfrastructureAttributeSeriesJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job infrastructureAttributeSeriesJob() {

		return createJobIndexing(JOB_NAME, infrastructureAttributeSeriesPartitionStep());
	}

	@Bean
	public Step infrastructureAttributeSeriesPartitionStep() {

		return createPartitionStep(JOB_NAME, infrastructureAttributeSeriesProcessor(),
				infrastructureAttributeSeriesStep());
	}

	@Bean
	public Step infrastructureAttributeSeriesStep() {

		return createStepIndexing(infrastructureAttributeSeriesItemReader(null, null),
				infrastructureAttributeSeriesProccessor(), infrastructureAttributeSeriesItemWriter());
	}

	@Bean
	public ColumnRangePartitioner infrastructureAttributeSeriesProcessor() {

		return createPartitioner(TABLE_DATA_SOURCE);
	}

	@Bean
	@StepScope
	public ItemReaderBase<InfrastructureAttributes> infrastructureAttributeSeriesItemReader(
			@Value("#{stepExecutionContext[start]}") Long start, @Value("#{stepExecutionContext[end]}") Long end) {

		return createItemReader(repository, start, end);
	}

	@Bean
	public InfrastructureAttributeSeriesProcessor infrastructureAttributeSeriesProccessor() {

		return new InfrastructureAttributeSeriesProcessor();
	}

	@Bean
	public ItemSeriesWriterBase<AttributeSeries> infrastructureAttributeSeriesItemWriter() {

		return createItemWriter(service);
	}
}
