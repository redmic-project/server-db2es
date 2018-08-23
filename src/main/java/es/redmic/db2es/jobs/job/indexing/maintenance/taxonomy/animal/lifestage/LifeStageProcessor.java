package es.redmic.db2es.jobs.job.indexing.maintenance.taxonomy.animal.lifestage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.animal.model.LifeStage;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.animal.service.LifeStageESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.animal.dto.LifeStageDTO;

public class LifeStageProcessor implements ItemProcessor<LifeStage, DomainES> {

	private static final Logger LOGGER = LoggerFactory.getLogger(LifeStageProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private LifeStageESService service;

	public DomainES process(LifeStage inBean) throws Exception {

		LifeStageDTO dto = orikaMapper.map(inBean, LifeStageDTO.class);
		DomainES outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
