package es.redmic.db2es.jobs.job.indexing.administrative.project;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.administrative.model.Project;
import es.redmic.db.administrative.repository.ProjectRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.administrative.AdministrativeJobName;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.es.administrative.service.ProjectESService;
import es.redmic.models.es.administrative.dto.ProjectDTO;

@Configuration
public class ProjectJobConfig
		extends JobIndexingConfig<Project, ProjectDTO, es.redmic.models.es.administrative.model.Project> {

	private static final String JOB_NAME = AdministrativeJobName.PROJECT.toString();

	@Autowired
	ProjectRepository repository;

	@Autowired
	ProjectESService service;

	public ProjectJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job projectJob() {

		return createJobIndexing().start(projectStep()).build();
	}

	@Bean
	public Step projectStep() {

		return createStepIndexing(projectItemReader(), projectProccessor(), projectItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<Project> projectItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public ProjectProcessor projectProccessor() {

		return new ProjectProcessor();
	}

	@Bean
	public ItemWriterBySave<es.redmic.models.es.administrative.model.Project> projectItemWriter() {

		return createItemWriter(service);
	}
}
