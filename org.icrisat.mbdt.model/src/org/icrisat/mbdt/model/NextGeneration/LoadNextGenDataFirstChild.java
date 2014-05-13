package org.icrisat.mbdt.model.NextGeneration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoadNextGenDataFirstChild implements Serializable {

	String targetAcc;
	List<Integer> linkageGrpWidth = new ArrayList<Integer>();
	List<LoadNextGenDataSecondChild> loadNGSecondChild;
	
	public String getTargetAcc() {
		return targetAcc;
	}

	public void setTargetAcc(String targetAcc) {
		this.targetAcc = targetAcc;
	}

	public List<Integer> getLinkageGrpWidth() {
		if(linkageGrpWidth == null){
			linkageGrpWidth = new ArrayList<Integer>();
		}
		return linkageGrpWidth;
	}

	public void setLinkageGrpWidth(List<Integer> linkageGrpWidth) {
		this.linkageGrpWidth = linkageGrpWidth;
	}

	public List<LoadNextGenDataSecondChild> getLoadNGSecondChild() {
		if(loadNGSecondChild == null){
			loadNGSecondChild = new ArrayList<LoadNextGenDataSecondChild>();
		}
		return loadNGSecondChild;
	}
}
