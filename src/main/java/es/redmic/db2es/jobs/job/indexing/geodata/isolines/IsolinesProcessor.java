package es.redmic.db2es.jobs.job.indexing.geodata.isolines;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.geodata.isolines.model.Isolines;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.geodata.isolines.service.IsolinesESService;
import es.redmic.models.es.geojson.common.model.GeoMultiLineStringData;
import es.redmic.models.es.geojson.isolines.dto.IsolinesDTO;

public class IsolinesProcessor
		implements ItemProcessor<Isolines, GeoMultiLineStringData> {

	private static final Logger log = LoggerFactory.getLogger(IsolinesProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private IsolinesESService service;

	@Override
	public GeoMultiLineStringData process(Isolines isolinesIn) throws Exception {

		IsolinesDTO isolinesDto = orikaMapper.map(isolinesIn, IsolinesDTO.class);

		GeoMultiLineStringData isolinesOut = service.mapper(isolinesDto);

		log.info("Converting (" + isolinesIn + ") into (" + isolinesOut + ")");

		return isolinesOut;
	}
}
