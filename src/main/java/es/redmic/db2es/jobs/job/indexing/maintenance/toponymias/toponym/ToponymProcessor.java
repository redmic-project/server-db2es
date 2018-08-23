package es.redmic.db2es.jobs.job.indexing.maintenance.toponymias.toponym;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.redmic.db.geodata.toponym.model.Toponym;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.toponym.service.ToponymESService;
import es.redmic.models.es.geojson.toponym.dto.ToponymDTO;



@Component
public class ToponymProcessor implements ItemProcessor<Toponym, es.redmic.models.es.geojson.toponym.model.Toponym> {
	 
	private static final Logger log = LoggerFactory.getLogger(ToponymProcessor.class);
	
	@Autowired
	private OrikaScanBean orikaMapper;
	
	@Autowired
	private ToponymESService service;
	
	@Override
	public es.redmic.models.es.geojson.toponym.model.Toponym process(Toponym modelIn) throws Exception {
		
		ToponymDTO dto = orikaMapper.map(modelIn, ToponymDTO.class);

		es.redmic.models.es.geojson.toponym.model.Toponym modelOut = service.mapper(dto);

		log.info("Converting (" + modelIn.getId() + ") into (" + modelOut.getId() + ")");

		return modelOut;
	}
}
