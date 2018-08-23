package es.redmic.db2es.jobs.job.indexing.maintenance.classifications.linetype;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.line.model.LineType;
import es.redmic.db.maintenance.line.repository.LineTypeRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.classifications.ClassificationsDomainJobName;
import es.redmic.es.maintenance.line.service.LineTypeESService;
import es.redmic.models.es.maintenance.line.dto.LineTypeDTO;

@Configuration
public class LineTypeJobConfig
		extends JobIndexingConfig<LineType, LineTypeDTO, es.redmic.models.es.maintenance.line.model.LineType> {

	private static final String JOB_NAME = ClassificationsDomainJobName.LINE_TYPE.toString();

	@Autowired
	LineTypeRepository repository;
	
	@Autowired
	LineTypeESService service;
	
	public LineTypeJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job lineTypeJob() {
		
		return createJobIndexing().start(getLineTypeStep()).build();
	}

	@Bean
	public Step getLineTypeStep() {
		
		return createStepIndexing(lineTypeItemReader(), lineTypeProccessor(), lineTypeItemWriter());
	}
	
	@Bean
	@StepScope
	public ItemReaderBase<LineType> lineTypeItemReader() {
		
		return createItemReader(repository);
	}
	
	@Bean
	public LineTypeProcessor lineTypeProccessor() {
		
		return new LineTypeProcessor();
	}

	@Bean
	public ItemWriterBySave<es.redmic.models.es.maintenance.line.model.LineType> lineTypeItemWriter() {
		
		return createItemWriter(service);
	}
}
