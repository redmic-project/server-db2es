package es.redmic.db2es.jobs.job.indexing.taxonomy.species;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.administrative.taxonomy.model.AbstractSpecies;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.administrative.taxonomy.service.SpeciesESService;
import es.redmic.models.es.administrative.taxonomy.dto.SpeciesDTO;

public class SpeciesDiscardValidAsProcessor implements ItemProcessor<AbstractSpecies, es.redmic.models.es.administrative.taxonomy.model.Species> {
	 
	private static final Logger LOGGER = LoggerFactory.getLogger(SpeciesDiscardValidAsProcessor.class);
	
	@Autowired
	private OrikaScanBean orikaMapper;
	
	@Autowired
	private SpeciesESService service;
	
	public es.redmic.models.es.administrative.taxonomy.model.Species process(AbstractSpecies inBean) throws Exception {
		
		SpeciesDTO dto = orikaMapper.map(inBean, SpeciesDTO.class);
		dto.setValidAs(null);
		es.redmic.models.es.administrative.taxonomy.model.Species outBean = service.mapper(dto);
		
		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}
}
