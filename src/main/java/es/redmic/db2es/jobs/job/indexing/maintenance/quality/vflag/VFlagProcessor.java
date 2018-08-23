package es.redmic.db2es.jobs.job.indexing.maintenance.quality.vflag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.quality.model.VFlag;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.quality.service.VFlagESService;
import es.redmic.models.es.maintenance.quality.dto.VFlagDTO;

public class VFlagProcessor implements ItemProcessor<VFlag, es.redmic.models.es.maintenance.quality.model.VFlag> {

	private static final Logger LOGGER = LoggerFactory.getLogger(VFlagProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private VFlagESService service;

	public es.redmic.models.es.maintenance.quality.model.VFlag process(VFlag inBean) throws Exception {

		VFlagDTO dto = orikaMapper.map(inBean, VFlagDTO.class);
		es.redmic.models.es.maintenance.quality.model.VFlag outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
