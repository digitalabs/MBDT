package org.icrisat.mbdt.model;

public class WorkbenchImportState {
	String genotypeFile;
	String linkageFile;
	String qtlFileName;
	
	public String getGenotypeFile() {
		return genotypeFile;
	}
	public void setGenotypeFile(String genotypeFile) {
		this.genotypeFile = genotypeFile;
	}
	public String getLinkageFile() {
		return linkageFile;
	}
	public void setLinkageFile(String linkageFile) {
		this.linkageFile = linkageFile;
	}
	public String getQtlFileName() {
		return qtlFileName;
	}
	public void setQtlFileName(String qtlFileName) {
		this.qtlFileName = qtlFileName;
	}
}
