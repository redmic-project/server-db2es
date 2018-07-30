package es.redmic.db2es.jobs.job.indexing.maintenance.quality.qflag;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.redmic.db.maintenance.quality.model.QFlag;
import es.redmic.db.maintenance.quality.repository.QFlagRepository;
import es.redmic.db2es.jobs.job.common.base.ItemReaderBase;
import es.redmic.db2es.jobs.job.common.base.ItemWriterBySave;
import es.redmic.db2es.jobs.job.indexing.common.JobIndexingConfig;
import es.redmic.db2es.jobs.job.indexing.maintenance.quality.QualityDomainJobName;
import es.redmic.es.maintenance.quality.service.QFlagESService;
import es.redmic.models.es.maintenance.quality.dto.QFlagDTO;

@Configuration
public class QFlagJobConfig
		extends JobIndexingConfig<QFlag, QFlagDTO, es.redmic.models.es.maintenance.quality.model.QFlag> {

	private static final String JOB_NAME = QualityDomainJobName.QFLAG.toString();

	@Autowired
	QFlagRepository repository;

	@Autowired
	QFlagESService service;

	public QFlagJobConfig() {

		super(JOB_NAME);
	}

	@Bean
	public Job qFlagJob() {

		return createJobIndexing().start(qFlagStep()).build();
	}

	@Bean
	@StepScope
	public Step qFlagStep() {

		return createStepIndexing(qFlagItemReader(), qFlagProccessor(), qFlagItemWriter());
	}

	@Bean
	@StepScope
	public ItemReaderBase<QFlag> qFlagItemReader() {

		return createItemReader(repository);
	}

	@Bean
	public QFlagProcessor qFlagProccessor() {

		return new QFlagProcessor();
	}

	@Bean
	public ItemWriterBySave<es.redmic.models.es.maintenance.quality.model.QFlag> qFlagItemWriter() {

		return createItemWriter(service);
	}
}
