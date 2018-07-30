package es.redmic.db2es.jobs.job.indexing.maintenance.parameters;

import es.redmic.db2es.jobs.job.common.JobsNamesBase;

public enum ParametersDomainJobName implements JobsNamesBase<ParametersDomainJobName> {

	// @formatter:off
	
	PARAMETER_TYPE(Constants.PARAMETER_TYPE),
	UNIT_TYPE(Constants.UNIT_TYPE),
	UNIT(Constants.UNIT),
	SAMPLE_TYPE(Constants.SAMPLE_TYPE),
	PARAMETER(Constants.PARAMETER),
	DEVICE_TYPE(Constants.DEVICE_TYPE);
		
	// @formatter:on

	final String jobName;

	ParametersDomainJobName(String jobName) {
		this.jobName = jobName;
	}

	@Override
	public String toString() {
		return jobName;
	}

	private static class Constants {

		// @formatter:off
		
			public static final String
					PARAMETER_TYPE = "parameterType",
					UNIT_TYPE = "unitType",
					UNIT = "unit",
					SAMPLE_TYPE = "sampleType",
					PARAMETER = "parameter",
					DEVICE_TYPE = "deviceType";
					
	   // @formatter:on

	}
}
