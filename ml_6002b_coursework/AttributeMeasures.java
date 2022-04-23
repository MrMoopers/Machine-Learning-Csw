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

        int[][] peatyContingencyTable = new int[][] {
            {1, 0},
            {1, 0},
            {1, 0},
            {1, 0},
            {0, 0},
            {0, 1},
            {0, 1},
            {0, 1},
            {0, 1},
            {0, 1},
        };

        int[][] exampleTempContingencyTable = new int[][] {
            {1, 0},
            {1, 0},
            {1, 1},
            {0, 1},
            {0, 1},
            {0, 0},
            {0, 1},
            {0, 0},
            {0, 1},
            {0, 1},
            {0, 1},
            {0, 1},
            {1, 1},
            {0, 0},
        };

        double d = 0.0;

        

        d = measureInformationGain(peatyContingencyTable);

// d = measureInformationGain(exampleTempContingencyTable);
        d = measureInformationGainRatio(peatyContingencyTable);
        d = measureGini(peatyContingencyTable);
        d = measureChiSquared(peatyContingencyTable);

    }

    private static double measureInformationGain(int[][] contingencyTable) {
        //TODO: You can assume that the rows represent different values of the attribute being assessed, and 
        //          the columns the class counts.

        // each time you run it you would send in the next contingency table created from the last, so just do it once only!

        //foreach column, not the last one
        //    count the number of 1's
        //    calc infogain for counter / length of big array (10)


        //page 28 of decision trees
        //Count the number of 
        double peaty1_TrueCount  = 0;
        double peaty1_FalseCount = 0;
        double peaty2_TrueCount  = 0;
        double peaty2_FalseCount = 0;

        int tableSize = contingencyTable.length;
        for (int index = 0; index < tableSize; index++) {
            if (contingencyTable[index][0] == 1 && contingencyTable[index][1] == 0){
                peaty1_TrueCount++;
            }
            else if (contingencyTable[index][0] == 1 && contingencyTable[index][1] == 1)
            {
                peaty1_FalseCount++;
            }
            else if (contingencyTable[index][0] == 0 && contingencyTable[index][1] == 1)
            {
                peaty2_TrueCount++;
            }
            else if (contingencyTable[index][0] == 0 && contingencyTable[index][1] == 0)
            {
                peaty2_FalseCount++;
            }


        }

        //If it would otherwise try and log(0), then instead use this base value of 0.0.
        double hX = 0.0;
        double h1 = 0.0;
        double h2 = 0.0;

        //H(X) = - 
        double totalTrue = peaty1_TrueCount + peaty2_TrueCount;
        double totalFalse = peaty1_FalseCount + peaty2_FalseCount;
        hX = -(
            ( (totalTrue / tableSize) * Math.log(totalTrue / tableSize) / Math.log(2)) +
            ( (totalFalse/ tableSize) * Math.log(totalFalse/ tableSize) / Math.log(2))
               );

        //H(1)
        double peaty1_TrueChance  = peaty1_TrueCount  / (peaty1_TrueCount + peaty1_FalseCount);
        double peaty1_FalseChance = peaty1_FalseCount / (peaty1_TrueCount + peaty1_FalseCount);
        
        if ( !(peaty1_TrueChance == 0.0 || peaty1_FalseChance == 0.0 ))
        {
            h1 = -(
                ( (peaty1_TrueChance) * Math.log(peaty1_TrueChance) / Math.log(2)) +
                ( (peaty1_FalseChance) * Math.log(peaty1_FalseChance) / Math.log(2))
                );
        }

        //H(2)
        double peaty2_TrueChance  = peaty2_TrueCount  / (peaty2_TrueCount + peaty2_FalseCount);
        double peaty2_FalseChance = peaty2_FalseCount / (peaty2_TrueCount + peaty2_FalseCount);
        
        if ( !(peaty2_TrueChance == 0.0 || peaty2_FalseChance == 0.0 ))
        {
            h2 = -(
                ( (peaty2_TrueChance) * Math.log(peaty2_TrueChance) / Math.log(2)) +
                ( (peaty2_FalseChance) * Math.log(peaty2_FalseChance) / Math.log(2))
                );
        }

        //Gain
        double peaty1_total = peaty1_TrueCount + peaty1_FalseCount;
        double peaty2_total = peaty2_TrueCount + peaty2_FalseCount;
        double gain = hX - ((peaty1_total / tableSize) * h1) - ((peaty2_total / tableSize) * h2);
        return gain;
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
