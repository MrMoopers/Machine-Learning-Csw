package ml_6002b_coursework;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.core.*;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

import java.io.FileReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Random;
import java.util.Vector;

/**
 * A basic decision tree classifier for use in machine learning coursework (6002B).
 */
public class CourseworkTree extends AbstractClassifier{

    /** Measure to use when selecting an attribute to split the data with. */
    private AttributeSplitMeasure attSplitMeasure = new IGAttributeSplitMeasure();

    /** Maxiumum depth for the tree. */
    private int maxDepth = Integer.MAX_VALUE;

    /** The root node of the tree. */
    private TreeNode root;
    
    private boolean isAverageProbabilities = false;

    public double getRootBestGain(){
        return root.getBestGain();
    }

  /**
   * Lists the command-line options for this classifier.
   * 
   * @return an enumeration over all possible options
   */
  @Override
  public Enumeration<Option> listOptions() {

    Vector<Option> newVector = new Vector<Option>();

    // newVector.addElement(new Option(
    //   "\tNumber of attributes to randomly investigate.\t(default 0)\n"
    //     + "\t(<0 = int(log_2(#predictors)+1)).", "K", 1,
    //   "-K <number of attributes>"));

    // newVector.addElement(new Option(
    //   "\tSet minimum number of instances per leaf.\n\t(default 1)", "M", 1,
    //   "-M <minimum number of instances>"));

    // newVector.addElement(new Option(
    //   "\tSet minimum numeric class variance proportion\n"
    //     + "\tof train variance for split (default 1e-3).", "V", 1,
    //   "-V <minimum variance for split>"));

    // newVector.addElement(new Option("\tSeed for random number generator.\n"
    //   + "\t(default 1)", "S", 1, "-S <num>"));

    // newVector.addElement(new Option(
    //   "\tThe maximum depth of the tree, 0 for unlimited.\n" + "\t(default 0)",
    //   "depth", 1, "-depth <num>"));

    // newVector.addElement(new Option("\tNumber of folds for backfitting "
    //   + "(default 0, no backfitting).", "N", 1, "-N <num>"));
    // newVector.addElement(new Option("\tAllow unclassified instances.", "U", 0,
    //   "-U"));
    // newVector.addAll(Collections.list(super.listOptions()));

    return newVector.elements();
  }

  /**
   * Gets options from this classifier.
   * 
   * @return the options for the current setup
   */
  @Override
  public String[] getOptions() {
    Vector<String> result = new Vector<String>();

    // result.add("-asm"); //AttributeSplitMeasure
    // result.add("" + this.attSplitMeasure);

    // if ("" + this.attSplitMeasure == "IGAttributeSplitMeasure")
    // {
    //     result.add("-U"); //Usegain
    //     result.add("" + this.attSplitMeasure.getUseGain());
    // }


    // result.add("-S"); //randomSplitValue
    // result.add("" + randomSplitValue);


    // // Collections.addAll(result, super.getOptions());
    // String[] optionsIG = result.toArray(new String[result.size()]);

    // Collections.addAll(result, super.getOptions());

    return result.toArray(new String[result.size()]);
  }

  /**
   * Parses a given list of options.
   * <p/>
   * 
   <!-- options-start -->
   * Valid options are: <p>
   * 
   * <pre> -K &lt;number of attributes&gt;
   *  Number of attributes to randomly investigate. (default 0)
   *  (&lt;0 = int(log_2(#predictors)+1)).</pre>
   * 
   * <pre> -M &lt;minimum number of instances&gt;
   *  Set minimum number of instances per leaf.
   *  (default 1)</pre>
   * 
   * <pre> -V &lt;minimum variance for split&gt;
   *  Set minimum numeric class variance proportion
   *  of train variance for split (default 1e-3).</pre>
   * 
   * <pre> -S &lt;num&gt;
   *  Seed for random number generator.
   *  (default 1)</pre>
   * 
   * <pre> -depth &lt;num&gt;
   *  The maximum depth of the tree, 0 for unlimited.
   *  (default 0)</pre>
   * 
   * <pre> -N &lt;num&gt;
   *  Number of folds for backfitting (default 0, no backfitting).</pre>
   * 
   * <pre> -U
   *  Allow unclassified instances.</pre>
   * 
   * <pre> -B
   *  Break ties randomly when several attributes look equally good.</pre>
   * 
   * <pre> -output-debug-info
   *  If set, classifier is run in debug mode and
   *  may output additional info to the console</pre>
   * 
   * <pre> -do-not-check-capabilities
   *  If set, classifier capabilities are not checked before classifier is built
   *  (use with caution).</pre>
   * 
   * <pre> -num-decimal-places
   *  The number of decimal places for the output of numbers in the model (default 2).</pre>
   * 
   <!-- options-end -->
   * 
   * @param options the list of options as an array of strings
   * @throws Exception if an option is not supported
   */
  @Override
  public void setOptions(String[] options) throws Exception {
    String tmpStr;


    tmpStr = Utils.getOption("asm", options);
    if (tmpStr.length() != 0) {
        switch (tmpStr) {
            case "IGAttributeSplitMeasure":
            // ((IGAttributeSplitMeasure) attSplitMeasure).setUseGain(Boolean.parseBoolean(tmpStr));
                IGAttributeSplitMeasure iGAttributeSplitMeasure = new IGAttributeSplitMeasure();

                tmpStr = Utils.getOption("U", options);
                if (tmpStr.length() != 0)
                {
                    iGAttributeSplitMeasure.setUseGain(Boolean.parseBoolean(tmpStr));
                }

                setAttSplitMeasure(iGAttributeSplitMeasure);

                break;

            case "GiniAttributeSplitMeasure":
                setAttSplitMeasure(new GiniAttributeSplitMeasure());
                break;

            case "ChiSquaredAttributeSplitMeasure":
                setAttSplitMeasure(new ChiSquaredAttributeSplitMeasure());
                break;
        
            default:
                break;
        }
    }

    tmpStr = Utils.getOption("S", options);
    if (tmpStr.length() != 0)
    {
        attSplitMeasure.setSplitValue(Double.parseDouble(tmpStr));
    }

    tmpStr = Utils.getOption("depth", options);
    if (tmpStr.length() != 0)
    {
        setMaxDepth(Integer.parseInt(tmpStr));
    }

    tmpStr = Utils.getOption("A", options);
    if (tmpStr.length() != 0)
    {
        setIsAverageProbabilities(Boolean.parseBoolean(tmpStr));
    }

    // tmpStr = Utils.getOption('K', options);
    // if (tmpStr.length() != 0) {
    //   m_KValue = Integer.parseInt(tmpStr);
    // } else {
    //   m_KValue = 0;
    // }

    // tmpStr = Utils.getOption('M', options);
    // if (tmpStr.length() != 0) {
    //   m_MinNum = Double.parseDouble(tmpStr);
    // } else {
    //   m_MinNum = 1;
    // }

    // tmpStr = Utils.getOption('S', options);
    // if (tmpStr.length() != 0) {
    //   setSeed(Integer.parseInt(tmpStr));
    // } else {
    //   setSeed(1);
    // }

    // tmpStr = Utils.getOption("depth", options);
    // if (tmpStr.length() != 0) {
    //   setMaxDepth(Integer.parseInt(tmpStr));
    // } else {
    //   setMaxDepth(0);
    // }

    // setAllowUnclassifiedInstances(Utils.getFlag('U', options));

    // super.setOptions(options);

    Utils.checkForRemainingOptions(options);
  }

    // public void setOptions(IGAttributeSplitMeasure iGAttributeSplitMeasure, boolean useGain, double splitValue) {
    //         //should this be a string array somehow?
    //         if (useGain){
    //             iGAttributeSplitMeasure.setUseGain(true);
    //         }
    //         else{
    //             iGAttributeSplitMeasure.setUseGain(false);
    //         }

    //         setOptions(iGAttributeSplitMeasure, splitValue);
    // }

    // public void setOptions(AttributeSplitMeasure attributeSplitMeasure, double splitValue) {
    //     //should this be a string array somehow?
    //     attributeSplitMeasure.setSplitValue(splitValue);
    //     setAttSplitMeasure(attributeSplitMeasure);      

        
    //     //set max depth?

    // }

    public CourseworkTree(){

    }

    // @Override
    // public void setOptions(String[] options) throws Exception {
    //     // setSplitCriterion(new SelectedTag(Integer.parseInt(opt), TAGS_SELECTION)); 
    // or
    //// classifier.setOptions(Utils.splitOptions("quotedOptionString"))
    // }

    // public CourseworkTree(AttributeSplitMeasure attributeSplitMeasure){
    //     setAttSplitMeasure(attributeSplitMeasure);
    // }

    // public CourseworkTree(IGAttributeSplitMeasure iGAttributeSplitMeasure, boolean useGain){
    //     if (useGain){
    //         iGAttributeSplitMeasure.setUseGain(true);
    //     }
    //     else{
    //         iGAttributeSplitMeasure.setUseGain(false);
    //     }

    //     setAttSplitMeasure(iGAttributeSplitMeasure);
    // }

    /**
     * Sets the attribute split measure for the classifier.
     *
     * @param attSplitMeasure the split measure
     */
    public void setAttSplitMeasure(AttributeSplitMeasure attSplitMeasure) {
        this.attSplitMeasure = attSplitMeasure;
    }

    /**
     * Sets the max depth for the classifier.
     *
     * @param maxDepth the max depth
     */
    public void setMaxDepth(int maxDepth){
        this.maxDepth = maxDepth;
    }

        /**
     * Sets the max depth for the classifier.
     *
     * @param maxDepth the max depth
     */
    public void setIsAverageProbabilities(boolean isAverageProbabilities){
        this.isAverageProbabilities = isAverageProbabilities;
    }
    /**
     * Returns default capabilities of the classifier.
     *
     * @return the capabilities of this classifier
     */
    @Override
    public Capabilities getCapabilities() {
        Capabilities result = super.getCapabilities();
        result.disableAll();

        // attributes
        result.enable(Capabilities.Capability.NOMINAL_ATTRIBUTES);

        // class
        result.enable(Capabilities.Capability.NOMINAL_CLASS);

        //instances
        result.setMinimumNumberInstances(2);

        return result;
    }

    /**
     * Builds a decision tree classifier.
     *
     * @param data the training data
     */
    @Override
    public void buildClassifier(Instances data) throws Exception {
        if (data.classIndex() != data.numAttributes() - 1) {
            throw new Exception("Class attribute must be the last index.");
        }

        root = new TreeNode();
        root.buildTree(data, 0);
    }

    /**
     * Classifies a given test instance using the decision tree.
     *
     * @param instance the instance to be classified
     * @return the classification
     */
    @Override
    public double classifyInstance(Instance instance) {
        double[] probs = distributionForInstance(instance);

        if (!isAverageProbabilities)
        {
            int maxClass = 0;
            for (int n = 1; n < probs.length; n++) {
                if (probs[n] > probs[maxClass]) {
                    maxClass = n;
                }
            }

            return maxClass;
        }
        else
        {
            double sumOfClassProbs = 0;
            for (int i = 0;i<probs.length;i++)
            {
                sumOfClassProbs += probs[i];
            }
            return sumOfClassProbs / probs.length;

        }

    }

    /**
     * Computes class distribution for instance using the decision tree.
     *
     * @param instance the instance for which distribution is to be computed
     * @return the class distribution for the given instance
     */
    @Override
    public double[] distributionForInstance(Instance instance) {
        return root.distributionForInstance(instance);
    }




    /**
     * Class representing a single node in the tree.
     */
    private class TreeNode {

        /** Attribute used for splitting, if null the node is a leaf. */
        Attribute bestSplit = null;

        /** Best gain from the splitting measure if the node is not a leaf. */
        double bestGain = 0;

        /** Depth of the node in the tree. */
        int depth;

        /** The node's children if it is not a leaf. */
        TreeNode[] children;

        /** The class distribution if the node is a leaf. */
        double[] leafDistribution;

        public double getBestGain()
        {
            return bestGain;
        }

        /**
         * Recursive function for building the tree.
         * Builds a single tree node, finding the best attribute to split on using a splitting measure.
         * Splits the best attribute into multiple child tree node's if they can be made, else creates a leaf node.
         *
         * @param data Instances to build the tree node with
         * @param depth the depth of the node in the tree
         */
        void buildTree(Instances data, int depth) throws Exception {
            this.depth = depth;

            // Loop through each attribute, finding the best one.
            for (int i = 0; i < data.numAttributes() - 1; i++) {
                double gain = attSplitMeasure.computeAttributeQuality(data, data.attribute(i));

                if (gain > bestGain) {
                    bestSplit = data.attribute(i);
                    bestGain = gain;
                }
            }

            // If we found an attribute to split on, create child nodes.
            if (bestSplit != null) {
                Instances[] split = attSplitMeasure.splitData(data, bestSplit);
                children = new TreeNode[split.length];

                // Create a child for each value in the selected attribute, and determine whether it is a leaf or not.
                for (int i = 0; i < children.length; i++){
                    children[i] = new TreeNode();

                    boolean leaf = split[i].numDistinctValues(data.classIndex()) == 1 || depth + 1 == maxDepth;

                    if (split[i].isEmpty()) {
                        children[i].buildLeaf(data, depth + 1);
                    } else if (leaf) {
                        children[i].buildLeaf(split[i], depth + 1);
                    } else {
                        children[i].buildTree(split[i], depth + 1);
                    }
                }
            // Else turn this node into a leaf node.
            } else {
                leafDistribution = classDistribution(data);
            }
        }

        /**
         * Builds a leaf node for the tree, setting the depth and recording the class distribution of the remaining
         * instances.
         *
         * @param data remaining Instances to build the leafs class distribution
         * @param depth the depth of the node in the tree
         */
        void buildLeaf(Instances data, int depth) {
            this.depth = depth;
            leafDistribution = classDistribution(data);
        }

        /**
         * Recursive function traversing node's of the tree until a leaf is found. Returns the leafs class distribution.
         *
         * @return the class distribution of the first leaf node
         */
        double[] distributionForInstance(Instance inst) {
            // If the node is a leaf return the distribution, else select the next node based on the best attributes
            // value.
            if (bestSplit == null) {
                return leafDistribution;
            } else {
                return children[(int) inst.value(bestSplit)].distributionForInstance(inst);
            }
        }

        /**
         * Returns the normalised version of the input array with values summing to 1.
         *
         * @return the class distribution as an array
         */
        double[] classDistribution(Instances data) {
            double[] distribution = new double[data.numClasses()];
            for (Instance inst : data) {
                distribution[(int) inst.classValue()]++;
            }

            double sum = 0;
            for (double d : distribution){
                sum += d;
            }

            if (sum != 0){
                for (int i = 0; i < distribution.length; i++) {
                    distribution[i] = distribution[i] / sum;
                }
            }

            return distribution;
        }

        /**
         * Summarises the tree node into a String.
         *
         * @return the summarised node as a String
         */
        @Override
        public String toString() {
            String str;
            if (bestSplit == null){
                str = "Leaf," + Arrays.toString(leafDistribution) + "," + depth;
            } else {
                str = bestSplit.name() + "," + bestGain + "," + depth;
            }
            return str;
        }
    }

    /**
     * Main method.
     *
     * @param args the options for the classifier main
     */
    public static void main(String[] args) {
        try{ 
            //Generate a 'random split' value between 0 and 1
            Random random = new Random(999);
            double randomSplitValue = random.nextDouble();

            String[] optionsIGUseGain = {"-asm", "IGAttributeSplitMeasure", "-U", "" + true, "-S", "" + randomSplitValue, "-depth", "" + Integer.MAX_VALUE, "a", "" + false};
            String[] optionsIG = {"-asm", "IGAttributeSplitMeasure", "-U", "" + false, "-S", "" + randomSplitValue, "-depth", "" + Integer.MAX_VALUE, "a", "" + false};
            String[] optionsGini = {"-asm", "GiniAttributeSplitMeasure", "-S", "" + randomSplitValue, "-depth", "" + Integer.MAX_VALUE, "a", "" + false};
            String[] optionsChiSquared = {"-asm", "ChiSquaredAttributeSplitMeasure", "-S", "" + randomSplitValue, "-depth", "" + Integer.MAX_VALUE, "a", "" + false};


            CourseworkTree courseworkTree;

            //#region optdigits
            String dataLocation="src/main/java/ml_6002b_coursework/test_data/optdigits.arff";
            Instances trainingData;
            FileReader reader = new FileReader(dataLocation); 
            trainingData = new Instances(reader); 
            trainingData.setClassIndex(trainingData.numAttributes() - 1);

            //Create a new tree and build a classifier for Infomation Gain
            courseworkTree = new CourseworkTree();
            courseworkTree.setOptions(optionsIGUseGain);
            courseworkTree.buildClassifier(trainingData);
            System.out.println("DT using measure InfomationGain on optdigits problem has test accuracy = " + courseworkTree.getRootBestGain());

            //Create a new tree and build a classifier for Infomation Gain Ratio
            courseworkTree = new CourseworkTree();
            courseworkTree.setOptions(optionsIG);
            courseworkTree.buildClassifier(trainingData);
            System.out.println("DT using measure InfomationGain on optdigits problem has test accuracy = " + courseworkTree.getRootBestGain());

            //Create a new tree and build a classifier for Gini
            courseworkTree = new CourseworkTree();
            courseworkTree.setOptions(optionsGini);
            courseworkTree.buildClassifier(trainingData);
            System.out.println("DT using measure Gini on optdigits problem has test accuracy = " + courseworkTree.getRootBestGain());

            //Create a new tree and build a classifier for Chi-Squared
            courseworkTree = new CourseworkTree();
            courseworkTree.setOptions(optionsChiSquared);
            courseworkTree.buildClassifier(trainingData);
            System.out.println("DT using measure Chi-Squared on optdigits problem has test accuracy = " + courseworkTree.getRootBestGain());
            //#endregion
            System.out.println();

            //Expected:
            //"DT using measure InfomationGain on optdigits problem has test accuracy = 0.5965943888477512"
            //"DT using measure InfomationGain on optdigits problem has test accuracy = 1.2585011413741618"
            //"DT using measure Gini on optdigits problem has test accuracy = 0.9527714554040989"
            //"DT using measure Chi-Squared on optdigits problem has test accuracy = 7688.223510194523"

            //#region Chinatown
            //TODO: Anthony Bagnall code errors with a java.lang.ArrayIndexOutOfBoundsException in AttributeSplitMeasure.java at splitData (ln 85)
            
            //Could you do this by setting the att.values for each attribute in the data to all the unique values in that column?
            // Or set that attribute's values to 0 and 1?
            dataLocation="src/main/java/ml_6002b_coursework/test_data/Chinatown.arff";
            reader = new FileReader(dataLocation); 
            trainingData = new Instances(reader); 
            trainingData.setClassIndex(trainingData.numAttributes() - 1);
            //Create a new tree and build a classifier for Infomation Gain
            courseworkTree = new CourseworkTree();
            courseworkTree.setOptions(optionsIGUseGain);
            courseworkTree.buildClassifier(trainingData);
            System.out.println("DT using measure InfomationGain on Chinatown problem has test accuracy = " + courseworkTree.getRootBestGain());

            //Create a new tree and build a classifier for Infomation Gain Ratio
            courseworkTree = new CourseworkTree();
            courseworkTree.setOptions(optionsIG);
            courseworkTree.buildClassifier(trainingData);
            System.out.println("DT using measure InfomationGain on Chinatown problem has test accuracy = " + courseworkTree.getRootBestGain());

            //Create a new tree and build a classifier for Gini
            courseworkTree = new CourseworkTree();
            courseworkTree.setOptions(optionsGini);
            courseworkTree.buildClassifier(trainingData);
            System.out.println("DT using measure Gini on Chinatown problem has test accuracy = " + courseworkTree.getRootBestGain());

            //Create a new tree and build a classifier for Chi-Squared
            courseworkTree = new CourseworkTree();
            courseworkTree.setOptions(optionsChiSquared);
            courseworkTree.buildClassifier(trainingData);
            System.out.println("DT using measure Chi-Squared on Chinatown problem has test accuracy = " + courseworkTree.getRootBestGain());
            //#endregion

            reader.close();
        }catch(Exception e){ 
            System.out.println("Exception caught: "+e); 
        } 
    }
}