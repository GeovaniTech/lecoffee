package utils;

import to.TOInputFilter;

public class SimpleWhere {
	public static String queryFilter(String field, TOInputFilter filter) {
		if(filter.getType().equals("contains")) {
			return " AND " + field + " LIKE '%" + filter.getValue() + "%'";
		} else {
			return " AND " + field + " NOT LIKE '%" + filter.getValue() + "%'";
		}
	}
}
