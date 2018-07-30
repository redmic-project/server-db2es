package es.redmic.db2es.jobs.job.indexing.taxonomy.family;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.administrative.taxonomy.model.Family;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.db2es.jobs.job.indexing.taxonomy.classs.ClassProcessor;
import es.redmic.es.administrative.taxonomy.service.FamilyESService;
import es.redmic.models.es.administrative.taxonomy.dto.TaxonDTO;
import es.redmic.models.es.administrative.taxonomy.model.Taxon;

public class FamilyWithValidAsProcessor implements ItemProcessor<Family, Taxon> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClassProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private FamilyESService service;

	public Taxon process(Family inBean) throws Exception {

		TaxonDTO dto = orikaMapper.map(inBean, TaxonDTO.class);

		Taxon outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}
}
