package es.redmic.db2es.jobs.job.indexing.maintenance.administrative.country;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.administrative.model.Country;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.domain.administrative.service.CountryESService;
import es.redmic.models.es.maintenance.administrative.dto.CountryDTO;

public class CountryProcessor
		implements ItemProcessor<Country, es.redmic.models.es.maintenance.administrative.model.Country> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CountryProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private CountryESService service;

	public es.redmic.models.es.maintenance.administrative.model.Country process(Country inBean) throws Exception {

		CountryDTO dto = orikaMapper.map(inBean, CountryDTO.class);
		es.redmic.models.es.maintenance.administrative.model.Country outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
