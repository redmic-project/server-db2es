package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.taxon.origin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.taxonomy.model.Origin;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.OriginESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.taxonomy.dto.OriginDTO;

public class OriginProcessor implements ItemProcessor<Origin, DomainES> {

	private static final Logger LOGGER = LoggerFactory.getLogger(OriginProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private OriginESService service;

	public DomainES process(Origin inBean) throws Exception {

		OriginDTO dto = orikaMapper.map(inBean, OriginDTO.class);
		DomainES outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
