package es.redmic.db2es.jobs.job.indexing.geodata.area;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.geodata.area.model.Area;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.geodata.area.service.AreaESService;
import es.redmic.models.es.geojson.area.dto.AreaDTO;
import es.redmic.models.es.geojson.common.model.GeoMultiPolygonData;

public class AreaProcessor implements ItemProcessor<Area, GeoMultiPolygonData> {

	private static final Logger log = LoggerFactory.getLogger(AreaProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private AreaESService service;

	@Override
	public GeoMultiPolygonData process(Area areaIn) throws Exception {

		AreaDTO areaDto = orikaMapper.map(areaIn, AreaDTO.class);

		GeoMultiPolygonData areaOut = service.mapper(areaDto);

		log.info("Converting (" + areaIn + ") into (" + areaOut + ")");

		return areaOut;
	}
}
