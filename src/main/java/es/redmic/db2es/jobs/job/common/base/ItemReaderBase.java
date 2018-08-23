package es.redmic.db2es.jobs.job.common.base;

import java.io.Serializable;
import java.util.Collections;

import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.data.domain.Sort.Direction;

import es.redmic.databaselib.common.model.SuperModel;
import es.redmic.databaselib.common.repository.BaseRepository;

public class ItemReaderBase<TModel extends SuperModel>
		extends RepositoryItemReader<TModel> {

	public ItemReaderBase(BaseRepository<TModel, ? extends Serializable> repository, Integer pageSize) {
		this.setRepository(repository);
		this.setPageSize(pageSize);
		this.setMethodName("findAll");
		this.setSort(Collections.singletonMap("id", Direction.ASC));
	}
}
