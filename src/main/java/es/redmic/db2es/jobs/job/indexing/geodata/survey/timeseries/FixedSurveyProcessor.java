package es.redmic.db2es.jobs.job.indexing.geodata.survey.timeseries;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.geodata.properties.fixedsurvey.model.FixedSurvey;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.geodata.geofixedstation.service.GeoFixedTimeSeriesESService;
import es.redmic.models.es.geojson.common.model.GeoPointData;
import es.redmic.models.es.geojson.geofixedstation.dto.GeoFixedTimeSeriesDTO;

public class FixedSurveyProcessor implements ItemProcessor<FixedSurvey, GeoPointData> {
	 
	private static final Logger log = LoggerFactory.getLogger(FixedSurveyProcessor.class);
	
	@Autowired
	private OrikaScanBean orikaMapper;
	
	@Autowired
	private GeoFixedTimeSeriesESService service;
	
	
	@Override
	public GeoPointData process(FixedSurvey modelIn) throws Exception {
		
		GeoFixedTimeSeriesDTO tempDto = orikaMapper.map(modelIn, GeoFixedTimeSeriesDTO.class);
		GeoPointData outDto = service.mapper(tempDto);

		log.info("Converting (" + modelIn + ") into (" + outDto + ")");

		return outDto;
	}
}
