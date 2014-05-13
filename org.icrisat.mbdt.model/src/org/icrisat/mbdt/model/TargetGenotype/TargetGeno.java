package org.icrisat.mbdt.model.TargetGenotype;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TargetGeno implements Serializable {

	List<Parents> parents;
	int position;
	int positionParents;
	int markerCount;
	List<String> Movements;
	String tcreate;
	String loadTarget;

	public String getLoadTarget() {
		return loadTarget;
	}

	public void setLoadTarget(String loadTarget) {
		this.loadTarget = loadTarget;
	}
	
	public int getMarkerCount() {
		return markerCount;
	}

	public void setMarkerCount(int markerCount) {
		this.markerCount = markerCount;
	}
	
	public int getPositionParents() {
		return positionParents;
	}

	public void setPositionParents(int positionParents) {
		this.positionParents = positionParents;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public List<Parents> getParents() {
		if(parents== null) {
			parents= new ArrayList<Parents>();
		}
		return parents;
	}

	public void setParents(List<Parents> parents) {
		this.parents = parents;
	}

	private TargetGeno()
	{
		// no code req'd
	}
	
	public static TargetGeno getTargetGeno()
	{
		if (ref == null)
			// it's ok, we can call this constructor
			ref = new TargetGeno();
		return ref;
	}

	private static TargetGeno ref;

	public List<String> getMovements() {
		return Movements;
	}

	public void setMovements(List<String> movements) {
		Movements = movements;
	}

	public String getTcreate() {
		return tcreate;
	}

	public void setTcreate(String tcreate) {
		this.tcreate = tcreate;
	}

}
