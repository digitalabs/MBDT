package org.icrisat.mbdt.model.sessions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.Chromosome.Chromosome;
import org.icrisat.mbdt.model.notifiers.ILoadNotifier;
import org.icrisat.mbdt.model.notifiers.ILoadNotifierChromosome;

public class SessionChromosome {

	private static SessionChromosome sessionchrom;
	private Chromosome chromosome;
	private List<ILoadNotifierChromosome> notifiers;
	
	private SessionChromosome() {
		
	}
	
	public static SessionChromosome getInstance() {
		if(sessionchrom==null){
			sessionchrom=new SessionChromosome();
		}
		return sessionchrom;
	}
	
	public List<ILoadNotifierChromosome> getNotifier(){
		if(notifiers==null){
			notifiers=new ArrayList<ILoadNotifierChromosome>();
			}
		return notifiers;
	}
	
	public void addNotifyListener(ILoadNotifierChromosome notifier){
		getNotifier().add(notifier);
	}
	
	public Chromosome getChromosome() {
		return chromosome;
	}

	public void setChromosome(Chromosome chrom){
		this.chromosome = chrom;
		fileNotifyChanged(chrom);
	}
	
	private void fileNotifyChanged(Chromosome chrom) {
		if(notifiers==null){
			notifiers=new ArrayList<ILoadNotifierChromosome>();
			}		
		for(Iterator iterator=notifiers.iterator();iterator.hasNext();){
			ILoadNotifierChromosome notifier=(ILoadNotifierChromosome)iterator.next();
			try{
			notifier.notifyLoadChromosome(chrom);
			}catch(Exception e){
			}
		}	
	}
}
