package es.redmic.db2es.jobs.job.indexing.geodata.platformtracking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.geodata.tracking.platform.model.PlatformTracking;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.geodata.tracking.platform.service.PlatformTrackingESService;
import es.redmic.models.es.geojson.common.model.GeoPointData;
import es.redmic.models.es.geojson.tracking.platform.dto.PlatformTrackingDTO;

public class PlatformTrackingProcessor implements ItemProcessor<PlatformTracking, GeoPointData> {
	 
	private static final Logger LOGGER = LoggerFactory.getLogger(PlatformTrackingProcessor.class);
	
	@Autowired
	private OrikaScanBean orikaMapper;
	
	@Autowired
	private PlatformTrackingESService service;
	
	public GeoPointData process(PlatformTracking platformTrackingIn) throws Exception {
		
		PlatformTrackingDTO platformTrackingDto = orikaMapper.map(platformTrackingIn, PlatformTrackingDTO.class);
		GeoPointData platformTrackingOut = service.mapper(platformTrackingDto);

		LOGGER.info("Converting (" + platformTrackingIn + ") into (" + platformTrackingOut + ")");

		return platformTrackingOut;
	}

}
