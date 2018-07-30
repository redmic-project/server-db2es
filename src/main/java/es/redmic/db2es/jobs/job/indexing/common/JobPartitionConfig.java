package es.redmic.db2es.jobs.job.indexing.common;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;

import es.redmic.databaselib.common.model.SuperModel;
import es.redmic.db2es.config.ColumnRangePartitioner;
import es.redmic.db2es.jobs.job.common.JobBase;
import es.redmic.db2es.jobs.job.common.JobIncrementer;
import es.redmic.models.es.common.dto.BaseDTO;
import es.redmic.models.es.common.model.BaseES;

public abstract class JobPartitionConfig<TModel extends SuperModel, TDTO extends BaseDTO<?>, TModelES extends BaseES<?>>
		extends JobBase {

	private final String FIELD = "id";

	@Value("${db2es.reader.PAGE_SIZE}")
	protected int PAGE_SIZE;

	@Value("${db2es.partition.GRID_SIZE}")
	Integer GRID_SIZE;

	@Value("${db2es.writer.COMMIT_INTERVAL}")
	protected Integer COMMIT_INTERVAL;

	@Autowired
	TaskExecutor taskExecutor;

	@Autowired
	DataSource primaryDataSource;

	String name;

	public JobPartitionConfig(String name) {

		this.name = name;
	}

	// @formatter:off
	
	protected Job createJobIndexing(String name, Step partitionalStep) {
		
		return jobBuilderFactory.get("indexing-" + name + "-job")
			.listener(jobListener)
			.incrementer(new JobIncrementer())
			.start(partitionalStep)
			.build();
	}
	
	protected Step createPartitionStep(String name, Partitioner partitioner, Step step) {

		return stepBuilderFactory.get("partition-" + name + "-step")
			.partitioner("indexing-" + name + "-step", partitioner)
			.step(step)
			.taskExecutor(taskExecutor)
			.gridSize(GRID_SIZE)
			.build();
	}
	
	// @formatter:on

	public ColumnRangePartitioner createPartitioner(String table) {
		
		return createPartitioner(table, null);
	}

	public ColumnRangePartitioner createPartitioner(String table, String whereClause) {

		ColumnRangePartitioner partitioner = new ColumnRangePartitioner();
		partitioner.setColumn(FIELD);
		partitioner.setTable(table);
		
		if (whereClause != null)
			partitioner.setWhereClause(whereClause);
		
		partitioner.setDataSource(primaryDataSource);

		return partitioner;
	}
}
