package es.redmic.db2es.config;

import javax.sql.DataSource;

import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import es.redmic.db2es.jobs.listener.EventStepListener;
import es.redmic.db2es.jobs.listener.JobListener;
import es.redmic.db2es.jobs.listener.LogProcessListener;

@Configuration
@EnableBatchProcessing
public class JobConfig implements BatchConfigurer {
	
	@Value("${db2es.taskExecutor.MAX_POOL_SIZE}")
	private Integer MAX_POOL_SIZE;
	
	@Value("${db2es.taskExecutor.CORE_POOL_SIZE}")
	private Integer CORE_POOL_SIZE;
	
	@Value("${db2es.taskExecutor.QUEUE_CAPACITY}")
	private Integer QUEUE_CAPACITY;

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource primaryDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@ConfigurationProperties(prefix = "jobs.datasource")
	public DataSource dataSourceJob() {

		return DataSourceBuilder.create().build();
	}

	@Bean
	public JobRegistry jobRegistry() {
		JobRegistry jobRegistry = new MapJobRegistry();

		return jobRegistry;
	}

	@Bean
	public StepBuilderFactory stepBuilderFactory() throws Exception {
		StepBuilderFactory stepBuilderFactory = new StepBuilderFactory(getJobRepository(), getTransactionManager());

		return stepBuilderFactory;
	}

	@Bean
	public TaskExecutor taskExecutor() {
		
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        taskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        taskExecutor.setQueueCapacity(QUEUE_CAPACITY);
		taskExecutor.afterPropertiesSet();
		return taskExecutor;
	}

	@Bean
	public JobBuilderFactory jobBuilderFactory() throws Exception {
		JobBuilderFactory factory = new JobBuilderFactory(getJobRepository());

		return factory;
	}

	@Bean
	public JobOperator jobOperator() throws Exception {
		SimpleJobOperator jobOperator = new SimpleJobOperator();
		jobOperator.setJobExplorer(getJobExplorer());
		jobOperator.setJobLauncher(getJobLauncher());
		jobOperator.setJobRegistry(jobRegistry());
		jobOperator.setJobRepository(getJobRepository());
		jobOperator.afterPropertiesSet();

		return jobOperator;
	}

	@Bean
	public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor() throws Exception {
		JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
		jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry());
		jobRegistryBeanPostProcessor.afterPropertiesSet();

		return jobRegistryBeanPostProcessor;
	}

	public JobExplorer getJobExplorer() throws Exception {
		JobExplorerFactoryBean factory = new JobExplorerFactoryBean();
		factory.setDataSource(dataSourceJob());
		factory.afterPropertiesSet();

		return factory.getObject();
	}

	public JobLauncher getJobLauncher() throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(getJobRepository());
		jobLauncher.setTaskExecutor(taskExecutor());
		jobLauncher.afterPropertiesSet();

		return jobLauncher;
	}

	public JobRepository getJobRepository() throws Exception {
		JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
		factory.setDataSource(dataSourceJob());
		factory.setTransactionManager(getTransactionManager());
		factory.afterPropertiesSet();

		return factory.getObject();
	}

	public PlatformTransactionManager getTransactionManager() throws Exception {

		return new DataSourceTransactionManager(dataSourceJob());
	}

	@Bean
	@Autowired
	public JobListener jobListener(ApplicationEventPublisher publisher) {
		return new JobListener(publisher);
	}

	@Bean
	@Autowired
	public StepExecutionListener stepExecutionListener(ApplicationEventPublisher publisher) {
		return new EventStepListener(publisher);
	}

	@Bean
	public LogProcessListener logProcessListener() {
		return new LogProcessListener();
	}

}
