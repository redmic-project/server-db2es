package es.redmic.db2es.jobs.job.indexing.document;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.administrative.model.Document;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.administrative.service.DocumentESService;
import es.redmic.models.es.administrative.dto.DocumentDTO;

public class DocumentProcessor implements ItemProcessor<Document, es.redmic.models.es.administrative.model.Document> {

	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private DocumentESService service;

	public es.redmic.models.es.administrative.model.Document process(Document inBean) throws Exception {

		DocumentDTO dto = orikaMapper.map(inBean, DocumentDTO.class);
		es.redmic.models.es.administrative.model.Document outBean = service.mapper(dto);

		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}
}

