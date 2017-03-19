package main;

import java.util.ArrayList;
import java.util.List;

public class Point {

	double x;
	double y;
	
	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public static Point midpoint(Point a, Point b){
		
		Point out = new Point(0,0);
		
		out.x = ( a.x + b.x )/2;
		out.y = ( a.y + b.y )/2;
		
		return out;
	}

	public static List<Point> interpolate(List<Point> l){
		
		long size = Math.round(l.get(l.size()-1).x);
		
		List<Point> out = new ArrayList<Point>();
		
		for(int i = (int) Math.round(l.get(0).x); i < size; i++){
			
			int y = 0;
			
			Point pre = searchFor(l, i);
			Point post = searchFor(l, i+1);
			
			double m = (post.y - pre.y)/(post.x - pre.x);
			double b = pre.y - (pre.x * m);
			
			out.add(new Point(i, (int) Math.round(b + i*m)));
		}
		
		return out;
	}
	
	public static Point searchFor(List<Point> l, int v){
		
		Point closest = new Point(0,0);
		double dist = Double.MAX_VALUE;
		
		for(Point p : l){
			
			if(Math.abs(p.x-v) < dist && p.x <= v){
				closest = p;
				dist = Math.abs(p.x-v);
			}
			
		}
		
		return closest;
	}
	
	public void print(){
		
		System.out.println(this.x + "," + this.y);
		
	}
	
}
