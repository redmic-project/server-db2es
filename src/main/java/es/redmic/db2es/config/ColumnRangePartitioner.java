package es.redmic.db2es.config;

import java.util.TreeMap;

import javax.sql.DataSource;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

public class ColumnRangePartitioner implements Partitioner {
	
	private JdbcOperations jdbcTemplate;

	private String table;

	private String column;
	
	private String whereClause;

	/**
	 * The name of the SQL table the data are in.
	 *
	 * @param table the name of the table
	 */
	public void setTable(String table) {
		this.table = table;
	}

	/**
	 * The name of the column to partition.
	 *
	 * @param column the column name.
	 */
	public void setColumn(String column) {
		this.column = column;
	}

	public String getWhereClause() {
		return whereClause;
	}

	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

	/**
	 * The data source for connecting to the database.
	 *
	 * @param dataSource a {@link DataSource}
	 */
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * Partition a database table assuming that the data in the column specified
	 * are uniformly distributed. The execution context values will have keys
	 * <code>minValue</code> and <code>maxValue</code> specifying the range of
	 * values to consider in each partition.
	 *
	 * @see Partitioner#partition(int)
	 */
	@Override
	public TreeMap<String, ExecutionContext> partition(int gridSize) {
		
		String clause = " from " + table ;
		if (whereClause != null)
			clause += " where " + whereClause;
		
		int min = 0;
		int max = 0;
		
		try {
			min = jdbcTemplate.queryForObject("SELECT MIN(" + column + ")" + clause , Integer.class);
			max = jdbcTemplate.queryForObject("SELECT MAX(" + column + ")" + clause, Integer.class);
		}
		catch(Exception e) {
			System.out.println("SELECT MIN|MAX(" + column + ")" + clause + " no ha retornado resultados");
		}
		
		int targetSize = (max - min) / gridSize + 1;

		TreeMap<String, ExecutionContext> result = new TreeMap<String, ExecutionContext>();
		int number = 0;
		int start = min;
		int end = start + targetSize - 1;

		while (start <= max) {
			ExecutionContext value = new ExecutionContext();
			result.put("partition" + number, value);

			if (end >= max) {
				end = max;
			}
			value.putInt("start", start);
			value.putInt("end", end);
			start += targetSize;
			end += targetSize;
			number++;
		}

		return result;
	}
}