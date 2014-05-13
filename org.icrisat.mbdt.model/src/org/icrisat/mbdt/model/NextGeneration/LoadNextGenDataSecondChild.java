package org.icrisat.mbdt.model.NextGeneration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class LoadNextGenDataSecondChild implements Serializable {
	
	List<String> NGTargetMarkerName = new ArrayList<String>();
	List<String> NGTargetalleles = new ArrayList<String>();
	List<String> NGTargetallelesChar = new ArrayList<String>();
	List<String> NGTcolorValue = new ArrayList<String>();
	List<Integer> NGTWidth = new ArrayList<Integer>();
	LinkedHashMap<String, String> NGTcolorValuesHashMap = new LinkedHashMap<String, String>();
	int NGTHeight;
	int NGTLinkageGrpcount = 0;
	
	public List<String> getNGTargetMarkerName() {
		if(NGTargetMarkerName == null){
			NGTargetMarkerName = new ArrayList<String>();
		}
		return NGTargetMarkerName;
	}
	public void setNGTargetMarkerName(List<String> targetMarkerName) {
		NGTargetMarkerName = targetMarkerName;
	}
	public List<String> getNGTargetalleles() {
		if(NGTargetalleles == null){
			NGTargetalleles = new ArrayList<String>();
		}
		return NGTargetalleles;
	}
	public void setNGTargetalleles(List<String> targetalleles) {
		NGTargetalleles = targetalleles;
	}
	public List<String> getNGTcolorValue() {
		if(NGTcolorValue == null){
			NGTcolorValue = new ArrayList<String>();
		}
		return NGTcolorValue;
	}
	public void setNGTcolorValue(List<String> tcolorValue) {
		NGTcolorValue = tcolorValue;
	}
	public List<Integer> getNGTWidth() {
		if(NGTWidth == null){
			NGTWidth = new ArrayList<Integer>();
		}
		return NGTWidth;
	}
	public void setNGTWidth(List<Integer> width) {
		NGTWidth = width;
	}
	public int getNGTHeight() {
		return NGTHeight;
	}
	public void setNGTHeight(int height) {
		NGTHeight = height;
	}
	public int getNGTLinkageGrpcount() {
		return NGTLinkageGrpcount;
	}
	public void setNGTLinkageGrpcount(int linkageGrpcount) {
		NGTLinkageGrpcount = linkageGrpcount;
	}
	public LinkedHashMap<String, String> getNGTcolorValuesHashMap() {
		return NGTcolorValuesHashMap;
	}
	public void setNGTcolorValuesHashMap(
			LinkedHashMap<String, String> tcolorValuesHashMap) {
		NGTcolorValuesHashMap = tcolorValuesHashMap;
	}
	public List<String> getNGTargetallelesChar() {
		if(NGTargetallelesChar == null){
			NGTargetallelesChar = new ArrayList<String>();
		}
		return NGTargetallelesChar;
	}
	public void setNGTargetallelesChar(List<String> targetallelesChar) {
		NGTargetallelesChar = targetallelesChar;
	}
	
}
