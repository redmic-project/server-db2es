package es.redmic.db2es.jobs.job.indexing.geodata.animaltracking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.geodata.tracking.animal.model.AnimalTracking;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.geodata.tracking.animal.service.AnimalTrackingESService;
import es.redmic.models.es.geojson.common.model.GeoPointData;
import es.redmic.models.es.geojson.tracking.animal.dto.AnimalTrackingDTO;

public class AnimalTrackingProcessor implements ItemProcessor<AnimalTracking, GeoPointData> {
	 
	private static final Logger LOGGER = LoggerFactory.getLogger(AnimalTrackingProcessor.class);
	
	@Autowired
	private OrikaScanBean orikaMapper;
	
	@Autowired
	private AnimalTrackingESService service;
	
	public GeoPointData process(AnimalTracking animalTrackingIn) throws Exception {
		
		AnimalTrackingDTO animalTrackingDto = orikaMapper.map(animalTrackingIn, AnimalTrackingDTO.class);
		GeoPointData animalTrackingOut = service.mapper(animalTrackingDto);

		LOGGER.info("Converting (" + animalTrackingIn + ") into (" + animalTrackingOut + ")");

		return animalTrackingOut;
	}

}
