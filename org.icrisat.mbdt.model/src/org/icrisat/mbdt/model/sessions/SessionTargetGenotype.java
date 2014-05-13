package org.icrisat.mbdt.model.sessions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.icrisat.mbdt.model.TargetGenotype.TargetGeno;
import org.icrisat.mbdt.model.notifiers.ILoadNotifierTargetGenotype;

public class SessionTargetGenotype {

	private static SessionTargetGenotype sessionTg;
	private TargetGeno targetGeno;
	private List<ILoadNotifierTargetGenotype> notifiers;
	
	private SessionTargetGenotype() {
		
	}
	
	public static SessionTargetGenotype getInstance() {
		if(sessionTg==null){
			sessionTg=new SessionTargetGenotype();
		}
		return sessionTg;
	}
	
	public List<ILoadNotifierTargetGenotype> getNotifier(){
		if(notifiers==null){
			notifiers=new ArrayList<ILoadNotifierTargetGenotype>();
			}
		return notifiers;
	}
	
	public void addNotifyListener(ILoadNotifierTargetGenotype notifier){
		getNotifier().add(notifier);
	}
	
	public TargetGeno getTargetGeno() {
		return targetGeno;
	}
	
	public void setTargetGeno(TargetGeno targetGeno) {
		this.targetGeno = targetGeno;
		fileNotifyChanged(targetGeno);
	}
	
	private void fileNotifyChanged(TargetGeno targetGeno) {
		for(Iterator iterator=notifiers.iterator();iterator.hasNext();){
			ILoadNotifierTargetGenotype notifier=(ILoadNotifierTargetGenotype)iterator.next();
			try{
			notifier.notifyLoadTargetGenotype(targetGeno);
			}catch(Exception e){
			}
		}	
	}
}
