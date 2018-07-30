package es.redmic.db2es.jobs.job.indexing.geodata.survey.objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.geodata.properties.fixedsurvey.model.FixedSurvey;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.geodata.geofixedstation.service.GeoFixedObjectCollectingSeriesESService;
import es.redmic.models.es.geojson.common.model.GeoLineStringData;
import es.redmic.models.es.geojson.geofixedstation.dto.GeoFixedObjectCollectingSeriesDTO;

public class ObjectCollectingSurveyProcessor implements ItemProcessor<FixedSurvey, GeoLineStringData> {
	 
	private static final Logger log = LoggerFactory.getLogger(ObjectCollectingSurveyProcessor.class);
	
	@Autowired
	private OrikaScanBean orikaMapper;
	
	@Autowired
	private GeoFixedObjectCollectingSeriesESService service;
	
	
	@Override
	public GeoLineStringData process(FixedSurvey modelIn) throws Exception {
		
		GeoFixedObjectCollectingSeriesDTO tempDto = orikaMapper.map(modelIn, GeoFixedObjectCollectingSeriesDTO.class);
		
		GeoLineStringData outDto = service.mapper(tempDto);

		log.info("Converting (" + modelIn + ") into (" + outDto + ")");

		return outDto;
	}
}
