package com.nghiale.api.utils;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class QueryCreator {
	private static final String FETCH_GRAPH_HINTS = "javax.persistence.fetchgraph";

	public static Query createFindQuery(String graphName, Long id, EntityManager em) {
		StringBuilder builder = new StringBuilder("SELECT e FROM ")
				.append(graphName.substring(0, graphName.indexOf('.'))).append(" e ").append("WHERE e.id = :id");
		Query query = em.createQuery(builder.toString()).setParameter("id", id).setHint(FETCH_GRAPH_HINTS,
				em.createEntityGraph(graphName));
		return query;
	}

	public static Query createFindAllQuery(String graphName, EntityManager em) {
		StringBuilder builder = new StringBuilder("SELECT e FROM ")
				.append(graphName.substring(0, graphName.indexOf('.'))).append(" e");
		Query query = em.createQuery(builder.toString()).setHint(FETCH_GRAPH_HINTS, em.createEntityGraph(graphName));
		return query;
	}
}
