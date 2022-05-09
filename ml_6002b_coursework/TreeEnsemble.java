package ml_6002b_coursework;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
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

    // @Override
    // public double classifyInstance(Instance instance) throws Exception {
    //     double[] dist = distributionForInstance(instance);
    //     return utilities.GenericTools.indexOfMax(dist);
    // }

    public void majorityVote(Instance instance)  {
        double x;
        for (int i = 0; i < _treeEnsemble.length; i++) {
            //Is always 0? the if statement is always false.
            x = _treeEnsemble[i].classifyInstance(instance);
            System.out.println((i + 1) + ") class: = " + x);
        }
    }

    @Override
    public void buildClassifier(Instances instances) throws Exception {
        double attributeProportionSelected = 0.5;
        int maxDepth = Integer.MAX_VALUE;

        RandomSubset randomSubset;

        int classifierChoice;
        Random rand = new Random(7); //TODO: REMOVE USING A SEED
        double randomSplitValue = rand.nextDouble();

        for (int i = 0;i< _treeEnsemble.length;i++) {
            randomSubset = new RandomSubset();
            randomSubset.setNumAttributes(attributeProportionSelected);
            randomSubset.setSeed(rand.nextInt(Integer.MAX_VALUE));
            randomSubset.setInputFormat(instances);
            Instances instancesSubset = Filter.useFilter(instances, randomSubset);

            printInstanceSubset(instancesSubset);
            
            classifierChoice = rand.nextInt(4);
            _treeEnsemble[i] = new CourseworkTree();

            switch (classifierChoice) {
                case 0:
                _treeEnsemble[i].setOptions(new IGAttributeSplitMeasure(), true, randomSplitValue);
                    break;
                case 1:
                _treeEnsemble[i].setOptions(new IGAttributeSplitMeasure(), false, randomSplitValue);
                    break;
                case 2:
                _treeEnsemble[i].setOptions(new GiniAttributeSplitMeasure(), randomSplitValue);
                    break;
                case 3:
                _treeEnsemble[i].setOptions(new ChiSquaredAttributeSplitMeasure(), randomSplitValue);
                    break;
            
                default:
                    break;
            }

            //Attribute selection Process & stopping conditions
            maxDepth = rand.nextInt(Integer.MAX_VALUE);
            _treeEnsemble[i].setMaxDepth(maxDepth);
            
            _treeEnsemble[i].buildClassifier(instancesSubset);
        }

        //TODO: Which Instance???
        majorityVote(instances.firstInstance());
        
        int a = 0;
    }

    public void printInstanceSubset(Instances instancesSubset) {
        for (int a = 0;a<instancesSubset.numAttributes();a++){
            System.out.print(instancesSubset.attribute(a) + ", ");
        }
        System.out.println(" ");
    }

    public static void main(String[] args) throws Exception {
        TreeEnsemble treeEnsemble = new TreeEnsemble();

        String dataLocation="src/main/java/ml_6002b_coursework/test_data/WhiskeyRegion_TRAIN.arff";
        FileReader reader = new FileReader(dataLocation); 
        Instances trainingData = new Instances(reader); 
        trainingData.setClassIndex(trainingData.numAttributes() - 1);

        treeEnsemble.buildClassifier(trainingData);
    }

}
