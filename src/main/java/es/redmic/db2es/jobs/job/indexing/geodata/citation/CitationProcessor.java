package es.redmic.db2es.jobs.job.indexing.geodata.citation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.geodata.citation.model.Citation;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.geodata.citation.service.CitationESService;
import es.redmic.models.es.geojson.citation.dto.CitationDTO;
import es.redmic.models.es.geojson.common.model.GeoPointData;

public class CitationProcessor implements ItemProcessor<Citation, GeoPointData> {
	 
	private static final Logger log = LoggerFactory.getLogger(CitationProcessor.class);
	
	@Autowired
	private OrikaScanBean orikaMapper;
	
	@Autowired
	private CitationESService service;
	
	@Override
	public GeoPointData process(Citation citationIn) throws Exception {
		
		CitationDTO citationDto = orikaMapper.map(citationIn, CitationDTO.class);

		GeoPointData citationOut = service.mapper(citationDto);

		log.info("Converting (" + citationIn + ") into (" + citationOut + ")");

		return citationOut;
	}
}
