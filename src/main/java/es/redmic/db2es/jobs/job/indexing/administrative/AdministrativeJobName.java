package es.redmic.db2es.jobs.job.indexing.administrative;

import es.redmic.db2es.jobs.job.common.JobsNamesBase;

public enum AdministrativeJobName implements JobsNamesBase<AdministrativeJobName> {

	// @formatter:off
	ORGANISATION(Constants.ORGANISATION),
	CONTACT(Constants.CONTACT),
	PLATFORM(Constants.PLATFORM),
	
	PROGRAM(Constants.PROGRAM),
	PROJECT(Constants.PROJECT),
	ACTIVITY(Constants.ACTIVITY),
	
	DEVICE(Constants.DEVICE);
		
	// @formatter:on

	final String jobName;

	AdministrativeJobName(String jobName) {
		this.jobName = jobName;
	}

	public String toString() {
		return jobName;
	}

	private static class Constants {

		// @formatter:off
		
			public static final String
					ACTIVITY = "activity",
					CONTACT = "contact",
					DEVICE = "device",
					ORGANISATION = "organisation",
					PLATFORM = "platform",
					PROGRAM = "program",
					PROJECT = "project";					
					
	   // @formatter:on

	}
}
