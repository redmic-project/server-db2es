package es.redmic.db2es.jobs.job.indexing.administrative.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.administrative.model.Project;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.administrative.service.ProjectESService;
import es.redmic.models.es.administrative.dto.ProjectDTO;

public class ProjectProcessor implements ItemProcessor<Project, es.redmic.models.es.administrative.model.Project> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectProcessor.class);

	@Autowired
	private OrikaScanBean orikaMapper;

	@Autowired
	private ProjectESService service;

	public es.redmic.models.es.administrative.model.Project process(Project inBean) throws Exception {

		ProjectDTO dto = orikaMapper.map(inBean, ProjectDTO.class);
		es.redmic.models.es.administrative.model.Project outBean = service.mapper(dto);
		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}

}
