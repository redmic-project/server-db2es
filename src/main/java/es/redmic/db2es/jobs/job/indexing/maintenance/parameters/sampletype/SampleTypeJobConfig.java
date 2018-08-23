package es.redmic.db2es.jobs.job.indexing.maintenance.parameters.sampletype;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.samples.model.SampleType;
import es.redmic.db.maintenance.samples.repository.SampleTypeRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.parameters.ParametersDomainJobName;
import es.redmic.es.maintenance.samples.service.SampleTypeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.samples.dto.SampleTypeDTO;

@Configuration
public class SampleTypeJobConfig extends JobIndexingConfig<SampleType, SampleTypeDTO, DomainES> {

	private static final String JOB_NAME = ParametersDomainJobName.SAMPLE_TYPE.toString();

	@Autowired
	SampleTypeRepository repository;
	
	@Autowired
	SampleTypeESService service;
	
	public SampleTypeJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job sampleTypeJob() {
		
		return createJobIndexing().start(getSampleTypeStep()).build();
	}

	@Bean
	@StepScope
	public Step getSampleTypeStep() {
		
		return createStepIndexing(sampleTypeItemReader(), sampleTypeProccessor(), sampleTypeItemWriter());
	}
	
	@Bean
	@StepScope
	public ItemReaderBase<SampleType> sampleTypeItemReader() {
		
		return createItemReader(repository);
	}
	
	@Bean
	public SampleTypeProcessor sampleTypeProccessor() {
		
		return new SampleTypeProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> sampleTypeItemWriter() {
		
		return createItemWriter(service);
	}
}
