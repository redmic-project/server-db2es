package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.animal.ending;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.animal.model.Ending;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.animal.service.EndingESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.animal.dto.EndingDTO;

public class EndingProcessor implements ItemProcessor<Ending, DomainES> {

	private static final Logger LOGGER = LoggerFactory.getLogger(EndingProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private EndingESService service;

	public DomainES process(Ending inBean) throws Exception {

		EndingDTO dto = orikaMapper.map(inBean, EndingDTO.class);
		DomainES outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
