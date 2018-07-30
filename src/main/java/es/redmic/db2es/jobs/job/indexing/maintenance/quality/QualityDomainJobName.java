package es.redmic.db2es.jobs.job.indexing.maintenance.quality;

import es.redmic.db2es.jobs.job.common.JobsNamesBase;

public enum QualityDomainJobName implements JobsNamesBase<QualityDomainJobName> {

	// @formatter:off
	
	QFLAG(Constants.QFLAG),
	VFLAG(Constants.VFLAG);
	
	// @formatter:on

	final String jobName;

	QualityDomainJobName(String jobName) {
		this.jobName = jobName;
	}

	@Override
	public String toString() {
		return jobName;
	}

	private static class Constants {

		// @formatter:off
		
			public static final String
					QFLAG = "qFlag",
					VFLAG = "vFlag";					
					
	   // @formatter:on

	}
}
