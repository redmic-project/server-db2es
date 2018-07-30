package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.taxon.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.taxonomy.model.Status;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.StatusESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.taxonomy.dto.StatusDTO;

public class StatusProcessor implements ItemProcessor<Status, DomainES> {

	private static final Logger LOGGER = LoggerFactory.getLogger(StatusProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private StatusESService service;

	public DomainES process(Status inBean) throws Exception {

		StatusDTO dto = orikaMapper.map(inBean, StatusDTO.class);
		DomainES outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
