package org.icrisat.mbdt.model;

import java.io.Serializable;
import java.util.List;

import org.icrisat.mbdt.model.Chromosome.Chromosome;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;
import org.icrisat.mbdt.model.GenotypeModel.Genotype;
import org.icrisat.mbdt.model.GenotypeModel.SelectedAccessions;
import org.icrisat.mbdt.model.GenotypeModel.SelectedAccessions1;
import org.icrisat.mbdt.model.TargetGenotype.TargetGeno;

public class WorkbenchState implements Serializable {
	
	RootModel rootModelSer;
	LinkageData linkData;
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	Qtl_MapData qtlMapData;
	TargetGeno targetGeno;
	Chromosome chromsome;
	 public Chromosome getChromsome() {
		return chromsome;
	}

	public void setChromsome(Chromosome chromsome) {
		this.chromsome = chromsome;
	}

	static final long serialVersionUID = 2L;
	public TargetGeno getTargetGeno() {
		return targetGeno;
	}

	public void setTargetGeno(TargetGeno targetGeno) {
		this.targetGeno = targetGeno;
	}

	public LinkageData getLinkData() {
		return linkData;
	}

	public void setLinkData(LinkageData linkData) {
		this.linkData = linkData;
	}

	public RootModel getRootModelSer() {
		return rootModelSer;
	}

	public void setRootModelSer(RootModel rootModelSer) {
		this.rootModelSer = rootModelSer;
	}

	public Qtl_MapData getQtlMapData() {
		return qtlMapData;
	}

	public void setQtlMapData(Qtl_MapData qtlMapData) {
		this.qtlMapData = qtlMapData;
	}	
	
}
