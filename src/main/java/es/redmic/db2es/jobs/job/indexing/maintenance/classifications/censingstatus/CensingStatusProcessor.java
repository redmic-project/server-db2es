package es.redmic.db2es.jobs.job.indexing.maintenance.classifications.censingstatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.strech.model.CensingStatus;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.strech.service.CensingStatusESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.strech.dto.CensingStatusDTO;

public class CensingStatusProcessor implements ItemProcessor<CensingStatus, DomainES> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CensingStatusProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private CensingStatusESService service;

	public DomainES process(CensingStatus inBean) throws Exception {

		CensingStatusDTO dto = orikaMapper.map(inBean, CensingStatusDTO.class);
		DomainES outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
