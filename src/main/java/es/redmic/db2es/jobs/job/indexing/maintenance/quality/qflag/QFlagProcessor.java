package es.redmic.db2es.jobs.job.indexing.maintenance.quality.qflag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.quality.model.QFlag;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.quality.service.QFlagESService;
import es.redmic.models.es.maintenance.quality.dto.QFlagDTO;

public class QFlagProcessor implements ItemProcessor<QFlag, es.redmic.models.es.maintenance.quality.model.QFlag> {

	private static final Logger LOGGER = LoggerFactory.getLogger(QFlagProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private QFlagESService service;

	public es.redmic.models.es.maintenance.quality.model.QFlag process(QFlag inBean) throws Exception {

		QFlagDTO dto = orikaMapper.map(inBean, QFlagDTO.class);
		es.redmic.models.es.maintenance.quality.model.QFlag outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
