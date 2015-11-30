import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mujtaba on 26/04/15.
 */
public class Exceptional {

    private static Example[] trainingExample;

    public static void main(String[] args) {


        // map each letter to represent a number
        HashMap<String,Integer> letter = new HashMap<String, Integer>();
        int t = 0;
        for (char ch = 'A'; ch <= 'Z'; ++ch) {
            letter.put(String.valueOf(ch), t);
            t++;
        }

        List<Example> examples = new ArrayList<Example>();

        // read files
        String csvFile = "letter-recognition.data";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            // read each line and parse features and lable
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] row = line.split(cvsSplitBy);

                double[] feature = new double[16];

                // the output is zero ara with 1 in the position of the letter
                double[] labels = new double[26];
                labels[letter.get(row[0])] = 1;

                for (int i = 1; i <= feature.length; i++) {
                    feature[i -1] = Double.parseDouble(row[i]);
                }




                // add the example in the set
                examples.add(new Example(feature, labels));

            }



            // split data set into training and test example
            Example[] trainingExample = new Example[15000];
            Example[] testExample = new Example[5000];
            System.arraycopy(examples.toArray(new Example[examples.size()]), 0, trainingExample, 0, trainingExample.length);
            System.arraycopy(examples.toArray(new Example[examples.size()]), trainingExample.length, testExample, 0, testExample.length);




            // initialize mlp with 16 input and 10 hidden layer and 26 output
            MLP mlp = new MLP(16, 10, 26);


            try {
                PrintWriter fout = new PrintWriter(new FileWriter("exc.text"));
                fout.println("#\tX\tY");

            for (int i = 0; i < 100; ++i) {

                double error = 0.0;
                for (int l = 0; l < trainingExample.length; ++l) {
                    double[] output = mlp.forward(trainingExample[l].input);
                    error += mlp.backwards(output, trainingExample[l].output);

                }

                mlp.updateWeights(0.000001);

                System.out.println(i + " -> error : " + error);

                fout.println("\t" + i + "\t" + error);

            }

                fout.close();
            } catch (IOException e){
                e.printStackTrace();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
