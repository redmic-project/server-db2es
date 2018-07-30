package es.redmic.db2es.jobs.job.indexing.maintenance.quality.vflag;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.quality.model.VFlag;
import es.redmic.db.maintenance.quality.repository.VFlagRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.quality.QualityDomainJobName;
import es.redmic.es.maintenance.quality.service.VFlagESService;
import es.redmic.models.es.maintenance.quality.dto.VFlagDTO;

@Configuration
public class VFlagJobConfig
		extends JobIndexingConfig<VFlag, VFlagDTO, es.redmic.models.es.maintenance.quality.model.VFlag> {

	private static final String JOB_NAME = QualityDomainJobName.VFLAG.toString();

	@Autowired
	VFlagRepository repository;

	@Autowired
	VFlagESService service;

	public VFlagJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job vFlagJob() {

		return createJobIndexing().start(vFlagStep()).build();
	}

	@Bean
	@StepScope
	public Step vFlagStep() {

		return createStepIndexing(vFlagItemReader(), vFlagProccessor(), vFlagItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<VFlag> vFlagItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public VFlagProcessor vFlagProccessor() {

		return new VFlagProcessor();
	}

	@Bean
	public ItemWriterBySave<es.redmic.models.es.maintenance.quality.model.VFlag> vFlagItemWriter() {

		return createItemWriter(service);
	}
}
