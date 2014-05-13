package org.icrisat.mbdt.model.Chromosome;

	import java.io.Serializable;
	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.HashSet;
	import java.util.List;

import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.Datavalidation.DataValidation;
import org.icrisat.mbdt.model.GenotypeModel.Genotype;
import org.icrisat.mbdt.model.LinkageMapModel.LinkageMap;
import org.icrisat.mbdt.model.NextGeneration.LoadNextGenData;
import org.icrisat.mbdt.model.PhenotypeModel.Phenotype;
import org.icrisat.mbdt.model.QTLModel.QTL;
import org.icrisat.mbdt.model.TargetGenotype.TargetGeno;

	public class Chromosome implements Serializable {
		
		List<LinkageMap> linkagemap;
		List<QTL> qtl;
		List<Genotype> genotype;
		List<Phenotype> phenotype;
		List<LoadNextGenData> loadNextGen;
		String name;
		private Chromosome()
		{
			// no code req'd
		}
		public static Chromosome getChromosome()
		{
			if (ref == null)
				// it's ok, we can call this constructor
				ref = new Chromosome();
			return ref;
		}	
		
		private static Chromosome ref;
		public List<LinkageMap> getLinkagemap() {
			if(linkagemap==null){
				linkagemap=new ArrayList<LinkageMap>();
			}
			return linkagemap;
		}
		public void setLinkagemap(List<LinkageMap> linkagemap) {
			this.linkagemap = linkagemap;
		}
		public List<LoadNextGenData> getLoadNextGen() {
			if(loadNextGen == null){
				loadNextGen = new ArrayList<LoadNextGenData>();			
			}
			return loadNextGen;
		}

		public void setLoadNextGen(List<LoadNextGenData> loadNextGen) {
			this.loadNextGen = loadNextGen;
		}
		public List<QTL> getQtl() {
			if(qtl==null){
				qtl=new ArrayList<QTL>();
			}	
			return qtl;
		}
		public void setQtl(List<QTL> qtl) {
			this.qtl = qtl;
		}
		public List<Genotype> getGenotype() {
			if(genotype==null){
				genotype=new ArrayList<Genotype>();
			}
			return genotype;
		}
		public void setGenotype(List<Genotype> genotype) {
			this.genotype = genotype;
		}
		public List<Phenotype> getPhenotype() {
			return phenotype;
		}
		public void setPhenotype(List<Phenotype> phenotype) {
			this.phenotype = phenotype;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		
		
		
	}