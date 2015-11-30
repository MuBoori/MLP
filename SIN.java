import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Created by mujtaba on 26/04/15.
 */
public class SIN {

    public static void main(String[] args) {


        Random random = new Random();

        Example[] examples= new Example[50];

        for(int i = 0; i < examples.length; i++) {
            double x1 = -1 + 2 * random.nextDouble() / 1,
                    x2 = -1 + 2 * random.nextDouble() / 1,
                    x3 = -1 + 2 * random.nextDouble() / 1,
                    x4 = -1 + 2 * random.nextDouble() / 1;

            examples[i] = new Example(new double[]{x1, x2, x3, x4},
                    new double[]{Math.sin(x1 + x2 + x3 + x4)});

        }

        Example[] trainingExample = new Example[40];
        Example[] testExample = new Example[10];

        System.arraycopy(examples, 0, trainingExample, 0, trainingExample.length);
        System.arraycopy(examples, trainingExample.length, testExample, 0, testExample.length);

        MLP mlp = new MLP(4,5,1);


        try {
            PrintWriter fout = new PrintWriter(new FileWriter("Sin.text"));
            fout.println("#\tX\tY");

        for (int i = 0; i < 1000000; ++i) {

            double error = 0.0;
            for (int l = 0; l < trainingExample.length; ++l) {
                double[] output = mlp.forward(trainingExample[l].input);
                error +=  mlp.backwards( output,trainingExample[l].output);

            }

            mlp.updateWeights(0.01);


            if(i % 10000 == 1) {
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
        for(int i = 0 ; i < testExample.length; i++){
            double[] out= mlp.forward(testExample[i].input);
            System.out.println(out[1] + " : "+ testExample[i].output[0]);
        }


        }
}
