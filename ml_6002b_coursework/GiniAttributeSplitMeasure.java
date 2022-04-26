package ml_6002b_coursework;

import java.io.FileReader;

import weka.core.Attribute;
import weka.core.Instances;


public class GiniAttributeSplitMeasure extends AttributeSplitMeasure {


    @Override
    public double computeAttributeQuality(Instances data, Attribute att) throws Exception {
        //get contingency table
        double[] attrArray = data.attributeToDoubleArray(att.index());
        double[] classArray = data.attributeToDoubleArray(data.classIndex());
        int[][] contingencyTable = new int[data.size()][2];

        for (int i = 0;i<data.size();i++) {
            int[] newArray = new int[] {
                (int)attrArray[i],
                (int)classArray[i]
            };
            
             contingencyTable[i] = newArray;
        }

        double value = 0.0;
        
        value = AttributeMeasures.measureGini(contingencyTable);

        return value;
    }

    /**
     * Main method.
     *
     * @param args the options for the split measure main
     */
    public static void main(String[] args) {
        try{ 
            // String dataLocation="src/main/java/ml_6002b_coursework/test_data/Chinatown.arff"; 
            String dataLocation="src/main/java/ml_6002b_coursework/test_data/WhiskeyRegion_TRAIN.arff"; 
            Instances trainingData;

            FileReader reader = new FileReader(dataLocation); 
            trainingData = new Instances(reader); 

            Attribute attribute = trainingData.attribute(0);
            trainingData.setClassIndex(3);
            
            GiniAttributeSplitMeasure giniAttributeSplitMeasure = new GiniAttributeSplitMeasure();
            double d  = giniAttributeSplitMeasure.computeAttributeQuality(trainingData, attribute);

            int a = 0;

            // CourseworkTree courseworkTree = new CourseworkTree();
            // courseworkTree.buildClassifier(trainingData);

            // double acc = .0;
            // for (Instance testInst : trainingData) {
            //     double pred = courseworkTree.classifyInstance(testInst);             //aka predict
            //     //double [] dist = randf.distributionForInstance(testInst); //aka predict_proba
                
            //     if (pred == testInst.classValue())
            //         acc++;
        } catch (Exception e) { 
            System.out.println("Exception caught: "+e); 
        } 

        System.out.println("Not Implemented.");
    }

}
