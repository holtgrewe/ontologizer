package ontologizer.calculation;

import java.util.HashSet;
import java.util.Set;

import ontologizer.association.AssociationContainer;
import ontologizer.enumeration.TermEnumerator;
import ontologizer.ontology.Ontology;
import ontologizer.ontology.TermID;
import ontologizer.set.PopulationSet;
import ontologizer.set.StudySet;
import ontologizer.statistics.Hypergeometric;
import ontologizer.statistics.IPValueCalculation;
import ontologizer.statistics.IPValueCalculationProgress;
import ontologizer.statistics.PValue;
import ontologizer.types.ByteString;

/**
 *
 * This class hides all the details about how the p values are calculated
 * from the multiple test correction.
 *
 * @author Sebastian Bauer
 *
 */
class ParentChildPValuesCalculation implements IPValueCalculation
{
	/*
	 * We basically have the arguments of calculateStudy as fields
	 */
	public Ontology graph;
	public AssociationContainer goAssociations;
	public PopulationSet populationSet;
	public TermEnumerator popTermEnumerator;
	public StudySet observedStudySet;
	public Hypergeometric hyperg;

	private PValue [] calculatePValues(StudySet studySet, IPValueCalculationProgress progress)
	{
		/* We need this to get genes annotated in the study set */
		TermEnumerator studyTermEnumerator = studySet.enumerateTerms(graph,
				goAssociations);

		//PValue p [] = new PValue[populationTermCounter.getTotalNumberOfAnnotatedTerms()];
		PValue p [] = new PValue[popTermEnumerator.getTotalNumberOfAnnotatedTerms()];
		int i=0;

		/* For every term within the goTermCounter */
		for (TermID term : popTermEnumerator)
		{
			// calculating properties of term
			ParentChildGOTermProperties termProp = calculateTerm(term, graph,
					popTermEnumerator, studyTermEnumerator);

			// adding properties to p Vector
			p[i++] = termProp;
		}

		return p;
	}

	public int currentStudySetSize()
	{
		return observedStudySet.getGeneCount();
	}

	public PValue[] calculateRawPValues(IPValueCalculationProgress progress)
	{
		return calculatePValues(observedStudySet, progress);
	}

	public PValue[] calculateRandomPValues(IPValueCalculationProgress progress)
	{
		return calculatePValues(populationSet.generateRandomStudySet(observedStudySet.getGeneCount()), progress);
	}

	private ParentChildGOTermProperties calculateTerm(
			TermID term,
			Ontology graph,
			TermEnumerator popTermEnumerator,
			TermEnumerator studyTermEnumerator)
	{
		// counts annotated to term
		int studyTermCount = studyTermEnumerator.getAnnotatedGenes(term).totalAnnotatedCount();
		int popTermCount = popTermEnumerator.getAnnotatedGenes(term).totalAnnotatedCount();

		// this is what we give back
		ParentChildGOTermProperties prop = new ParentChildGOTermProperties();
		prop.goTerm = graph.getTerm(term);
		prop.annotatedPopulationGenes = popTermCount;
		prop.annotatedStudyGenes = studyTermCount;

		if (graph.isRootTerm(term)) {
			prop.nparents = 0;
			prop.ignoreAtMTC = true;
			prop.p = 1.0;
			prop.p_adjusted = 1.0;
			prop.p_min = 1.0;
		} else {
			// getting parents
			Set<TermID> parents = graph.getTermParents(term);

			// These will hold the names of all genes directly annotated to parents
			HashSet<ByteString> popParentAllGenes = new HashSet<ByteString>();
			HashSet<ByteString> studyParentAllGenes = new HashSet<ByteString>();

			// looping over all parents to get the genes and adding all annotated genes to HashSets
			for (TermID parent : parents)
			{
				popParentAllGenes.addAll(
						popTermEnumerator.getAnnotatedGenes(parent).totalAnnotated
				);
				studyParentAllGenes.addAll(
						studyTermEnumerator.getAnnotatedGenes(parent).totalAnnotated
				);
			}

			// number of genes annotated to family (term and parents)
			int popFamilyCount = popParentAllGenes.size();
			int studyFamilyCount = studyParentAllGenes.size();

			prop.popFamilyGenes = popFamilyCount;
			prop.studyFamilyGenes = studyFamilyCount;
			prop.nparents = parents.size();

			if (studyTermCount != 0) {
				if (popFamilyCount == popTermCount) {
					prop.ignoreAtMTC = true;
					prop.p = 1.0;
					prop.p_adjusted = 1.0;
					prop.p_min = 1.0;
				} else {
					double p = hyperg.phypergeometric(
							popFamilyCount,
							(double)popTermCount / (double)popFamilyCount,
							studyFamilyCount,
							studyTermCount);

					prop.ignoreAtMTC = false;
					prop.p = p;
					prop.p_min = hyperg.dhyper(
							popTermCount,
							popFamilyCount,
							popTermCount,
							popTermCount);
				}
			} else {
				prop.ignoreAtMTC = true;
				prop.p = 1.0;
				prop.p_adjusted = 1.0;
				prop.p_min = 1.0;
			}
		}

		return prop;
	}

	@Override
	public int getNumberOfPValues()
	{
		return popTermEnumerator.getTotalNumberOfAnnotatedTerms();
	}
};