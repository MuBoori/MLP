
public class Unit {

    private double activation;
    private double[] weights;

    static final float lambda = 1.5f;

	// main constructor
	public Unit(int prevUnitsNo, java.util.Random rand) {

		weights = new double[prevUnitsNo];


		for (int i = 0; i < prevUnitsNo; ++i)
			weights[i] = rand.nextFloat() - 0.5f;
	}


	public double activate(double inputs[]) {
		activation = 0.0f;
		assert(inputs.length == weights.length);

		for (int i = 0; i < inputs.length; ++i) // dot product
			activation += inputs[i] * weights[i];


		return 2.0f / (1.0f + (float) Math.exp((-activation) * lambda)) - 1.0f;
	}

	public double getActivationDerivative() {
		float expmlx = (float) Math.exp(lambda * activation);
		return 2 * lambda * expmlx / ((1 + expmlx) * (1 + expmlx));
	}

	public double[] getWeights() {
        return weights;
    }
	public double getWeight(int i) {
        return weights[i];
    }
	public void setWeight(int i, double v) {
        weights[i] = v;
    }


}
