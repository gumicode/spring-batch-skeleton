package com.example.skeleton.common.batch.partition;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.util.Optional.ofNullable;

public class QuerydslNoOffsetPartitioner<T> implements Partitioner {

	private final Function<JPAQueryFactory, JPAQuery<Long>> minQuery;
	private final Function<JPAQueryFactory, JPAQuery<Long>> maxQuery;
	private final JPAQueryFactory queryFactory;

	public QuerydslNoOffsetPartitioner(
			EntityManagerFactory entityManagerFactory,
			Function<JPAQueryFactory, JPAQuery<Long>> minQuery,
			Function<JPAQueryFactory, JPAQuery<Long>> maxQuery) {
		this.queryFactory = new JPAQueryFactory(entityManagerFactory.createEntityManager());
		this.minQuery = minQuery;
		this.maxQuery = maxQuery;
	}

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {

		long min = ofNullable(minQuery.apply(queryFactory).fetchFirst()).orElse(0L);
		long max = ofNullable(maxQuery.apply(queryFactory).fetchFirst()).orElse(0L);
		long targetSize = (max - min) / (long) gridSize + 1;

		Map<String, ExecutionContext> result = new HashMap<>();
		long number = 0;
		long start = min;
		long end = start + targetSize - 1;

		while (start <= max) {
			ExecutionContext value = new ExecutionContext();
			result.put("partition" + number, value);

			if (end >= max) {
				end = max;
			}
			value.putLong("start", start);
			value.putLong("end", end);
			value.putLong("number", number);
			start += targetSize;
			end += targetSize;
			number++;
		}

		return result;
	}
}
