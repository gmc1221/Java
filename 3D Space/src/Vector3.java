

public class Vector3 {

	public double x;
	public double y;
	public double z;
	
	public Vector3(double x, double y, double z){
		
		this.x = x;
		this.y = y;
		this.z = z;
		
	}
	
	public static Vector3 add(Vector3 a, Vector3 b){
		
		double x = a.x + b.x;
		double y = a.y + b.y;
		double z = a.z + b.z;
		
		Vector3 c = new Vector3(x, y, z);
		
		return c;
	}
	
	public static Vector3 mult(Vector3 a, double n){
		
		double x = a.x * n;
		double y = a.y * n;
		double z = a.z * n;
		
		Vector3 c = new Vector3(x,y,z);
		return c;
	}
	
	public void print(){
		System.out.println(this.x + ", " + this.y + ", " + this.z);
	}
	
	public static Vector3 normalize(Vector3 a){

		Vector3 n;
		
		double l = Math.sqrt(a.x*a.x + a.y*a.y + a.z*a.z);
		
		n = new Vector3(a.x/l, a.y/l, a.z/l);
		
		return n;
	}
}