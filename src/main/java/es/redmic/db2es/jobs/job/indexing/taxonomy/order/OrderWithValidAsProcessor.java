package es.redmic.db2es.jobs.job.indexing.taxonomy.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.administrative.taxonomy.model.Orderr;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.db2es.jobs.job.indexing.taxonomy.classs.ClassProcessor;
import es.redmic.es.administrative.taxonomy.service.OrderESService;
import es.redmic.models.es.administrative.taxonomy.dto.TaxonDTO;
import es.redmic.models.es.administrative.taxonomy.model.Taxon;

public class OrderWithValidAsProcessor implements ItemProcessor<Orderr, Taxon> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClassProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private OrderESService service;

	public Taxon process(Orderr inBean) throws Exception {

		TaxonDTO dto = orikaMapper.map(inBean, TaxonDTO.class);

		Taxon outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}
}
