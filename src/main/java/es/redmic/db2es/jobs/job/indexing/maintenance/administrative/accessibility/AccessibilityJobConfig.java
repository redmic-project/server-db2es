package es.redmic.db2es.jobs.job.indexing.maintenance.administrative.accessibility;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.common.model.Accessibility;
import es.redmic.db.maintenance.common.repository.AccessibilityRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.administrative.AdministrativeDomainJobName;
import es.redmic.es.maintenance.domain.administrative.service.AccessibilityESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.common.dto.AccessibilityDTO;

@Configuration
public class AccessibilityJobConfig extends JobIndexingConfig<Accessibility, AccessibilityDTO, DomainES> {

	private static final String JOB_NAME = AdministrativeDomainJobName.ACCESSIBILITY.toString();

	@Autowired
	AccessibilityRepository repository;

	@Autowired
	AccessibilityESService service;

	public AccessibilityJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job accessibilityJob() {

		return createJobIndexing().start(accessibilityStep()).build();
	}

	@Bean
	@StepScope
	public Step accessibilityStep() {

		return createStepIndexing(accessibilityItemReader(), accessibilityProccessor(), accessibilityItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Accessibility> accessibilityItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public AccessibilityProcessor accessibilityProccessor() {

		return new AccessibilityProcessor();
	}

	@Bean
	public ItemWriterBySave<DomainES> accessibilityItemWriter() {

		return createItemWriter(service);
	}
}
