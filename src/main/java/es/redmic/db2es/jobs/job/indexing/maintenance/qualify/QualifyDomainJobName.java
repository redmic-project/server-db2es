package es.redmic.db2es.jobs.job.indexing.maintenance.qualify;

import es.redmic.db2es.jobs.job.common.JobsNamesBase;

public enum QualifyDomainJobName implements JobsNamesBase<QualifyDomainJobName> {

	// @formatter:off
	
	ATTRIBUTE_TYPE(Constants.ATTRIBUTE_TYPE);
	
	// @formatter:on

	final String jobName;

	QualifyDomainJobName(String jobName) {
		this.jobName = jobName;
	}

	@Override
	public String toString() {
		return jobName;
	}

	private static class Constants {

		// @formatter:off
		
			public static final String
					ATTRIBUTE_TYPE = "attributeType";				
					
	   // @formatter:on

	}
}
