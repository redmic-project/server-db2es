package es.redmic.db2es.jobs.job.indexing.document;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.administrative.model.Document;
import es.redmic.db.administrative.repository.DocumentRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.JobsNames;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.es.administrative.service.DocumentESService;
import es.redmic.models.es.administrative.dto.DocumentDTO;

@Configuration
public class DocumentJobConfig extends
		JobIndexingConfig<Document, DocumentDTO, es.redmic.models.es.administrative.model.Document> {

	private static final String JOB_NAME = JobsNames.DOCUMENT.toString();

	@Autowired
	DocumentRepository repository;

	@Autowired
	DocumentESService service;

	public DocumentJobConfig() {
		super(JOB_NAME);
	}

	@Bean
	public Job documentJob() {
		
		return createJobIndexing().start(documentStep()).build();
	}

	@Bean
	public Step documentStep() {
		
		return createStepIndexing(documentItemReader(), documentProccessor(),
				documentItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Document> documentItemReader() {
		
		return createItemReader(repository);
	}
	
	@Bean
	public DocumentProcessor documentProccessor() {
		
		return new DocumentProcessor();
	}

	@Bean
	public ItemWriterBySave<es.redmic.models.es.administrative.model.Document> documentItemWriter() {
		
		return createItemWriter(service);
	}
}
