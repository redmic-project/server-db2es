package es.redmic.db2es.jobs.job.indexing.atlas.themeinspire;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.atlas.layer.model.ThemeInspire;
import es.redmic.db.atlas.layer.repository.ThemeInspireRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.atlas.AtlasJobName;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.es.atlas.service.ThemeInspireESService;
import es.redmic.models.es.atlas.dto.ThemeInspireDTO;

@Configuration
public class ThemeInspireJobConfig
		extends JobIndexingConfig<ThemeInspire, ThemeInspireDTO, es.redmic.models.es.atlas.model.ThemeInspire> {

	private static final String JOB_NAME = AtlasJobName.THEME_INSPIRE.toString();

	@Autowired
	ThemeInspireRepository repository;

	@Autowired
	ThemeInspireESService service;

	public ThemeInspireJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job themeInspireJob() {

		return createJobIndexing().start(themeInspireStep()).build();
	}

	@Bean
	public Step themeInspireStep() {

		return createStepIndexing(themeInspireItemReader(), themeInspireProccessor(), themeInspireItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<ThemeInspire> themeInspireItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public ThemeInspireProcessor themeInspireProccessor() {

		return new ThemeInspireProcessor();
	}

	@Bean
	public ItemWriterBySave<es.redmic.models.es.atlas.model.ThemeInspire> themeInspireItemWriter() {

		return createItemWriter(service);
	}
}
