package ml_6002b_coursework;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.core.FastVector;
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

    @Override
    public void buildClassifier(Instances instances) throws Exception {
        //or if (data.classIndex() != data.numAttributes() - 1?
        // if (_classifier == null)
        // {
        //     throw new Exception("Base classifier not yet implemented");
        // }

        double attributeProportionSelected = 0.5;

        // Classifier[] classifiers = AbstractClassifier.makeCopies(_classifier, _numTrees);

        

        int numberAttributes = (int)(instances.numAttributes() * attributeProportionSelected);

        RandomSubset randomSubset;

        int classifierChoice;
        Random rand = new Random(7); //TODO: REMOVE USING A SEED
        double randomSplitValue = rand.nextDouble();


        Vector<Integer> subset;
        Vector<Integer>	indices;
        for (int i = 0;i< _treeEnsemble.length;i++) {

            randomSubset = new RandomSubset();
            randomSubset.setNumAttributes(attributeProportionSelected);
            randomSubset.setSeed(rand.nextInt(Integer.MAX_VALUE));
            randomSubset.setInputFormat(instances);
            Instances instancesSubset = Filter.useFilter(instances, randomSubset);

            for (int a = 0;a<instancesSubset.numAttributes();a++){
                System.out.print(instancesSubset.attribute(a) + ", ");
            }
            System.out.println(" ");
            
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
            
            _treeEnsemble[i].buildClassifier(instancesSubset);
        }
        
        for (int i = 0; i<_numTrees;i++){
            // classifiers[i].classifyInstance(instance)
        }
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
