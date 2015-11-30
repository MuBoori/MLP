import java.util.ArrayList;


/**
 *
 * A class that host all the units in this layer
 * also preform calculation and initialize variable
 * */
public class Layer {

    private int unitNo, prevUnitNo;
    private ArrayList<Unit> units;
    private double _outputs[];

	public Layer(int prevUnitNo, int UnitNo, java.util.Random rand) {
		unitNo = UnitNo + 1;
		this.prevUnitNo = prevUnitNo + 1;


		units = new ArrayList<Unit>();
		_outputs = new double[unitNo];

		for (int i = 0; i < unitNo; ++i)
			units.add(new Unit(this.prevUnitNo, rand));
	}

	// add 1 in front of the out vector
	public static double[] addBias(double[] in) {
		double out[] = new double[in.length + 1];
		for (int i = 0; i < in.length; ++i)
			out[i + 1] = in[i];
		out[0] = 1.0f;
		return out;
	}


	public double[] calOutput(double in[]) {
		double inputs[];

		// add an input (bias) if necessary
		if (in.length != getWeights(0).length)
			inputs = addBias(in);
		else
			inputs = in;


		for (int i = 1; i < unitNo; ++i)
			_outputs[i] = units.get(i).activate(inputs);

		// add biass
		_outputs[0] = 1.0f;

		return _outputs;
	}

	public int size() {
        return unitNo;
    }
	public double getOutput(int i) {
        return _outputs[i];
    }
	public double getActivationDerivative(int i) {
        return units.get(i).getActivationDerivative();

    }
	public double[] getWeights(int i) {
        return units.get(i).getWeights();
    }
	public double getWeight(int i, int j) {
        return units.get(i).getWeight(j);
    }
	public void setWeight(int i, int j, double v) {
        units.get(i).setWeight(j, v);
    }


}
