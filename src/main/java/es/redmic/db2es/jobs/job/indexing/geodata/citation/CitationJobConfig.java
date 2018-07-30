package es.redmic.db2es.jobs.job.indexing.geodata.citation;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.geodata.citation.model.Citation;
import es.redmic.db.geodata.citation.repository.CitationRepository;
import es.redmic.db2es.config.ColumnRangePartitioner;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.geo.ItemGeoWriterBase;
import es.redmic.db2es.jobs.job.indexing.JobsNames;
import es.redmic.db2es.jobs.job.indexing.common.JobGeoIndexingConfig;
import es.redmic.es.geodata.citation.service.CitationESService;
import es.redmic.models.es.geojson.citation.dto.CitationDTO;
import es.redmic.models.es.geojson.common.model.GeoPointData;

@Configuration
public class CitationJobConfig extends JobGeoIndexingConfig<Citation, CitationDTO, GeoPointData> {

	private static final String JOB_NAME = JobsNames.CITATION.toString(), TABLE_DATA_SOURCE = "citation";

	@Autowired
	CitationRepository repository;

	@Autowired
	CitationESService service;

	public CitationJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job citationJob() {

		return createJobIndexing(JOB_NAME, citationPartitionStep());
	}

	@Bean
	public Step citationPartitionStep() {

		return createPartitionStep(JOB_NAME, citationPartitioner(), citationStep());
	}

	@Bean
	public Step citationStep() {

		return createStepIndexing(citationItemReader(null, null), citationProccessor(), citationItemWriter());
	}

	@Bean
	public ColumnRangePartitioner citationPartitioner() {

		return createPartitioner(TABLE_DATA_SOURCE);
	}

	@Bean
	@StepScope
	public ItemReaderBase<Citation> citationItemReader(@Value("#{stepExecutionContext[start]}") Long start,
			@Value("#{stepExecutionContext[end]}") Long end) {

		return createItemReader(repository, start, end);
	}

	@Bean
	public ItemGeoWriterBase<GeoPointData> citationItemWriter() {

		return createItemWriter(service);
	}

	@Bean
	public CitationProcessor citationProccessor() {

		return new CitationProcessor();
	}
}
