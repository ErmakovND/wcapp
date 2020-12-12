package nd.ermakov.wcapp.regression;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

public class RegressionModel {

    private OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();

    public void fit(Double[][] x, Double[] y) {
        regression.setNoIntercept(true);
        regression.newSampleData(toPrimitive(y), toPrimitive(x));
    }

    public double predict(Double[] x) {
        double[] w = regression.estimateRegressionParameters();
        double y = 0;
        for (int i = 0; i < x.length; i++) {
            y += x[i] * w[i];
        }
        return y;
    }

    private double[] toPrimitive(Double[] arr) {
        double[] res = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    private double[][] toPrimitive(Double[][] arr) {
        double[][] res = new double[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = toPrimitive(arr[i]);
        }
        return res;
    }
}
