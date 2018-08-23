package es.redmic.db2es.jobs.job.indexing.maintenance.toponymias;

import es.redmic.db2es.jobs.job.common.JobsNamesBase;

public enum ToponymiasJobName implements JobsNamesBase<ToponymiasJobName> {

	// @formatter:off
	
	TOPONYM_TYPE(Constants.TOPONYM_TYPE),
	TOPONYM(Constants.TOPONYM);
		
	// @formatter:on

	final String jobName;

	ToponymiasJobName(String jobName) {
		this.jobName = jobName;
	}

	@Override
	public String toString() {
		return jobName;
	}

	private static class Constants {

		// @formatter:off
		
			public static final String
							TOPONYM_TYPE = "toponymType",
							TOPONYM = "toponym";					
	   // @formatter:on

	}
}
