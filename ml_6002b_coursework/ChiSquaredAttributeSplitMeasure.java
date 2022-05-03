package ml_6002b_coursework;

import java.io.FileReader;

import weka.core.Attribute;
import weka.core.Instances;


public class ChiSquaredAttributeSplitMeasure extends AttributeSplitMeasure {


    @Override
    public double computeAttributeQuality(Instances data, Attribute att) throws Exception {
        //get contingency table
        double[] attrArray = super.splitDataOnNumeric(data, att);
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
        
        value = AttributeMeasures.measureChiSquared(contingencyTable);

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
            trainingData.setClassIndex(trainingData.numAttributes() - 1);
            
            for (int i = 0;i<trainingData.numAttributes() - 1;i++){
                ChiSquaredAttributeSplitMeasure chiSquaredAttributeSplitMeasure = new ChiSquaredAttributeSplitMeasure();
                double result = chiSquaredAttributeSplitMeasure.computeAttributeQuality(trainingData, trainingData.attribute(i));
    
                System.out.println("measure Chi-Squared for attribute " + trainingData.attribute(i).name() + 
                " splitting diagnosis = " + result);
            }
        } catch (Exception e) { 
            System.out.println("Exception caught: "+e); 
        } 
    }

}
