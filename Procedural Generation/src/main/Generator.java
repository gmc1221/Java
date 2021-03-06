package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Generator {

	float a;
	float q = 0.7f;
	float factor;
	int d;
	int l;
	
	Random rand;
	
	Point s;
	Point f;
	
	
	public Generator(float amplitude, int depth, Point start, Point end){
		
		this.a = amplitude;
		this.d = depth;
		
		this.s = start;
		this.f = end;
		
		rand = new Random();
	}
	
	public List<Point> generate1d() {
		
		List<Point> p = new ArrayList<Point>();
		List<Point> buffer = new ArrayList<Point>();
		
		p.add(s);
		p.add(f);
		
		for(int i = 0; i < d; i++){
			for(int n = 0; n < p.size() - 1; n++){
				buffer.add(p.get(n));
				Point mid = Point.midpoint(p.get(n), p.get(n+1));
				mid.y = disp(mid.y, a * (1/Math.pow(2, i)));
				buffer.add(mid);
			}
			buffer.add(f);
			p.clear();
			for(Point q : buffer){
				p.add(q);
			}
			buffer.clear();
		}

		return p;
		
	}
	
	public Point3[][] generate2d(Point3 p1, Point3 p2, Point3 p3, Point3 p4){
		
		l = (int) (Math.pow(2, d) + 1);
		
		System.out.println("l = " + l);
		
		List<Point3> p = new ArrayList<Point3>();
		List<Point3> buffer = new ArrayList<Point3>();
		
		Point3[][] points = new Point3[(int) (Math.pow(2, d) + 1)][(int) (Math.pow(2, d) + 1)];
		
		//Subtracting one since array indices start at 0
		points[0][0] = p1;
		points[l-1][0] = p2;
		points[0][l-1] = p3;
		points[l-1][l-1] = p4;
		
		int stepVal;
		factor = (float) Math.pow(2, -q);
		
		for(int i = 0; i < d; i++){
			
			//Diamond Step \/
			
			stepVal = (int) Math.pow(2, d-i);
			
			for(int x = 0; x < Math.pow(2, d); x+=stepVal){
				for(int y = 0; y < Math.pow(2, d); y+=stepVal){
					
					Point3 nw = points[x][y];
					Point3 ne = points[x+stepVal][y];
					Point3 sw = points[x][y+stepVal];
					Point3 se = points[x+stepVal][y+stepVal];
					
					Point3 newPoint = Point3.midpoint(nw, ne, sw, se);
					
					newPoint.z = disp(newPoint.z, a);
					
					points[x + stepVal/2][y + stepVal/2] = newPoint;
					
					
				}
			}
			
			
			//Square Step \/
			int y = 0;
			for(int x = stepVal/2; true; x+=stepVal){
				if(x > l-1){
					y += stepVal/2;
					x -= (l-1+stepVal/2);
					if(y > l-1){
						break;
					}
				}
				
				Point3 w;
				Point3 n;
				Point3 e;
				Point3 s;
				
					try{
						w = points[x-stepVal/2][y];
					} catch (ArrayIndexOutOfBoundsException er){
						w = null;
					}
					try{
						n = points[x][y-stepVal/2];
					} catch (ArrayIndexOutOfBoundsException er){
						n = null;
					}
					try{
						e = points[x+stepVal/2][y];
					} catch (ArrayIndexOutOfBoundsException er){
						e = null;
					}
					try{
						s = points[x][y+stepVal/2];
					} catch (ArrayIndexOutOfBoundsException er){
						s = null;
					}
					
					Point3 newPoint;
					
					if(w == null){
						newPoint = Point3.midpoint(n, s, e);
					} else if(n == null){
						newPoint = Point3.midpoint(s, e, w);
					} else if(e == null){
						newPoint = Point3.midpoint(n, s, w);
					}else if(s == null){
						newPoint = Point3.midpoint(n, e, w);
					} else{
						newPoint = Point3.midpoint(n, s, e, w);
					}
					
					newPoint.z = disp(newPoint.z, a);
					
					points[x][y] = newPoint;
					
			}
			
			a = a * factor;
			
		}
		
		return points;
		
	}
	
	
	private double disp(double v, double dis){
		
		double value = rand.nextDouble() * dis * 2;
		
		value -= dis;
		
		v += value;
		
		return v;
	}
	
}
