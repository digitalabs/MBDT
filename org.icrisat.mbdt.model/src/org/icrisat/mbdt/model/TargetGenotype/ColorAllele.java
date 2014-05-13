package org.icrisat.mbdt.model.TargetGenotype;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ColorAllele implements Serializable {

	String selectedParents;
	int rectanglePositionX;
	int rectanglePositionY;
	int width;
	int height;
	String chromosome;
	String alleleName;
	String markerName;
	String targetAlleleValue;
	
	HashMap<String, String> targetDraggedValue = new HashMap<String, String>();
	List<String> targetCharAlleleValues =new ArrayList<String>();
	
	public String getTargetAlleleValue() {
		return targetAlleleValue;
	}
	public void setTargetAlleleValue(String targetAlleleValue) {
		this.targetAlleleValue = targetAlleleValue;
	}
	public String getMarkerName() {
		return markerName;
	}
	public void setMarkerName(String markerName) {
		this.markerName = markerName;
	}
	public String getSelectedParents() {
		return selectedParents;
	}
	public void setSelectedParents(String selectedParents) {
		this.selectedParents = selectedParents;
	}
	public int getRectanglePositionX() {
		return rectanglePositionX;
	}
	public void setRectanglePositionX(int rectanglePositionX) {
		this.rectanglePositionX = rectanglePositionX;
	}
	public int getRectanglePositionY() {
		return rectanglePositionY;
	}
	public void setRectanglePositionY(int rectanglePositionY) {
		this.rectanglePositionY = rectanglePositionY;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getChromosome() {
		return chromosome;
	}
	public void setChromosome(String chromosome) {
		this.chromosome = chromosome;
	}
	public String getAlleleName() {
		return alleleName;
	}
	public void setAlleleName(String alleleName) {
		this.alleleName = alleleName;
	}
	public HashMap<String, String> getTargetDraggedValue() {
		return targetDraggedValue;
	}
	public void setTargetDraggedValue(HashMap<String, String> targetDraggedValue) {
		this.targetDraggedValue = targetDraggedValue;
	}
	public List<String> getTargetCharAlleleValues() {
		if(targetCharAlleleValues == null){
			targetCharAlleleValues = new ArrayList<String>();
		}
		return targetCharAlleleValues;
	}
	public void setTargetCharAlleleValues(List<String> targetCharAlleleValues) {
		this.targetCharAlleleValues = targetCharAlleleValues;
	}
	
}
