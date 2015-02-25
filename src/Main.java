import Map.mapGenerator;


public class Main {
	public static void main(String[] args){
		mapGenerator h = new mapGenerator(200, 200);
		for(int i=0;i<h.getMap().size();i++){
			System.out.println(h.getMap().get(i).getClass() + " " + h.getMap().get(i).getTopLeft() + " " + h.getMap().get(i).getBottomRight());
		}
	
	}
}
