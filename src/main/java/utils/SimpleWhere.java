package utils;

import to.TODateRangeFilter;
import to.TOInputFilter;
import to.TOInputNumberFilter;

public class SimpleWhere {
	public static String queryFilter(String field, TOInputFilter filter) {
		if(filter.getType().equals("contains")) {
			return " AND " + field + " LIKE '%" + filter.getValue() + "%'";
		} else {
			return " AND " + field + " NOT LIKE '%" + filter.getValue() + "%'";
		}
	}
	
	public static String queryFilterNumberRange(String field, TOInputNumberFilter filter) {
		StringBuilder sql = new StringBuilder();
		
		if(filter.getFrom() != null) {
			sql.append(" AND " + field + " >= " + filter.getFrom());
		} 
		
		if (filter.getTo() != null){
			sql.append(" AND " + field + " <= " + filter.getTo());
		}
		
		return sql.toString();
	}
	
	public static String queryFilterDateRange(String field, TODateRangeFilter filter) {
		StringBuilder sql = new StringBuilder();
		
		if(filter.getFrom() != null) {
			sql.append(" AND " + field + " >= '" + filter.getFrom() + "'");
		} 
		
		if (filter.getTo() != null){
			sql.append(" AND " + field + " <= '" + filter.getTo() + "'");
		}
		
		return sql.toString();
	}
}
