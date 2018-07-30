package es.redmic.db2es.jobs.job.indexing.maintenance.administrative.documenttype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.administrative.model.DocumentType;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.domain.administrative.service.DocumentTypeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.administrative.dto.DocumentTypeDTO;

public class DocumentTypeProcessor implements ItemProcessor<DocumentType, DomainES> {

	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentTypeProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private DocumentTypeESService service;

	@Override
	public DomainES process(DocumentType inBean) throws Exception {

		DocumentTypeDTO dto = orikaMapper.map(inBean, DocumentTypeDTO.class);
		DomainES outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
