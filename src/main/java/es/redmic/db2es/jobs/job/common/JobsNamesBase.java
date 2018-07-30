package es.redmic.db2es.jobs.job.common;

import java.util.Optional;
import java.util.stream.Stream;

public interface JobsNamesBase<E extends Enum<E>> {
	Class<E> getDeclaringClass();

	default Optional<E> fromString(String name) {
		if (name != null) {
			return Stream.of(getDeclaringClass().getEnumConstants())
				.filter(e -> e.toString().equals(name))
					.findFirst();
		}

		throw new IllegalArgumentException(name + " has no corresponding value");
	}

	public String toString();
	
	
}
