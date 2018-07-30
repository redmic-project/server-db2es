package es.redmic.db2es.base.service;

import java.lang.reflect.ParameterizedType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.transaction.annotation.Transactional;

import es.redmic.databaselib.common.model.LongModel;
import es.redmic.databaselib.common.repository.BaseRepository;
import es.redmic.db2es.config.OrikaScanBean;
import es.redmic.exception.database.DBNotFoundException;
import es.redmic.models.es.common.dto.DTO;
import es.redmic.models.es.common.dto.DTOEvent;

public abstract class ServiceRImpl<TModel extends LongModel, TDTO extends DTO>
		implements IServiceR<TModel, TDTO>, ApplicationEventPublisherAware {

	@Autowired
	protected OrikaScanBean factory;

	private ApplicationEventPublisher eventPublisher;

	protected Class<TDTO> typeOfTDTO;
	protected Class<TDTO> typeOfTModel;

	protected BaseRepository<TModel, Long> repository;

	protected static String DELETE_EVENT = "DELETE";
	protected static String ADD_EVENT = "ADD";
	protected static String UPDATE_EVENT = "UPDATE";

	@SuppressWarnings("unchecked")
	@Autowired
	public ServiceRImpl(BaseRepository<TModel, Long> repository) {
		this.repository = repository;
		this.typeOfTModel = (Class<TDTO>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		this.typeOfTDTO = (Class<TDTO>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[1];
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

	public void publish(String eventType, TDTO result) {

		this.eventPublisher.publishEvent(new DTOEvent(this, eventType, result));
	}

	public TDTO findById(Long id) {
		 TModel model = findOne(id);
		 
		 return convertModelToDto(model);
	}
	
	@Transactional(readOnly = true, rollbackFor = { DBNotFoundException.class })
	private TModel findOne(Long id) {
		TModel model = repository.getOne(id);
		if (model == null) {
			throw new DBNotFoundException("id", String.valueOf(id));
		}
		
		return model;
	}
	
	public TDTO convertModelToDto(TModel model) {
		TDTO dto = (TDTO) factory.map(model, typeOfTDTO);
		return dto;
	}
}
