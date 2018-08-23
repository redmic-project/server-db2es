package es.redmic.db2es.jobs.job.indexing.administrative.contact;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.administrative.model.Contact;
import es.redmic.db.administrative.repository.ContactRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.administrative.AdministrativeJobName;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.es.administrative.service.ContactESService;
import es.redmic.models.es.administrative.dto.ContactDTO;

@Configuration
public class ContactJobConfig
		extends JobIndexingConfig<Contact, ContactDTO, es.redmic.models.es.administrative.model.Contact> {

	private static final String JOB_NAME = AdministrativeJobName.CONTACT.toString();

	@Autowired
	ContactRepository repository;

	@Autowired
	ContactESService service;

	public ContactJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job contactJob() {
		
		return createJobIndexing().start(contactStep()).build();
	}

	@Bean
	public Step contactStep() {
		
		return createStepIndexing(contactItemReader(), contactProccessor(), contactItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Contact> contactItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public ContactProcessor contactProccessor() {

		return new ContactProcessor();
	}

	@Bean
	public ItemWriterBySave<es.redmic.models.es.administrative.model.Contact> contactItemWriter() {

		return createItemWriter(service);
	}
}
