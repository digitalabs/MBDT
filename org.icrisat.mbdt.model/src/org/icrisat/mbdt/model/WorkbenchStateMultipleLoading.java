package org.icrisat.mbdt.model;

import java.io.Serializable;

public class WorkbenchStateMultipleLoading implements Serializable {
	
	String projectName;
	String generationName;
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getGenerationName() {
		return generationName;
	}
	public void setGenerationName(String generationName) {
		this.generationName = generationName;
	}
}
