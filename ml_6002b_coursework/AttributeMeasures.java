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

        int[][] exampleHumidContingencyTable = new int[][] {
            {1, 0},
            {1, 0},
            {1, 1},
            {1, 1},
            {1, 1},
            {0, 0},
            {0, 1},
            {1, 0},
            {0, 1},
            {1, 1},
            {0, 1},
            {1, 1},
            {0, 1},
            {1, 0},
        };

        double d = 0.0;

        // d = measureInformationGain(exampleTempContingencyTable);   //Works
        // d = measureInformationGainRatio(exampleTempContingencyTable); //Works
        // d = measureGini(exampleTempContingencyTable);

        // d = measureInformationGain(exampleHumidContingencyTable);  
        // d = measureInformationGain(exampleTempContingencyTable);   
        d = measureChiSquared(exampleHumidContingencyTable);
        // d = measureChiSquared(exampleTempContingencyTable);

        // d = measureInformationGain(peatyContingencyTable);
        // d = measureInformationGainRatio(peatyContingencyTable);

        // d = measureGini(peatyContingencyTable);

        // d = measureChiSquared(peatyContingencyTable);

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
            //altered
            if (contingencyTable[index][0] == 1 && contingencyTable[index][1] == 1){
                peaty1_TrueCount++;
            }
            else if (contingencyTable[index][0] == 1 && contingencyTable[index][1] == 0)
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

            // if (contingencyTable[index][0] == 1 && contingencyTable[index][1] == 0){
            //     peaty1_TrueCount++;
            // }
            // else if (contingencyTable[index][0] == 1 && contingencyTable[index][1] == 1)
            // {
            //     peaty1_FalseCount++;
            // }
            // else if (contingencyTable[index][0] == 0 && contingencyTable[index][1] == 1)
            // {
            //     peaty2_TrueCount++;
            // }
            // else if (contingencyTable[index][0] == 0 && contingencyTable[index][1] == 0)
            // {
            //     peaty2_FalseCount++;
            // }


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

        double gain = measureInformationGain(contingencyTable);

        double peaty_TrueCount  = 0;
        double peaty_FalseCount = 0;

        int tableSize = contingencyTable.length;
        for (int index = 0; index < tableSize; index++) {
            if (contingencyTable[index][1] == 1){
                peaty_TrueCount++;
            }
            else
            {
                peaty_FalseCount++;
            }
        }

        double splitInfo = -(
            ( (peaty_TrueCount / tableSize) * Math.log(peaty_TrueCount / tableSize) / Math.log(2)) +
            ( (peaty_FalseCount / tableSize) * Math.log(peaty_FalseCount / tableSize) / Math.log(2))
        );

        double gainRatio = gain / splitInfo;
        return gainRatio;
    }



    private static double measureChiSquared(int[][] contingencyTable) {
        double peaty1Positive_observed  = 0;
        double peaty1Negative_observed = 0;
        double peaty2Positive_observed  = 0;
        double peaty2Negative_observed = 0;

        int tableSize = contingencyTable.length;
        for (int index = 0; index < tableSize; index++) {
            //altered
            if (contingencyTable[index][0] == 1 && contingencyTable[index][1] == 1){
                peaty1Positive_observed++;
            }
            else if (contingencyTable[index][0] == 1 && contingencyTable[index][1] == 0)
            {
                peaty1Negative_observed++;
            }
            else if (contingencyTable[index][0] == 0 && contingencyTable[index][1] == 1)
            {
                peaty2Positive_observed++;
            }
            else if (contingencyTable[index][0] == 0 && contingencyTable[index][1] == 0)
            {
                peaty2Negative_observed++;
            }
                 
        }

        double positiveCount = peaty1Positive_observed + peaty2Positive_observed; //top left
        double negativeCount = peaty1Negative_observed + peaty2Negative_observed;
        double peaty1_count = peaty1Positive_observed + peaty1Negative_observed;
        double peaty2_count = peaty2Positive_observed + peaty2Negative_observed;

        double peaty1Positive_expected = peaty1_count * (positiveCount / tableSize); //top left
        double peaty1Negative_expected = peaty1_count * (negativeCount / tableSize);
        double peaty2Positive_expected = peaty2_count * (positiveCount / tableSize);
        double peaty2Negative_expected = peaty2_count * (negativeCount / tableSize);

        double chiSquared = 
            (Math.pow((peaty1Positive_observed - peaty1Positive_expected), 2) / peaty1Positive_expected) +
            (Math.pow((peaty1Negative_observed - peaty1Negative_expected), 2) / peaty1Negative_expected) +
            (Math.pow((peaty2Positive_observed - peaty2Positive_expected), 2) / peaty2Positive_expected) +
            (Math.pow((peaty2Negative_observed - peaty2Negative_expected), 2) / peaty2Negative_expected);

        return chiSquared;
    }


    private static double measureGini(int[][] contingencyTable) {
        return 0;
    }




}
