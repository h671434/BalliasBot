package balliasbot.math;

public interface CostFunction {
	
	public static Vector error(Vector output, Vector target) {
		return output.minus(target);
	}
	
	public static double meanSquaredError(Vector errors) {
		double sum = 0;
		for(int i = 0; i < errors.size(); i++) {
			sum += Math.pow(errors.get(i), 2);
		}
		
		return sum / errors.size();
	}
	
	
	
}
