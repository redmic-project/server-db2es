package es.redmic.db2es.jobs.job.indexing.atlas;

import es.redmic.db2es.jobs.job.common.JobsNamesBase;

public enum AtlasJobName implements JobsNamesBase<AtlasJobName> {

	// @formatter:off
	
	THEME_INSPIRE(Constants.THEME_INSPIRE),
	LAYER(Constants.LAYER);
		
	// @formatter:on

	final String jobName;

	AtlasJobName(String jobName) {
		this.jobName = jobName;
	}

	public String toString() {
		return jobName;
	}

	private static class Constants {

		// @formatter:off
		
			public static final String
					THEME_INSPIRE = "themeInspire",
					LAYER = "layer";					
					
	   // @formatter:on

	}
}
