package es.redmic.db2es;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class LogItemProcessor<T> implements ItemProcessor<T, T> {

	private static final Logger LOGGER  =  LoggerFactory.getLogger(LogItemProcessor.class);

	public T process(T item) throws Exception {
		LOGGER.info(item.toString());
		return item;
	}
}
