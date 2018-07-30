package es.redmic.db2es.jobs.job.indexing.maintenance.classifications.seacondition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.strech.model.SeaCondition;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.strech.service.SeaConditionESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.strech.dto.SeaConditionDTO;

public class SeaConditionProcessor implements ItemProcessor<SeaCondition, DomainES> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SeaConditionProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private SeaConditionESService service;

	public DomainES process(SeaCondition inBean) throws Exception {

		SeaConditionDTO dto = orikaMapper.map(inBean, SeaConditionDTO.class);
		DomainES outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
