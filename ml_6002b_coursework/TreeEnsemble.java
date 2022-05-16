package ml_6002b_coursework;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.core.Attribute;
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

    public double[] majorityVote(Instance instance)  {
        double[] classCounters = new double[instance.numClasses()];
        for (int i = 0; i < _treeEnsemble.length; i++) {
            //Is always 0? the if statement is always false.
            int predictedClassIndex = (int)_treeEnsemble[i].classifyInstance(instance);
            classCounters[predictedClassIndex]+=1;
        }

        return classCounters;
    }

    @Override
    public void buildClassifier(Instances instances) throws Exception {
        Random rand = new Random(); //TODO: REMOVE USING A SEED

        double attributeProportionSelected = 0.5;

        int classifierChoice;
        RandomSubset randomSubset;
        for (int i = 0;i< _treeEnsemble.length;i++) {
            randomSubset = new RandomSubset();
            randomSubset.setNumAttributes(attributeProportionSelected);
            // randomSubset.setSeed(2);//TODO: Remove this
            // randomSubset.setSeed(rand.nextInt(Integer.MAX_VALUE));
            randomSubset.setInputFormat(instances);
            Instances instancesSubset = Filter.useFilter(instances, randomSubset);

            // printInstanceSubset(instancesSubset);//Evidence of Testing
            
            // classifierChoice = rand.nextInt(4);
            _treeEnsemble[i] = new CourseworkTree();

            // switch (classifierChoice) {
            //     case 0:
            //     _treeEnsemble[i].setOptions(optionsIGUseGain);
            //         break;
            //     case 1:
            //     _treeEnsemble[i].setOptions(optionsIG);
            //         break;
            //     case 2:
            //     _treeEnsemble[i].setOptions(optionsGini);
            //         break;
            //     case 3:
            //     _treeEnsemble[i].setOptions(optionsChiSquared);
            //         break;
            
            //     default:
            //         break;
            // }

            _treeEnsemble[i].buildClassifier(instancesSubset);
        }
    }

    @Override
    public double classifyInstance(Instance instance) throws Exception {
        //Despite being a double, this is the index of the class.
        double[] classCounters = majorityVote(instance);
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
    public double[] distributionForInstance(Instance instance) throws Exception {
        double[] classCounters = majorityVote(instance);
        double[] classProportions = new double[classCounters.length] ;

        for (int i = 0;i<classCounters.length;i++)
        {
            classProportions[i] = classCounters[i] / (double)_numTrees;
        }

        return classProportions;
    }

    public double[] averageDistributions(Instance instance) throws Exception {
        double[] classCounters = new double[instance.numClasses()];
        for (int i = 0; i < _treeEnsemble.length; i++) {
            //Is always 0? the if statement is always false.
            int predictedClassIndex = (int)Math.round(_treeEnsemble[i].classifyInstance(instance));
            classCounters[predictedClassIndex]+=1;
        }

        double[] classProportions = new double[classCounters.length] ;

        for (int i = 0;i<classCounters.length;i++)
        {
            classProportions[i] = classCounters[i] / (double)_numTrees;
        }

        return classProportions;
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
        //Attribute selection Process & stopping conditions
        Random rand = new Random();
        int maxDepth = Integer.MAX_VALUE;
        maxDepth = rand.nextInt(Integer.MAX_VALUE);
        double randomSplitValue = rand.nextDouble();

        // boolean isAverageDistributions = true;

        Vector<String[]> options = new Vector<String[]>();
        options.add(new String[] {"-asm", "IGAttributeSplitMeasure", "-U", "" + true, "-S", "" + randomSplitValue, "-depth", "" + maxDepth, "-A", "" + true});
        options.add(new String[] {"-asm", "IGAttributeSplitMeasure", "-U", "" + true, "-S", "" + randomSplitValue, "-depth", "" + maxDepth, "-A", "" + false});

        options.add(new String[] {"-asm", "IGAttributeSplitMeasure", "-U", "" + false, "-S", "" + randomSplitValue, "-depth", "" + maxDepth, "-A", "" + true});
        options.add(new String[] {"-asm", "IGAttributeSplitMeasure", "-U", "" + false, "-S", "" + randomSplitValue, "-depth", "" + maxDepth, "-A", "" + false});

        options.add(new String[] {"-asm", "GiniAttributeSplitMeasure", "-S", "" + randomSplitValue, "-depth", "" + maxDepth, "-A", "" + true});
        options.add(new String[] {"-asm", "GiniAttributeSplitMeasure", "-S", "" + randomSplitValue, "-depth", "" + maxDepth, "-A", "" + false});

        options.add(new String[] {"-asm", "ChiSquaredAttributeSplitMeasure", "-S", "" + randomSplitValue, "-depth", "" + maxDepth, "-A", "" + true});
        options.add(new String[] {"-asm", "ChiSquaredAttributeSplitMeasure", "-S", "" + randomSplitValue, "-depth", "" + maxDepth, "-A", "" + false});

        String dataLocation;
        FileReader reader;
        Instances trainingData;

        double majorityVoteAttributeIndex;
        Attribute majorityVoteAttribute;  
        double[] voteProportions;         
        double[] averageProportions;    
        
        for (int i = 0;i< options.size();i++)
        {
            dataLocation ="src/main/java/ml_6002b_coursework/test_data/optdigits.arff";
            reader = new FileReader(dataLocation); 
            trainingData = new Instances(reader); 
            trainingData.setClassIndex(trainingData.numAttributes() - 1);
    
            treeEnsemble.setOptions(options.get(i));
            treeEnsemble.buildClassifier(trainingData);

            for (int j=0;j<5;j++)
            {
                majorityVoteAttributeIndex = treeEnsemble.classifyInstance(trainingData.get(j));
                majorityVoteAttribute = trainingData.attribute((int)majorityVoteAttributeIndex);

                System.out.println("" + j + "). ");
                
                voteProportions = treeEnsemble.distributionForInstance(trainingData.get(j));
                System.out.println("DT using measure " + options.get(i)[1] + " on optdigits problem has test accuracy = " + Arrays.toString(voteProportions));
    
                averageProportions = treeEnsemble.averageDistributions(trainingData.get(j));
                System.out.println("DT using measure " + options.get(i)[1] + " on optdigits problem has probability estimates = " + Arrays.toString(averageProportions));
            }
        }
        
        treeEnsemble = new TreeEnsemble();
        for (int i = 0;i< options.size();i++)
        {
            dataLocation="src/main/java/ml_6002b_coursework/test_data/Chinatown.arff";
            reader = new FileReader(dataLocation); 
            trainingData = new Instances(reader); 
            trainingData.setClassIndex(trainingData.numAttributes() - 1);
    
            treeEnsemble.setOptions(options.get(i));
            treeEnsemble.buildClassifier(trainingData);

            for (int j=0;j<5;j++)
            {
                majorityVoteAttributeIndex = treeEnsemble.classifyInstance(trainingData.get(j));
                majorityVoteAttribute = trainingData.attribute((int)majorityVoteAttributeIndex);
                
                voteProportions = treeEnsemble.distributionForInstance(trainingData.get(j));
                System.out.println("DT using measure " + options.get(i)[1] + " on Chinatown problem has test accuracy = " + Arrays.toString(voteProportions));
    
                averageProportions = treeEnsemble.averageDistributions(trainingData.get(j));
                System.out.println("DT using measure " + options.get(i)[1] + " on Chinatown problem has probability estimates = " + Arrays.toString(averageProportions));
            }
        }
        

        
        
        int a = 0;
    }


    //MajorityVote.java
}
