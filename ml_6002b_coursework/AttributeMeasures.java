package ml_6002b_coursework;

/**
 * Empty class for Part 2.1 of the coursework.
 */
public class AttributeMeasures {

    /**
     * Main method.
     *
     * @param args the options for the attribute measure main
     */
    public static void main(String[] args) {
        System.out.println("0t Implemented.");

        // Islay --> 0
        // Speyside --> 1
        int[][] contingencyTable = new int[][] {
            {1,      0,      1,         0},
            {1,      1,     1,          0},
            {1,      0,      0,         0},
            {1,      0,      0,         0},
            {0,       1,     0,         0},
            {0,       1,     1,         1},
            {0,       1,     1,         1},
            {0,       1,     1,         1},
            {0,       0,      1,        1},
            {0,       0,      1,        1},
        };

        double d = measureInformationGain(contingencyTable);
        d = measureInformationGainRatio(contingencyTable);
        d = measureGini(contingencyTable);
        d = measureChiSquared(contingencyTable);

    }

    private static double measureInformationGain(int[][] contingencyTable) {
        //TODO: You can assume that the rows represent different values of the attribute being assessed, and 
        //          the columns the class counts.

        // each time you run it you would send in the next contingency table created from the last, so just do it once only!

        //foreach column, not the last one
        //    count the number of 1's
        //    calc infogain for counter / length of big array (10)


        //page 28 of decision trees
        double maxValue = Double.MIN_VALUE;
        int count = 0;
        for (int i = 0; i < contingencyTable.length; i++) {
            count = 0;

            //Count the number of 
            for (int index = 0; index < contingencyTable[i].length; index++) {
                count+= contingencyTable[i][index];
            }

            int attributeChance = count / contingencyTable[0].length;

            if (attributeChance == 1 || attributeChance == 0)
            {
                return -(Math.log(1)); //=0
            }
    
            return -((attributeChance * Math.log(attributeChance)) + ((1-attributeChance) * Math.log((1-attributeChance))));
            
    
            return 0;


        }




        if (attributeChance == 1 || attributeChance == 0)
        {
            return -(Math.log(1));
        }

        return -((attributeChance * Math.log(attributeChance)) + ((1-attributeChance) * Math.log((1-attributeChance))));
        

        return 0;
    }

    private static double measureInformationGainRatio(int[][] contingencyTable) {
        return 0;
    }

    private static double measureGini(int[][] contingencyTable) {
        return 0;
    }

    private static double measureChiSquared(int[][] contingencyTable) {

        return 0;
    }







}
