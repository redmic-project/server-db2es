package es.redmic.db2es.jobs.job.indexing.taxonomy;

import es.redmic.db2es.jobs.job.common.JobsNamesBase;

public enum TaxonomyJobName implements JobsNamesBase<TaxonomyJobName> {

	// @formatter:off
	
	KINGDOM(Constants.KINGDOM),
	PHYLUM(Constants.PHYLUM),
	SUBPHYLUM(Constants.SUBPHYLUM),
	CLASSS(Constants.CLASSS),
	
	ORDER_DISCARD_VALID_AS(Constants.ORDER_DISCARD_VALID_AS),
	ORDER_WITH_VALID_AS(Constants.ORDER_WITH_VALID_AS),
	
	FAMILY_DISCARD_VALID_AS(Constants.FAMILY_DISCARD_VALID_AS),
	FAMILY_WITH_VALID_AS(Constants.FAMILY_WITH_VALID_AS),
	
	GENUS_DISCARD_VALID_AS(Constants.GENUS_DISCARD_VALID_AS),
	GENUS_WITH_VALID_AS(Constants.GENUS_WITH_VALID_AS),
	
	SPECIES_DISCARD_VALIDAS(Constants.SPECIES_DISCARD_VALIDAS),
	SPECIES_WITH_VALID_AS(Constants.SPECIES_WITH_VALID_AS),
	
	ANIMAL(Constants.ANIMAL),
	MISIDENTIFICATION(Constants.MISIDENTIFICATION);
		
	// @formatter:on

	final String jobName;

	TaxonomyJobName(String jobName) {
		this.jobName = jobName;
	}

	@Override
	public String toString() {
		return jobName;
	}

	private static class Constants {

		// @formatter:off
		
			public static final String
					KINGDOM = "kingdom",
					PHYLUM = "phylum",
					SUBPHYLUM = "subphylum",
					CLASSS = "classs",
					
					ORDER_DISCARD_VALID_AS = "orderDiscardValidAs",
					ORDER_WITH_VALID_AS = "orderWithValidAs",
					
					FAMILY_DISCARD_VALID_AS = "familyDiscardValidAs",
					FAMILY_WITH_VALID_AS = "familyWithValidAs",
					
					GENUS_DISCARD_VALID_AS = "genusDiscardValidAs",
					GENUS_WITH_VALID_AS = "genusWithValidAs",
							
					SPECIES_DISCARD_VALIDAS = "speciesDiscardValidAs",
					SPECIES_WITH_VALID_AS = "speciesWithValidAs",
					
					ANIMAL = "animal",
					MISIDENTIFICATION = "misidentification";					
					
	   // @formatter:on

	}
}
