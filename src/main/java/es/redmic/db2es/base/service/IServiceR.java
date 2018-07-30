package es.redmic.db2es.base.service;

import es.redmic.databaselib.common.model.LongModel;
import es.redmic.models.es.common.dto.DTO;

public interface IServiceR<TModel extends LongModel, TDTO extends DTO> {

	public TDTO findAll();
	
	public TDTO convertModelToDto(TModel model);
	
	public TDTO findById(Long id);
}
