package nd.ermakov.wcapp.regression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegressionModelTest {

    @Test
    void predict() {
        RegressionModel model = new RegressionModel();
        Double[][] x = new Double[10][2];
        Double[] y = new Double[10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 2; j++) {
                x[i][j] = i + (double)j;
            }
            y[i] = (double)i;
        }
        model.fit(x, y);
        double p = model.predict(new Double[]{6D, 7D});
        double eps = 1e-10;
        assertTrue(p > 6 - eps);
        assertTrue(p < 6 + eps);
    }
}