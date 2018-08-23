package es.redmic.db2es.dto;

public class RunJobDTO {

	Long jobId;
	String jobName;

	public Long getJobId() {
		return jobId;
	}

	public RunJobDTO setJobId(Long jobId) {
		this.jobId = jobId;
		return this;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
}
