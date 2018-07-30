package es.redmic.db2es.jobs.job.indexing.maintenance.classifications;

import es.redmic.db2es.jobs.job.common.JobsNamesBase;

public enum ClassificationsDomainJobName implements JobsNamesBase<ClassificationsDomainJobName> {

	// @formatter:off
	
	INFRASTRUCTURE_TYPE(Constants.INFRASTRUCTURE_TYPE),
	LINE_TYPE(Constants.LINE_TYPE),
	OBJECT_TYPE(Constants.OBJECT_TYPE),
	THEMATIC_TYPE(Constants.THEMATIC_TYPE),
	CENSING_STATUS(Constants.CENSING_STATUS),
	SEA_CONDITION(Constants.SEA_CONDITION),
	AREA_TYPE(Constants.AREA_TYPE);
		
	// @formatter:on

	final String jobName;

	ClassificationsDomainJobName(String jobName) {
		this.jobName = jobName;
	}

	@Override
	public String toString() {
		return jobName;
	}

	private static class Constants {

		// @formatter:off
		
			public static final String
					INFRASTRUCTURE_TYPE = "infrastructureType",
					LINE_TYPE = "lineType",
					OBJECT_TYPE = "objectType",
					THEMATIC_TYPE = "thematicType",
					CENSING_STATUS = "censingStatus",
					SEA_CONDITION = "seaCondition",
					AREA_TYPE = "areaType";
			
	   // @formatter:on

	}
}
