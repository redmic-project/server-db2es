package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.animal.destiny;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.animal.model.Destiny;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.animal.service.DestinyESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.animal.dto.DestinyDTO;

public class DestinyProcessor implements ItemProcessor<Destiny, DomainES> {

	private static final Logger LOGGER = LoggerFactory.getLogger(DestinyProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private DestinyESService service;

	public DomainES process(Destiny inBean) throws Exception {

		DestinyDTO dto = orikaMapper.map(inBean, DestinyDTO.class);
		DomainES outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
