package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.taxon.permanence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.taxonomy.model.Permanence;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.PermanenceESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.taxonomy.dto.PermanenceDTO;

public class PermanenceProcessor implements ItemProcessor<Permanence, DomainES> {

	private static final Logger LOGGER = LoggerFactory.getLogger(PermanenceProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private PermanenceESService service;

	public DomainES process(Permanence inBean) throws Exception {

		PermanenceDTO dto = orikaMapper.map(inBean, PermanenceDTO.class);
		DomainES outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
