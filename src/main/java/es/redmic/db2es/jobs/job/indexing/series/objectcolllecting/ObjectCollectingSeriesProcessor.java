package es.redmic.db2es.jobs.job.indexing.series.objectcolllecting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.series.objectcollecting.model.ObjectCollecting;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.series.objectcollecting.service.ObjectCollectingSeriesESService;
import es.redmic.models.es.series.objectcollecting.dto.ObjectCollectingSeriesDTO;
import es.redmic.models.es.series.objectcollecting.model.ObjectCollectingSeries;

public class ObjectCollectingSeriesProcessor implements ItemProcessor<ObjectCollecting, ObjectCollectingSeries> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ObjectCollectingSeriesProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private ObjectCollectingSeriesESService service;

	public ObjectCollectingSeries process(ObjectCollecting inBean) throws Exception {

		ObjectCollectingSeriesDTO tempDto = orikaMapper.map(inBean, ObjectCollectingSeriesDTO.class);

		ObjectCollectingSeries outBean = service.mapper(tempDto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
