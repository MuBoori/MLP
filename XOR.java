import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by mujtaba on 26/04/15.
 */
public class XOR {

    public static void main(String[] args) {


        Example[] examples = new Example[]{
                new Example(new double[]{0,0},new double[]{0}),
                new Example(new double[]{0,1},new double[]{1}),
                new Example(new double[]{1,0},new double[]{1}),
                new Example(new double[]{1,1},new double[]{0})

        };


        MLP mlp = new MLP(2,2,1);

        try {
            PrintWriter fout = new PrintWriter(new FileWriter("XOR.text"));
            fout.println("#\tX\tY");

        for (int i = 0; i < 100000; ++i) {


            double error = 0.0;
            for (int l = 0; l < examples.length; ++l) {
                double[] output = mlp.forward(examples[l].input);
                error +=  mlp.backwards( output,examples[l].output);


            }

            mlp.updateWeights(0.3);

            if(i % 100 == 1) {
                System.out.println(i + " -> error : " + error);
                fout.println("\t" + i + "\t" + error);

            }


        }

            fout.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        // test the result

        System.out.println("MLP output : Actual output");
        for(int i = 0 ; i < examples.length; i++){
            double[] out= mlp.forward(examples[i].input);
            System.out.println(out[1] + " : "+ examples[i].output[0]);
        }
    }

}
