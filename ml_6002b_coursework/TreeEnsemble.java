package ml_6002b_coursework;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.RandomSubset;

public class TreeEnsemble extends AbstractClassifier {
    private int _numTrees = 50;
    // private ArrayList<CourseworkTree> _treeEnsemble = new ArrayList<>(_numTrees);
    private CourseworkTree[] _treeEnsemble = new CourseworkTree[_numTrees];
    private Classifier _classifier;
    
    public void setClassifier(Classifier classifier){
        _classifier = classifier;
    }

    public double majorityVote(Instance instance)  {
        double[] classCounters = new double[instance.numClasses()];
        for (int i = 0; i < _treeEnsemble.length; i++) {
            //Is always 0? the if statement is always false.
            int predictedClassIndex = (int)_treeEnsemble[i].classifyInstance(instance);
            classCounters[predictedClassIndex]+=1;
        }

        double largestClassCounter = -1;
        double largestClassCounterIndex = -1;
        for (int i = 0;i<classCounters.length; i++)
        {
            if (classCounters[i] > largestClassCounter)
            {
                largestClassCounter = classCounters[i];
                largestClassCounterIndex = i;
            }
        }

        return largestClassCounterIndex;
    }

    @Override
    public void buildClassifier(Instances instances) throws Exception {
        Random rand = new Random(1); //TODO: REMOVE USING A SEED

        double attributeProportionSelected = 0.5;

        //Attribute selection Process & stopping conditions
        int maxDepth = Integer.MAX_VALUE;
        maxDepth = rand.nextInt(Integer.MAX_VALUE);
        double randomSplitValue = rand.nextDouble();

        RandomSubset randomSubset;

        int classifierChoice;

        String[] optionsIGUseGain = {"-asm", "IGAttributeSplitMeasure", "-U", "" + true, "-S", "" + randomSplitValue, "-depth", "" + maxDepth};
        String[] optionsIG = {"-asm", "IGAttributeSplitMeasure", "-U", "" + false, "-S", "" + randomSplitValue, "-depth", "" + maxDepth};
        String[] optionsGini = {"-asm", "GiniAttributeSplitMeasure", "-S", "" + randomSplitValue, "-depth", "" + maxDepth};
        String[] optionsChiSquared = {"-asm", "ChiSquaredAttributeSplitMeasure", "-S", "" + randomSplitValue, "-depth", "" + maxDepth};

        for (int i = 0;i< _treeEnsemble.length;i++) {
            randomSubset = new RandomSubset();
            randomSubset.setNumAttributes(attributeProportionSelected);
            randomSubset.setSeed(2);//TODO: Remove this
            // randomSubset.setSeed(rand.nextInt(Integer.MAX_VALUE));
            randomSubset.setInputFormat(instances);
            Instances instancesSubset = Filter.useFilter(instances, randomSubset);

            // printInstanceSubset(instancesSubset);//Evidence of Testing
            
            classifierChoice = rand.nextInt(4);
            _treeEnsemble[i] = new CourseworkTree();

            switch (classifierChoice) {
                case 0:
                _treeEnsemble[i].setOptions(optionsIGUseGain);
                    break;
                case 1:
                _treeEnsemble[i].setOptions(optionsIG);
                    break;
                case 2:
                _treeEnsemble[i].setOptions(optionsGini);
                    break;
                case 3:
                _treeEnsemble[i].setOptions(optionsChiSquared);
                    break;
            
                default:
                    break;
            }

            _treeEnsemble[i].buildClassifier(instancesSubset);
        }
    }

    @Override
    public double classifyInstance(Instance instance) throws Exception {
        //Despite being a double, this is the index of the class.
        return majorityVote(instance);
    }

    public void printInstanceSubset(Instances instancesSubset) {
        for (int a = 0;a<instancesSubset.numAttributes();a++){
            System.out.print(instancesSubset.attribute(a) + ", ");
        }
        System.out.println(" ");
    }

    public static void main(String[] args) throws Exception {
        TreeEnsemble treeEnsemble = new TreeEnsemble();

        // String dataLocation="src/main/java/ml_6002b_coursework/test_data/WhiskeyRegion_TRAIN.arff";
        String dataLocation="src/main/java/ml_6002b_coursework/test_data/optdigits.arff";
        FileReader reader = new FileReader(dataLocation); 
        Instances trainingData = new Instances(reader); 
        trainingData.setClassIndex(trainingData.numAttributes() - 1);

        treeEnsemble.buildClassifier(trainingData);

        double classIndex = treeEnsemble.classifyInstance(trainingData.firstInstance());

        int a = 0;
    }

}
