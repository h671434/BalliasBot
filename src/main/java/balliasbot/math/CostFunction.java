package balliasbot.math;

public interface CostFunction {

	public static double meanSquaredError(Vector output, Vector target) {
		double[] outputArray = output.asArray();
		double[] targetArray = target.asArray();
		
		double sum = 0;
		for(int i = 0; i < outputArray.length; i++) {
			sum += Math.pow(outputArray[i] - targetArray[i], 2);
		}
		
		return sum / outputArray.length;
	}
	
}
