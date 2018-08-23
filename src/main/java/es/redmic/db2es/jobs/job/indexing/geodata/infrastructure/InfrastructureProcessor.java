package es.redmic.db2es.jobs.job.indexing.geodata.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.geodata.infrastructure.model.Infrastructure;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.geodata.infrastructure.service.InfrastructureESService;
import es.redmic.models.es.geojson.common.model.GeoPointData;
import es.redmic.models.es.geojson.infrastructure.dto.InfrastructureDTO;

public class InfrastructureProcessor implements ItemProcessor<Infrastructure, GeoPointData> {
	 
	private static final Logger log = LoggerFactory.getLogger(InfrastructureProcessor.class);
	
	@Autowired
	private OrikaScanBean orikaMapper;
	
	@Autowired
	private InfrastructureESService service;
	
	@Override
	public GeoPointData process(Infrastructure infrastructureIn) throws Exception {
		
		InfrastructureDTO infrastructureDto = orikaMapper.map(infrastructureIn, InfrastructureDTO.class);

		GeoPointData infrastructureOut = service.mapper(infrastructureDto);

		log.info("Converting (" + infrastructureIn + ") into (" + infrastructureOut + ")");

		return infrastructureOut;
	}
}
