package main;

public class Point3 extends Point{

	double z;
	
	public Point3(double x, double y, double z){
		super(x,y);
		this.z = z;
		
	}
	
	public void print(){
		System.out.println(this.x + "," + this.y + "," + this.z);
	}
	
	public static Point3 midpoint(Point3 a, Point3 b, Point3 c, Point3 d){
		
		Point3 out = new Point3(0,0,0);
		
		out.x = ( a.x + b.x + c.x + d.x)/4;
		out.y = ( a.y + b.y + c.y + d.y)/4;
		out.z = ( a.z + b.z + c.z + d.z)/4;
		
		return out;
	}
	
	public static Point3 midpoint(Point3 a, Point3 b, Point3 c){
		
		//TODO Fix Problem
		
		Point3 out = new Point3(0,0,0);
		
		out.x = ( a.x + b.x + c.x)/3;
		out.y = ( a.y + b.y + c.y)/3;
		out.z = ( a.z + b.z + c.z)/3;
		
		return out;
	}
	
}
