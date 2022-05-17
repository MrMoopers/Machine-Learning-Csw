package ml_6002b_coursework;

import java.io.FileReader;
import java.util.Random;

import weka.classifiers.trees.Id3;
import weka.core.Instances;

/**
 * lists of datasets available on blackboard for part 3 of the coursework.
 *
 * These problems have no missing values.
 */
public class DatasetLists {

    public static String[] nominalAttributeProblems={
            "balance-scale",
        //     "car-evaluation",
            "chess-krvk",
            "chess-krvkp",
            "connect-4",
            "contraceptive-method",
            "fertility",
            "habermans-survival",
            "hayes-roth",
            "led-display",
            "lymphography",
            "molecular-promoters",
            "molecular-splice",
            "monks-1",
            "monks-2",
            "monks-3",
            "nursery",
            "optdigits",
            "pendigits",
            "semeion",
            "spect-heart",
            "tic-tac-toe",
            "zoo",
    };

    public static String[] continuousAttributeProblems={
            "bank",
            "blood",
            "breast-cancer-wisc-diag",
            "breast-tissue",
            "cardiotocography-10clases",
            "ecoli",
            "glass",
            "hill-valley",
            "image-segmentation",
            "ionosphere",
            "iris",
            "libras",
            "musk-2",
            "oocytes_merluccius_nucleus_4d",
            "oocytes_trisopterus_states_5b",
            "optical",
            "ozone",
            "page-blocks",
            "parkinsons",
            "pendigits",
            "planning",
            "post-operative",
            "ringnorm",
            "seeds",
            "spambase",
            "statlog-image",
            "statlog-landsat",
            "statlog-shuttle",
            "steel-plates",
            "synthetic-control",
            "twonorm",
            "vertebral-column-3clases",
            "wall-following",
            "waveform-noise",
            "wine-quality-white",
            "yeast",
    };

    public static void main(String[] args) throws Exception {
        Random random = new Random();
        double randomSplitValue = random.nextDouble();

        String[] optionsIGUseGain = {"-asm", "IGAttributeSplitMeasure", "-U", "" + true, "-S", "" + randomSplitValue, "-depth", "" + Integer.MAX_VALUE, "-A", "" + false};
        String[] optionsIG = {"-asm", "IGAttributeSplitMeasure", "-U", "" + false, "-S", "" + randomSplitValue, "-depth", "" + Integer.MAX_VALUE, "-A", "" + false};
        String[] optionsGini = {"-asm", "GiniAttributeSplitMeasure", "-S", "" + randomSplitValue, "-depth", "" + Integer.MAX_VALUE, "-A", "" + false};
        String[] optionsChiSquared = {"-asm", "ChiSquaredAttributeSplitMeasure", "-S", "" + randomSplitValue, "-depth", "" + Integer.MAX_VALUE, "-A", "" + false};


        CourseworkTree courseworkTree;
        Id3 id3;
        String dataLocation;
        Instances trainingData;
        Instances testData;
        FileReader reader;

        // //#region CourseworkTree
        // System.out.println("nominalAttributeProblems))");
        // for (int i = 0;i<nominalAttributeProblems.length;i++)
        // {
        //         dataLocation="src/main/java/ml_6002b_coursework/evaluation_test_data/UCI Discrete/"+ nominalAttributeProblems[i] + "/" +nominalAttributeProblems[i] + ".arff";
        //         reader = new FileReader(dataLocation); 
        //         trainingData = new Instances(reader); 
        //         trainingData.setClassIndex(trainingData.numAttributes() - 1);

        //         //Create a new tree and build a classifier for Infomation Gain
        //         courseworkTree = new CourseworkTree();
        //         courseworkTree.setOptions(optionsIGUseGain);
        //         courseworkTree.buildClassifier(trainingData);
        //         System.out.println("IGUseGain|("+nominalAttributeProblems[i]+")|accuracy = " + courseworkTree.getRootBestGain());

        //         //Create a new tree and build a classifier for Infomation Gain
        //         courseworkTree = new CourseworkTree();
        //         courseworkTree.setOptions(optionsIG);
        //         courseworkTree.buildClassifier(trainingData);
        //         System.out.println("IG|("+nominalAttributeProblems[i]+")|accuracy = " + courseworkTree.getRootBestGain());

        //         //Create a new tree and build a classifier for Infomation Gain
        //         courseworkTree = new CourseworkTree();
        //         courseworkTree.setOptions(optionsGini);
        //         courseworkTree.buildClassifier(trainingData);
        //         System.out.println("Gini|("+nominalAttributeProblems[i]+")|accuracy = " + courseworkTree.getRootBestGain());

        //         //Create a new tree and build a classifier for Infomation Gain
        //         courseworkTree = new CourseworkTree();
        //         courseworkTree.setOptions(optionsChiSquared);
        //         courseworkTree.buildClassifier(trainingData);
        //         System.out.println("Chi^2|("+nominalAttributeProblems[i]+")|accuracy = " + courseworkTree.getRootBestGain());
        //         System.out.println("");
        // }

        // System.out.println("");
        // System.out.println("continuousAttributeProblems))");
        // for (int i = 0;i<continuousAttributeProblems.length;i++)
        // {
        //         dataLocation="src/main/java/ml_6002b_coursework/evaluation_test_data/UCI Continuous/"+ continuousAttributeProblems[i] + "/" +continuousAttributeProblems[i] + ".arff";
        //         reader = new FileReader(dataLocation); 
        //         trainingData = new Instances(reader); 
        //         trainingData.setClassIndex(trainingData.numAttributes() - 1);

        //         //Create a new tree and build a classifier for Infomation Gain
        //         courseworkTree = new CourseworkTree();
        //         courseworkTree.setOptions(optionsIGUseGain);
        //         courseworkTree.buildClassifier(trainingData);
        //         System.out.println("IGUseGain|("+continuousAttributeProblems[i]+")|accuracy = " + courseworkTree.getRootBestGain());

        //         //Create a new tree and build a classifier for Infomation Gain
        //         courseworkTree = new CourseworkTree();
        //         courseworkTree.setOptions(optionsIG);
        //         courseworkTree.buildClassifier(trainingData);
        //         System.out.println("IG|("+continuousAttributeProblems[i]+")|accuracy = " + courseworkTree.getRootBestGain());

        //         //Create a new tree and build a classifier for Infomation Gain
        //         courseworkTree = new CourseworkTree();
        //         courseworkTree.setOptions(optionsGini);
        //         courseworkTree.buildClassifier(trainingData);
        //         System.out.println("Gini|("+continuousAttributeProblems[i]+")|accuracy = " + courseworkTree.getRootBestGain());

        //         //Create a new tree and build a classifier for Infomation Gain
        //         courseworkTree = new CourseworkTree();
        //         courseworkTree.setOptions(optionsChiSquared);
        //         courseworkTree.buildClassifier(trainingData);
        //         System.out.println("Chi^2|("+continuousAttributeProblems[i]+")|accuracy = " + courseworkTree.getRootBestGain());
        //         System.out.println("");
        // }
        // //#endregion

        // System.out.println("AttributeCount="+testData.numAttributes());
        // System.out.println("InstanceCount="+testData.numInstances());
        // System.out.println("ClassCount="+testData.classAttribute().numValues());

       //#region CourseworkTree
       System.out.println("nominalAttributeProblems))");
       for (int i = 0;i<nominalAttributeProblems.length;i++)
       {
               dataLocation="src/main/java/ml_6002b_coursework/evaluation_test_data/UCI Discrete/"+ nominalAttributeProblems[i] + "/" +nominalAttributeProblems[i] + ".arff";
               reader = new FileReader(dataLocation); 
               trainingData = new Instances(reader); 
               trainingData.setClassIndex(trainingData.numAttributes() - 1);
               testData = new Instances(trainingData); 
               testData.setClassIndex(testData.numAttributes() - 1);

        //        System.out.println(nominalAttributeProblems[i]);
        //        System.out.println(testData.numAttributes());
        //        System.out.println(testData.numInstances());
        //        System.out.println(testData.classAttribute().numValues());
        //        System.out.println();

        //        id3 = new Id3();

        //        //Create a new tree and build a classifier for Infomation Gain
        //        String[] optionsID3 = {"-D", "" + false};
               
        //        id3.setOptions(optionsID3);
        //        id3.buildClassifier(trainingData);
        //        id3.classifyInstance(testData.firstInstance());
        //        System.out.println("id3|("+nominalAttributeProblems[i]+")|accuracy = " + id3.toString());

        //        System.out.println("");
       }
       System.out.println("");

       System.out.println("continuousAttributeProblems))");
       for (int i = 0;i<continuousAttributeProblems.length;i++)
       {
               dataLocation="src/main/java/ml_6002b_coursework/evaluation_test_data/UCI Continuous/"+ continuousAttributeProblems[i] + "/" +continuousAttributeProblems[i] + ".arff";
               reader = new FileReader(dataLocation); 
               trainingData = new Instances(reader); 
               trainingData.setClassIndex(trainingData.numAttributes() - 1);
               testData = new Instances(trainingData); 
               testData.setClassIndex(testData.numAttributes() - 1);


               System.out.println(continuousAttributeProblems[i]);
               System.out.println(testData.numAttributes());
               System.out.println(testData.numInstances());
               System.out.println(testData.classAttribute().numValues());
               System.out.println();

        //        id3 = new Id3();
               
        //        //Create a new tree and build a classifier for Infomation Gain
        //        String[] optionsID3 = {"-D", "" + false};
               
        //        id3.setOptions(optionsID3);
        //        id3.buildClassifier(trainingData);
        //        id3.classifyInstance(testData.firstInstance());
        //        System.out.println("id3|("+continuousAttributeProblems[i]+")|accuracy = " + id3.toString());

        //        System.out.println("");
       }

//        System.out.println("");
//        System.out.println("continuousAttributeProblems))");
//        for (int i = 0;i<continuousAttributeProblems.length;i++)
//        {
//                dataLocation="src/main/java/ml_6002b_coursework/evaluation_test_data/UCI Continuous/"+ continuousAttributeProblems[i] + "/" +continuousAttributeProblems[i] + ".arff";
//                reader = new FileReader(dataLocation); 
//                trainingData = new Instances(reader); 
//                trainingData.setClassIndex(trainingData.numAttributes() - 1);

//                //Create a new tree and build a classifier for Infomation Gain
//                courseworkTree = new CourseworkTree();
//                courseworkTree.setOptions(optionsIGUseGain);
//                courseworkTree.buildClassifier(trainingData);
//                System.out.println("IGUseGain|("+continuousAttributeProblems[i]+")|accuracy = " + courseworkTree.getRootBestGain());

//                //Create a new tree and build a classifier for Infomation Gain
//                courseworkTree = new CourseworkTree();
//                courseworkTree.setOptions(optionsIG);
//                courseworkTree.buildClassifier(trainingData);
//                System.out.println("IG|("+continuousAttributeProblems[i]+")|accuracy = " + courseworkTree.getRootBestGain());

//                //Create a new tree and build a classifier for Infomation Gain
//                courseworkTree = new CourseworkTree();
//                courseworkTree.setOptions(optionsGini);
//                courseworkTree.buildClassifier(trainingData);
//                System.out.println("Gini|("+continuousAttributeProblems[i]+")|accuracy = " + courseworkTree.getRootBestGain());

//                //Create a new tree and build a classifier for Infomation Gain
//                courseworkTree = new CourseworkTree();
//                courseworkTree.setOptions(optionsChiSquared);
//                courseworkTree.buildClassifier(trainingData);
//                System.out.println("Chi^2|("+continuousAttributeProblems[i]+")|accuracy = " + courseworkTree.getRootBestGain());
//                System.out.println("");
//        }
    }


}
