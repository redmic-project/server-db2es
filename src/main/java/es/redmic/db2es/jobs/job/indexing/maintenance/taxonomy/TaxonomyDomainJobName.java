package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy;

import es.redmic.db2es.jobs.job.common.JobsNamesBase;

public enum TaxonomyDomainJobName implements JobsNamesBase<TaxonomyDomainJobName>{

	// @formatter:off
	
	// Taxones
	CANARY_PROTECTION(Constants.CANARY_PROTECTION),
	CONFIDENCE(Constants.CONFIDENCE),
	ECOLOGY(Constants.ECOLOGY),
	ENDEMICITY(Constants.ENDEMICITY),
	EU_PROTECTION(Constants.EU_PROTECTION),
	INTEREST(Constants.INTEREST),
	ORIGIN(Constants.ORIGIN),
	PERMANENCE(Constants.PERMANENCE),
	RANK(Constants.RANK),
	SPAIN_PROTECTION(Constants.SPAIN_PROTECTION),
	STATUS(Constants.STATUS),
	TROPHIC_REGIME(Constants.TROPHIC_REGIME),
	
	// Animales
	DESTINY(Constants.DESTINY),
	ENDING(Constants.ENDING),
	LIFE_STAGE(Constants.LIFE_STAGE),
	SEX(Constants.SEX);
		
	// @formatter:on

	final String jobName;

	TaxonomyDomainJobName(String jobName) {
		this.jobName = jobName;
	}

	@Override
	public String toString() {
		return jobName;
	}

	private static class Constants {

		// @formatter:off
		
			public static final String
					CANARY_PROTECTION = "canaryProtection",
					CONFIDENCE = "confidence",
					ECOLOGY = "ecology",
					ENDEMICITY = "endemicity",
					EU_PROTECTION = "euProtection",
					INTEREST = "interest",
					ORIGIN = "origin",
					PERMANENCE = "permanence",
					RANK = "rank",
					SPAIN_PROTECTION = "spainProtection",
					STATUS = "status",
					TROPHIC_REGIME = "trophicRegime",

					// Animales
					DESTINY = "destiny",
					ENDING = "ending",
					LIFE_STAGE = "lifeStage",
					SEX = "sex";
					
	   // @formatter:on

	}
}
