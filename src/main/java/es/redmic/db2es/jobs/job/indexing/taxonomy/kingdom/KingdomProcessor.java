package es.redmic.db2es.jobs.job.indexing.taxonomy.kingdom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.administrative.taxonomy.model.Kingdom;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.administrative.taxonomy.service.KingdomESService;
import es.redmic.models.es.administrative.taxonomy.dto.KingdomDTO;
import es.redmic.models.es.administrative.taxonomy.model.Taxon;

public class KingdomProcessor implements ItemProcessor<Kingdom, Taxon> {

	private static final Logger LOGGER = LoggerFactory.getLogger(KingdomProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private KingdomESService service;

	public Taxon process(Kingdom inBean) throws Exception {

		KingdomDTO dto = orikaMapper.map(inBean, KingdomDTO.class);
		
		Taxon outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}
}

