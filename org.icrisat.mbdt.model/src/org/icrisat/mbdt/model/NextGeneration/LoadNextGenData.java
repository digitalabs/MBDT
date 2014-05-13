package org.icrisat.mbdt.model.NextGeneration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoadNextGenData implements Serializable {

	String targetAcc;
	Object targetAccObject;
	String targetType;
	
	List<LoadNextGenDataFirstChild> listtargetAcc;

	public String getTargetAcc() {
		return targetAcc;
	}

	public void setTargetAcc(String targetAcc) {
		this.targetAcc = targetAcc;
	}

	public Object getTargetAccObject() {
		return targetAccObject;
	}

	public void setTargetAccObject(Object targetAccObject) {
		this.targetAccObject = targetAccObject;
	}

	public List<LoadNextGenDataFirstChild> getListtargetAcc() {
		if(listtargetAcc == null){
			listtargetAcc = new ArrayList<LoadNextGenDataFirstChild>();			
		}
		return listtargetAcc;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
}
