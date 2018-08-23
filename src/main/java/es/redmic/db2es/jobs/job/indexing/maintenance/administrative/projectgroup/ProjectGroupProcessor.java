package es.redmic.db2es.jobs.job.indexing.maintenance.administrative.projectgroup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.db.maintenance.administrative.model.ProjectGroup;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.es.maintenance.domain.administrative.service.ProjectGroupESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.administrative.dto.ProjectGroupDTO;

public class ProjectGroupProcessor implements ItemProcessor<ProjectGroup, DomainES> {
	 
	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectGroupProcessor.class);
	
	@Autowired
	private OrikaScanBean orikaMapper;
	
	@Autowired
	private ProjectGroupESService service;
	
	public DomainES process(ProjectGroup inBean) throws Exception {
		
		ProjectGroupDTO dto = orikaMapper.map(inBean, ProjectGroupDTO.class);
		DomainES outBean = service.mapper(dto);
		
		LOGGER.info("Converting (" + inBean + ") into (" + outBean + ")");

		return outBean;
	}


}
