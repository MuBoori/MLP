import java.util.ArrayList;
import java.util.Random;
import java.io.*;


/**
 * Main MLP
 * */
public class MLP {

    int ni; // number of input
    int nh; // number of hidden unit
    int no; // number of output

    double[] o; // output

    private ArrayList<Layer> layers; // array to hold all the arrays
    private ArrayList<double[][]> dW;
    private ArrayList<double[]> grad;


    /***
     * Construct the MLP
     * */
	public MLP(int inputN, int hiddenUnit, int outputN)
	{
        this.ni = inputN;
        this.nh =hiddenUnit;
        this.no = outputN;


        randomise();

	}


    /**
     * initalize the variables
     * */
    private void randomise(){
        Random rand = new Random();

        layers = new ArrayList<Layer>();

        layers.add(new Layer(ni,ni,rand));
        layers.add(new Layer(ni,nh,rand));
        layers.add(new Layer(nh,no,rand));

        dW = new ArrayList<double[][]>();
        for (int i = 0; i < 3; ++i)
            dW.add(new double
                            [layers.get(i).size()]
                            [layers.get(i).getWeights(0).length]
            );

        grad = new ArrayList<double[]>();
        for (int i =  0; i < 3; ++i)
            grad.add(new double[layers.get(i).size()]);
    }

	public double[] forward(double[] inputs) {

		double outputs[] = new double[inputs.length];

		for( int i = 0; i < layers.size(); ++i ) {
			outputs = layers.get(i).calOutput(inputs);
			inputs = outputs;
		}


		return outputs;
	}

    /**
     *
     * */
	private double evaluateError(double output[], double target[]) {
		double d[];
		
		// add bias to input if necessary
		if (target.length != output.length)
			d = Layer.addBias(target);
		else
			d = target;

		assert(output.length == d.length);

		double e = 0;
		for (int i = 0; i < output.length; ++i)
			e += (output[i] - d[i]) * (output[i] - d[i]);

		return e;
	}



	public double backwards(double[] output ,double[] targets) {
		// for each neuron in each layer
		for (int c = layers.size()-1; c >= 0; --c) {
			for (int i = 0; i < layers.get(c).size(); ++i) {
				// if it's output layer neuron
				if (c == layers.size()-1) {
					grad.get(c)[i] =
						2 * (layers.get(c).getOutput(i) - targets[0])
						  * layers.get(c).getActivationDerivative(i);
				}
				else { // if it's neuron of the previous layers
					double sum = 0;
					for (int k = 1; k < layers.get(c+1).size(); ++k)
						sum += layers.get(c+1).getWeight(k, i) * grad.get(c+1)[k];
					grad.get(c)[i] = layers.get(c).getActivationDerivative(i) * sum;
				}
			}
		}

        calculateWeightsDelta();
        
        return evaluateError(output,targets);
	}

	private void resetWeightsDelta()
	{
		// reset delta values for each weight
		for (int c = 0; c < layers.size(); ++c) {
			for (int i = 0; i < layers.get(c).size(); ++i) {
				double weights[] = layers.get(c).getWeights(i);
				for (int j = 0; j < weights.length; ++j)
					dW.get(c)[i][j] = 0;
	        }		
		}
	}

	private void calculateWeightsDelta()
	{
		// forward delta values for each weight
		for (int c = 1; c < layers.size(); ++c) {
			for (int i = 0; i < layers.get(c).size(); ++i) {
				double weights[] = layers.get(c).getWeights(i);
				for (int j = 0; j < weights.length; ++j)
					dW.get(c)[i][j] += grad.get(c)[i]
					     * layers.get(c-1).getOutput(j);
			}
		}
	}

	public void updateWeights(double learning_rate)
	{
		for (int c = 0; c < layers.size(); ++c) {
			for (int i = 0; i < layers.get(c).size(); ++i) {
				double weights[] = layers.get(c).getWeights(i);
				for (int j = 0; j < weights.length; ++j)
					layers.get(c).setWeight(i, j, layers.get(c).getWeight(i, j)
							- (learning_rate * dW.get(c)[i][j]));
	        }
		}

        resetWeightsDelta();
	}



}
