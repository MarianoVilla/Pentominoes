package GeneralPurposeHelpers;

/**
 * A general matrices helper. Hasn't being used so far, 'cause it was made thinking in the Pentominos2DSolverMT refactoring that are yet to be made.
 */
public class MatrixHelper {
	
    public static void rotateMatrix(int[][] matrix) {
        if (matrix.length == 0) {
            return;
        }
        for (int i = 0; i < matrix.length / 2; i++) {
            int top = i;
            int bottom = matrix.length - 1 - i;
            for (int j = top; j < bottom; j++) {
                int temp = matrix[top][j];
                matrix[top][j] = matrix[j][bottom];
                matrix[j][bottom] = matrix[bottom][bottom - (j - top)];
                matrix[bottom][bottom - (j - top)] = matrix[bottom - (j - top)][top];
                matrix[bottom - (j - top)][top] = temp;
            }
        }
    }
    public static int[][] AddMatrices(int[][] a, int[][] b){
    	int c[][]=new int[a.length][a[0].length];
        for(int i = 0;i<3;i++){
            for(int j = 0;j<3;j++){
               c[i][j] = a[i][j]+b[i][j];
            }
        }
        return c;
    }
}
