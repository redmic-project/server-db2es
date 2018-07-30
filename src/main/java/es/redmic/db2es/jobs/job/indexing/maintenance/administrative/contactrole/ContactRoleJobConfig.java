package es.redmic.db2es.jobs.job.indexing.maintenance.administrative.contactrole;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.administrative.model.ContactRole;
import es.redmic.db.maintenance.administrative.repository.ContactRoleRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.administrative.AdministrativeDomainJobName;
import es.redmic.es.maintenance.domain.administrative.service.ContactRoleESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.administrative.dto.ContactRoleDTO;

@Configuration
public class ContactRoleJobConfig extends JobIndexingConfig<ContactRole, ContactRoleDTO, DomainES> {

	private static final String JOB_NAME = AdministrativeDomainJobName.CONTACT_ROLE.toString();

	@Autowired
	ContactRoleRepository repository;

	@Autowired
	ContactRoleESService service;

	public ContactRoleJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job contactRoleJob() {

		return createJobIndexing().start(contactRoleStep()).build();
	}

	@Bean
	@StepScope
	public Step contactRoleStep() {

		return createStepIndexing(contactRoleItemReader(), contactRoleProccessor(), contactRoleItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<ContactRole> contactRoleItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public ContactRoleProcessor contactRoleProccessor() {

		return new ContactRoleProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> contactRoleItemWriter() {

		return createItemWriter(service);
	}
}
