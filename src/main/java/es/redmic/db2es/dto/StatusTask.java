package es.redmic.db2es.dto;

import org.joda.time.DateTime;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import es.redmic.models.es.common.serializer.CustomDateTimeSerializer;

public class StatusTask {

	JobExecution job;

	public StatusTask(JobExecution job) {
		this.job = job;
	}
	
	public StatusTask(StepExecution step) {
		this.job = step.getJobExecution();
	}
	
	@JsonProperty("taskId")
	public Long getTaskId() {
		return job.getJobInstance().getInstanceId();
	}
	
	@JsonProperty("taskName")
	public String getTaskName() {
		return job.getJobInstance().getJobName();
	}
	
	@JsonProperty("status")
	public String getStatus() {
		return job.getStatus().name();
	}

	@JsonProperty("createTime")
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public DateTime getCreateTime() {
		return new DateTime(job.getCreateTime());
	}
	
	@JsonProperty("count")
	public Long getCount() {
		Long count = 0L;
		for (StepExecution jobIns : job.getStepExecutions()){
			count += jobIns.getWriteCount();
		}
		
		return count;
	}
	


}
