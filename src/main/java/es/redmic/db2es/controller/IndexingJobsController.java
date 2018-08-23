package es.redmic.db2es.controller;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.launch.NoSuchJobInstanceException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import es.redmic.db2es.dto.RunJobDTO;

@Controller
public class IndexingJobsController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	JobOperator jobOperator;

	@MessageMapping("/jobs/indexing")
	public void indexing(RunJobDTO runJob) throws NoSuchJobException, JobInstanceAlreadyExistsException {

		LOGGER.info(runJob.getJobId() + " - Petición de indexado");

		Set<Long> executions = getRunningExecutions(runJob.getJobName());

		if (executions.isEmpty()) {
			LOGGER.info(runJob.getJobId() + " - Iniciando ejecución");

			try {
				jobOperator.start(runJob.getJobName(), null);
			} catch (JobParametersInvalidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// jobOperator.startNextInstance(runJob.getJobName());

		} else {
			LOGGER.info(runJob.getJobId() + " - Ya existe una ejecución en curso");
		}
	}

	@MessageMapping("/jobs/stop")
	public void stop(RunJobDTO runJob)
			throws NoSuchJobException, NoSuchJobExecutionException, JobExecutionNotRunningException {
		LOGGER.info(runJob.getJobId() + " - Cancelación de indexado");

		Set<Long> executions = getRunningExecutions(runJob.getJobName());
		jobOperator.stop(executions.iterator().next());
	}

	@MessageMapping("/jobs/delete")
	public void delete(RunJobDTO runJob)
			throws NoSuchJobInstanceException, NoSuchJobExecutionException, JobExecutionAlreadyRunningException {
		LOGGER.info(runJob.getJobId() + " - Cancelación de indexado");

		List<Long> executions = getExecutions(runJob.getJobId());
		jobOperator.abandon(executions.iterator().next());
	}

	private Set<Long> getRunningExecutions(String jobName) throws NoSuchJobException {
		return jobOperator.getRunningExecutions(jobName);
	}

	private List<Long> getExecutions(Long jobId) throws NoSuchJobInstanceException {
		return jobOperator.getExecutions(jobId);
	}

}
