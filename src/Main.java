import Map.mapGenerator;


public class Main {
	public static void main(String[] args){
		mapGenerator h = new mapGenerator(600, 600);
		for(int i=0;i<h.getObjects().size();i++){
			System.out.println(h.getObjects().get(i).getClass() + " " + h.getObjects().get(i).getTopLeft() + " " + h.getObjects().get(i).getBottomRight());
		}
	
	}
}
