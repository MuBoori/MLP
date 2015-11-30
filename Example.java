/**
 * Created by mujtaba on 24/04/15.
 *
 * a class to hold examples
 */
public class Example {


    public double[] getOutput() {
        return output;
    }

    public void setOutput(double[] output) {
        this.output = output;
    }

    public double[] getInput() {
        return input;
    }

    public void setInput(double[] input) {
        this.input = input;
    }

    double[] input;
    double[] output;

    public Example(double[] input, double[] output) {
        this.input = input;
        this.output = output;
    }
}
