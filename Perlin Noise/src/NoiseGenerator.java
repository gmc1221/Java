import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class NoiseGenerator {

	int size;
	int scale = 50;
	long seed = 0;
	
	double vList[];
	
	Random rand;
	
	public NoiseGenerator(int s){
		
		rand = new Random(seed);
		
		size = s;
		vList = new double[size+1];
		
		for(int i = 0 ; i < size+1; i++){
			
			double y = rand.nextDouble() * 50;
			
			if(rand.nextBoolean()){
				y = y * -1;
			}
			
			vList[i] = y;
			
		}
		
	}
	
	public double getVal(double x){
		
		double left = leftBound(x);	
		double right = rightBound(x);
		
		double interp = x-Math.floor(x);
		double step = 3*interp*interp - 2 * interp*interp*interp;
		//double step = (1-Math.cos(interp*Math.PI))/2;
		double y = left * (1-step) + right * step;

		return y;
	}
	
	public static void main(String[] args) {
		
//		NoiseGenerator perlin = new NoiseGenerator(10);
//		
//		double[] vGen = new double[500];
//		
//		for(double i = 0; i < 500; i++){
//			vGen[(int) i] = perlin.getVal(i/50);
//		}
//		
//		Screen screen = new Screen(500, 500);
//		
//		Graphics2D g = Screen.image.createGraphics();
//		
//		g.setColor(Color.BLACK);
//		g.fillRect(0, 0, 100, 100);
//		
//		for(int i = 0; i < vGen.length; i++){
//			
//			g.setColor(Color.WHITE);
//			g.fillRect(i, (int)vGen[i]+250, 1, 1);
//
//		}
//		
//		g.dispose();
//		Screen.pane.repaint();
		
		int depth = 4;
		
		Noise2D perlin = new Noise2D(depth+1);
		
		Screen screen = new Screen(500,500);
		
		Graphics2D g = Screen.image.createGraphics();
		
		for(int x = 0; x < 500 +1; x++){
			for(int y = 0; y < 500 +1; y++){
				
				double val = perlin.getVal((double)x/(500/depth), (double)y/(500/depth));
				
				//val = val/2 + 0.5;
				
				g.setColor(Color.getHSBColor((float)val, 1, 1));
				g.fillRect(x, y, 1, 1);
				
				//System.out.println(x + "," + y);
				System.out.println(val);
				
			}
		}
		
		g.dispose();
		screen.pane.repaint();
		
	}

	public double leftBound(double x){
		
		int index = (int) Math.floor(x);
		
		return vList[index];
		
	}
	
	public double rightBound(double x){
		
		int index = (int) Math.floor(x) + 1;
		
		return vList[index];
		
	}
	
	public double lerp(double a, double b){
		
		double c = (a + b) / 2;
		
		return c;
		
	}
	
}
