package utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

public abstract class AbstractLov<to, filter> {		
	public List<to> getDataPage() {
		return null;
	}
	
	public String getPathLovXhtml() {
		return null;
	}
	
	public void openLov() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("modal", true);
		parameters.put("resizable", false);
		parameters.put("contentWidth", "65vw");
		parameters.put("contentHeight", "65vh");
		parameters.put("draggable", false);
		parameters.put("widgetVar", "lov");
		parameters.put("id", "lov");
		
		PrimeFaces.current().dialog().openDynamic(getPathLovXhtml(), parameters, null);	
	}
	
	public void closeLov() {
		PrimeFaces.current().dialog().closeDynamic("Lov closed");
	}
	
	public void clearFilters() {
		
	}
	
	public void onRowSelect(SelectEvent<to> event) {
		
	}
}
