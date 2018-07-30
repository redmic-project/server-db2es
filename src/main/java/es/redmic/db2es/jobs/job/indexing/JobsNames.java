package es.redmic.db2es.jobs.job.indexing;

import es.redmic.db2es.jobs.job.common.JobsNamesBase;

public enum JobsNames implements JobsNamesBase<JobsNames> {

	// @formatter:off
	
	ADMINISTRATIVE_DOMAINS(Constants.ADMINISTRATIVE_DOMAINS),
	DOCUMENT(Constants.DOCUMENT),
	CLASSIFICATION_DOMAINS(Constants.CLASSIFICATION_DOMAINS),
	PARAMETERS_DOMAINS(Constants.PARAMETERS_DOMAINS),
	QUALITY_DOMAINS(Constants.QUALITY_DOMAINS),
	TAXONOMY_DOMAINS(Constants.TAXONOMY_DOMAINS),
	QUALIFY_DOMAINS(Constants.QUALIFY_DOMAINS),
	ANCILLARY_DATA_DOMAINS(Constants.ANCILLARY_DATA_DOMAINS),
	TOPONYMIAS(Constants.TOPONYMIAS),
	
	TAXONOMY(Constants.TAXONOMY),
	ADMINISTRATIVE(Constants.ADMINISTRATIVE),
	ATLAS(Constants.ATLAS),
	
	TIMESERIES(Constants.TIMESERIES),
	OBJECT_COLLECTINGSERIES(Constants.OBJECT_COLLECTINGSERIES),
	ATTRIBUTESERIES(Constants.ATTRIBUTESERIES),
	
	ANIMAL_TRACKING(Constants.ANIMAL_TRACKING),
	CITATION(Constants.CITATION),
	INFRASTRUCTURE(Constants.INFRASTRUCTURE),
	ISOLINES(Constants.ISOLINES),
	AREA(Constants.AREA),
	PLATFORM_TRACKING(Constants.PLATFORM_TRACKING),
	OBJECT_COLLECTING_SURVEY(Constants.OBJECT_COLLECTING_SURVEY),
	TIMESERIES_SURVEY(Constants.TIMESERIES_SURVEY);
	
	// @formatter:on

	final String jobName;

	JobsNames(String jobName) {
		this.jobName = jobName;
	}

	@Override
	public String toString() {
		return jobName;
	}

	private static class Constants {

		// @formatter:off
		
			public static final String
					ADMINISTRATIVE_DOMAINS = "administrativeDomains",
					DOCUMENT = "document",
					CLASSIFICATION_DOMAINS = "classificationsDomains",
					PARAMETERS_DOMAINS = "parametersDomains",
					QUALITY_DOMAINS = "qualityDomains",
					QUALIFY_DOMAINS = "qualifyDomains",
					TAXONOMY_DOMAINS = "taxonomyDomains",
					ANCILLARY_DATA_DOMAINS = "ancillaryDataDomains",
					TOPONYMIAS = "toponymias",
					TAXONOMY = "taxonomy",
					ADMINISTRATIVE = "administrative",
					ATLAS = "atlas",
					TIMESERIES = "timeSeries",
					OBJECT_COLLECTINGSERIES = "objectCollectingseries",
					ATTRIBUTESERIES = "attributeSeries",
					ANIMAL_TRACKING = "animalTracking",
					CITATION = "citation",
					INFRASTRUCTURE = "infrastructure",
					ISOLINES = "isolines",
					AREA = "area",
					PLATFORM_TRACKING = "platformTracking",
					OBJECT_COLLECTING_SURVEY = "objectCollectingSurvey",
					TIMESERIES_SURVEY = "timeseriesSurvey";					
					
	   // @formatter:on

	}
}
