package org.icrisat.mbdt.model.notifiers;

import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.Chromosome.Chromosome;

public interface ILoadNotifierChromosome {

	public void notifyLoadChromosome(Chromosome cView);
//	public void notifyLoadChromosome(RootModel cView);
}