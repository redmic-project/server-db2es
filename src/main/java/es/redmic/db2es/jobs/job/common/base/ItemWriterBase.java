package es.redmic.db2es.jobs.job.common.base;

import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.ItemWriter;

import es.redmic.es.data.common.service.RWDataESService;
import es.redmic.models.es.common.model.BaseES;

public abstract class ItemWriterBase<TModelES extends BaseES<?>> extends StepExecutionListenerSupport
		implements ItemWriter<TModelES> {

	protected RWDataESService<TModelES, ?> service;

	public ItemWriterBase(RWDataESService<TModelES, ?> service) {
		this.service = service;
	}
}