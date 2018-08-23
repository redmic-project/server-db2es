package es.redmic.db2es.jobs.job.indexing.maintenance.administrative.documenttype;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.administrative.model.DocumentType;
import es.redmic.db.maintenance.administrative.repository.DocumentTypeRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.administrative.AdministrativeDomainJobName;
import es.redmic.es.maintenance.domain.administrative.service.DocumentTypeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.administrative.dto.DocumentTypeDTO;

@Configuration
public class DocumentTypeJobConfig extends JobIndexingConfig<DocumentType, DocumentTypeDTO, DomainES> {

	private static final String JOB_NAME = AdministrativeDomainJobName.DOCUMENT_TYPE.toString();

	@Autowired
	DocumentTypeRepository repository;

	@Autowired
	DocumentTypeESService service;

	public DocumentTypeJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job documentTypeJob() {

		return createJobIndexing().start(documentTypeStep()).build();
	}

	@Bean
	@StepScope
	public Step documentTypeStep() {

		return createStepIndexing(documentTypeItemReader(), documentTypeProccessor(), documentTypeItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<DocumentType> documentTypeItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public DocumentTypeProcessor documentTypeProccessor() {

		return new DocumentTypeProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> documentTypeItemWriter() {

		return createItemWriter(service);
	}
}
