package ml_6002b_coursework;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

import java.util.Enumeration;

/**
 * Interface for alternative attribute split measures for Part 2.2 of the coursework
 */
public abstract class AttributeSplitMeasure {

    public abstract double computeAttributeQuality(Instances data, Attribute att) throws Exception;

    //True mean use Infomation Gain. False means use Infomation Gain Ratio.
    private double _splitValue = 0.5;

    public void setSplitValue(double splitValue){
        _splitValue = splitValue;
    }

    public double[] splitDataOnNumeric(Instances data, Attribute att) throws Exception{
        double[] values = data.attributeToDoubleArray(att.index());

        //Determine the minimum and maximum values within this attributes data.
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (int i = 0; i< values.length; i++)
        {
            if (values[i] < min){
                min = values[i];
            }
            if (values[i] > max){
                max = values[i];
            } 
        }

        //Check that a divide by zero error will not occur. 
        if ((max - min) == Double.MIN_VALUE)
        {
            for (int i = 0; i < data.numInstances(); i++) {
                //There is only one value in the entire attribute's dataset.
                values[i] = 0.0;
            }
        }
        else
        {
            
            double normalisedValue = -1;
            for (int i = 0; i < data.numInstances(); i++) {
                //Normalise the value between 0.0 and 1.0
                normalisedValue = values[i] / (max - min);

                //Fit the data values into one of two sets.
                if (normalisedValue >= _splitValue)
                {
                    values[i] = 1.0;
                }
                else{
                    values[i] = 0.0;
                }
            }
        }

        return values;
    }

    /**
     * Splits a dataset according to the values of a nominal attribute.
     *
     * @param data the data which is to be split
     * @param att the attribute to be used for splitting
     * @return the sets of instances produced by the split
     */
    public Instances[] splitData(Instances data, Attribute att) {
        Instances[] splitData = new Instances[att.numValues()];
        for (int i = 0; i < att.numValues(); i++) {
            splitData[i] = new Instances(data, data.numInstances());
        }

        for (Instance inst: data) {
            splitData[(int) inst.value(att)].add(inst);
        }

        for (Instances split : splitData) {
            split.compactify();
        }

        return splitData;
    }

}
