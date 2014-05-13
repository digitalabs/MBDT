package org.icrisat.mbdt.model.sessions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.notifiers.ILoadNotifier;

public class Session {
	
	private static Session session;

	private RootModel rootmodel;
	
	private List<ILoadNotifier> notifiers;
	
	private Session(){
		
	}
	public static Session getInstance(){
		if(session==null){
			session=new Session();
		}
		return session;
	}

	public List<ILoadNotifier> getNotifier(){
		if(notifiers==null){
			notifiers=new ArrayList<ILoadNotifier>();
			}
		return notifiers;
	}
	
	public void addNotifyListener(ILoadNotifier notifier){
		getNotifier().add(notifier);
	}
	
	public RootModel getRootmodel() {
		return rootmodel;
	}
	public void setRootModel(RootModel rootmodel) {
		this.rootmodel = rootmodel;
		fileNotifyChanged(rootmodel);
	}
	private void fileNotifyChanged(RootModel rootmodel) {
		for(Iterator iterator=notifiers.iterator();iterator.hasNext();){
			ILoadNotifier notifier=(ILoadNotifier)iterator.next();
			try{
				notifier.notifyLoad(rootmodel);
			}catch(Exception e){

			}
		}	
	}
}
