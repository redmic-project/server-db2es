package es.redmic.db2es.jobs.job.indexing.maintenance.administrative.activityrank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.administrative.model.ActivityRank;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.domain.administrative.service.ActivityRankESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.administrative.dto.ActivityRankDTO;

public class ActivityRankProcessor implements ItemProcessor<ActivityRank, DomainES> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityRankProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private ActivityRankESService service;

	public DomainES process(ActivityRank inBean) throws Exception {

		ActivityRankDTO dto = orikaMapper.map(inBean, ActivityRankDTO.class);
		DomainES outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
