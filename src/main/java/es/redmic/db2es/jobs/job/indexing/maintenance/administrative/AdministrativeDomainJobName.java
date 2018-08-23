package es.redmic.db2es.jobs.job.indexing.maintenance.administrative;

import es.redmic.db2es.jobs.job.common.JobsNamesBase;

public enum AdministrativeDomainJobName implements JobsNamesBase<AdministrativeDomainJobName> {

	// @formatter:off
	
	ACCESSIBILITY(Constants.ACCESSIBILITY),
	ACTIVITY_FIELD(Constants.ACTIVITY_FIELD),
	ACTIVITY_RANK(Constants.ACTIVITY_RANK),
	ACTIVITY_TYPE(Constants.ACTIVITY_TYPE),
	CONTACT_ROLE(Constants.CONTACT_ROLE),
	COUNTRY(Constants.COUNTRY),
	DOCUMENT_TYPE(Constants.DOCUMENT_TYPE),
	ORGANISATION_ROLE(Constants.ORGANISATION_ROLE),
	ORGANISATION_TYPE(Constants.ORGANISATION_TYPE),
	PLATFORM_TYPE(Constants.PLATFORM_TYPE),
	PROJECT_GROUP(Constants.PROJECT_GROUP),
	SCOPE(Constants.SCOPE);
	
	// @formatter:on

	final String jobName;

	AdministrativeDomainJobName(String jobName) {
		this.jobName = jobName;
	}

	@Override
	public String toString() {
		return jobName;
	}

	private static class Constants {

		// @formatter:off
		
			public static final String
					ACCESSIBILITY = "accessibility",
					ACTIVITY_FIELD = "activityField",
					ACTIVITY_RANK = "activityRank",
					ACTIVITY_TYPE = "activityType",
					CONTACT_ROLE = "contactRole",
					COUNTRY = "country",
					DOCUMENT_TYPE = "documentType",
					ORGANISATION_ROLE = "organisationRole",
					ORGANISATION_TYPE = "organisationType",
					PLATFORM_TYPE = "platformType",
					PROJECT_GROUP = "projectGroup",
					SCOPE = "scope";					
					
	   // @formatter:on

	}
}
