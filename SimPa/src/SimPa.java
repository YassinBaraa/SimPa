import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

public class SimPa {

	 public static void main(String[] args) {
	        //System.out.println("program je krenuo");

	        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        String line;
	        int brojac = 1;
	        
	        String[] nizovi1 = null;
	        String[] nizovi2 = null;
	        String[] nizovi3 = null;
	        String[] nizovi4 = null;
	        String[] nizovi5 = null;
	        
	        List<String> ulazni_nizovi=new ArrayList<String>();
	        List<String> stanja = new ArrayList<String>();
	        List<String> simboli = new ArrayList<String>();
	        List<String> prihvatljiva_stanja = new ArrayList<String>();
	        List<String> stog_stanja = new ArrayList<String>();
	        String poc_stanje = null;
	        String poc_stog = null;
	        
	        List<String> list=new ArrayList<String>();

	        HashMap<String, HashMap<String,HashMap<String, List<String>>>> prijelazi = null;
	        //     trStanje         ulZnak         znStog   novoStanje,stog

	        try {
	            while ( (line = br.readLine()) != null && (!line.isEmpty() || brojac < 7))
	            {
	               /* line = br.readLine();
	                if(line.isEmpty()  || line.isBlank()) {break;}*/


	                //ulazni nizovi
	                if(brojac == 1) {
	                    nizovi1 = line.split("\\|");
	                    brojac++;

	                    for(String niz:nizovi1) {
	                        ulazni_nizovi.add(niz);
	                    }
	                }
	                //Skup stanja odvojenih zarezom
	                else if(brojac ==2) {
	                    nizovi2= line.split(",");
	                    brojac++;
	                    
	                    for(String niz:nizovi2) {
	                        stanja.add(niz);
	                    }
	                }
	                //Skup ulaznih znakova odvojenih zarezom
	                else if(brojac ==3) {
	                    nizovi3 = line.split(",");
	                    brojac++;
	                    
	                    for(String niz:nizovi3) {
	                        simboli.add(niz);
	                    }
	                }
	                //Skup znakova stoga odvojenih zarezom
	                else if(brojac ==4) {
	                    nizovi4 = line.split(",");
	                    brojac++;
	                    
	                    for(String niz:nizovi4) {
	                        stog_stanja.add(niz);
	                    }
	                }
	              //Skup prihvatljivih stanja odvojenih zarezom
	                else if(brojac ==5) {
	                    nizovi5 = line.split(",");
	                    brojac++;
	                    
	                    for(String niz:nizovi5) {
	                        prihvatljiva_stanja.add(niz);
	                    }
	                }
	                //Pocetno stanje
	                else if(brojac ==6) {
	                    poc_stanje = line;
	                    brojac++;
	                }
	              //Pocetni znak stoga
	                else if(brojac ==7) {
	                    poc_stog = line;
	                    brojac++;
	                }
	                //funkcije prijelaza
	                else {
	                    String funk_prijelaz = line;

	                    list.add(funk_prijelaz);
	                    brojac++;
	                }

	            }

	            prijelazi = parsiranje(list);
	            br.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	   
	        potisni(ulazni_nizovi,prihvatljiva_stanja,stog_stanja,poc_stanje,poc_stog,prijelazi);
	    }
	 
	 
	    private static void potisni(List<String> ulazni_nizovi,
			List<String> prihvatljiva_stanja, List<String> stog_stanja, String poc_stanje, String poc_stog,
			HashMap<String, HashMap<String, HashMap<String, List<String>>>> prijelazi) {
		// TODO Auto-generated method stub
	    	
	    	for(String niz : ulazni_nizovi) {
	    		Stack<String> stog = new Stack<>();
	    		String tren_stanje = poc_stanje;
	    		String novo_stanje = null;
	    		String novo_stog = null;
	    		boolean fail = false;
	    		
	    		stog.push(poc_stog);
	    		String[] stanja = niz.split(",");
	    		
	    		System.out.print(tren_stanje+"#"+stog.peek()+"|");
	    		for(int i=0; i <stanja.length;i++) {
	    			
	    			novo_stanje = null;
	    			novo_stog = null;
	    			if(prijelazi.get(tren_stanje) != null
	    					&& prijelazi.get(tren_stanje).get(stanja[i]) != null
	    					&& prijelazi.get(tren_stanje).get(stanja[i]).get(stog.peek()) != null
	    					&& prijelazi.get(tren_stanje).get(stanja[i]).get(stog.peek()).get(0) != null
	    					) {
	    					novo_stanje = prijelazi.get(tren_stanje).get(stanja[i]).get(stog.peek()).get(0);
	    			}
	    			else {
	    				//epsilon prijelaz
	    				if(prijelazi.get(tren_stanje) != null
		    					&& prijelazi.get(tren_stanje).get("$") != null
		    					&& prijelazi.get(tren_stanje).get("$").get(stog.peek()) != null
		    					&& prijelazi.get(tren_stanje).get("$").get(stog.peek()).get(0) != null
		    					) {
	    					novo_stanje = prijelazi.get(tren_stanje).get("$").get(stog.peek()).get(0);
	    				}
	    			}
	    			
	    			if(prijelazi.get(tren_stanje) != null
	    					&& prijelazi.get(tren_stanje).get(stanja[i]) != null
	    					&& prijelazi.get(tren_stanje).get(stanja[i]).get(stog.peek()) != null
	    					&& prijelazi.get(tren_stanje).get(stanja[i]).get(stog.peek()).get(1) != null
	    					) {
	    					novo_stog = prijelazi.get(tren_stanje).get(stanja[i]).get(stog.peek()).get(1);
	    					//System.out.println("---------"+stog.peek() +"-----");
	    			}
	    			else {
	    				//epsilon prijelaz
	    				if(prijelazi.get(tren_stanje) != null
		    					&& prijelazi.get(tren_stanje).get("$") != null
		    					&& prijelazi.get(tren_stanje).get("$").get(stog.peek()) != null
		    					&& prijelazi.get(tren_stanje).get("$").get(stog.peek()).get(1) != null
		    					) {
	    					novo_stog = prijelazi.get(tren_stanje).get("$").get(stog.peek()).get(1);
	    					i--;//dekrementiranje for petlje da se ne bi potrosio znak
	    				}
	    			}
	    		
	    			//System.out.println(novo_stanje + novo_stog)
	    			if(novo_stanje == null || novo_stog == null) {
	    				System.out.println("fail|0");
	    				fail = true;
	    				break;
	    			}
	    			//ako se na stogu dodaje znak
	    			else if(novo_stog.length() > 1) {
	    				if(novo_stog.length() == 2) {
	    					stog.pop();
	    				String novi = Character.toString(novo_stog.charAt(0));
	    				String stari = Character.toString(novo_stog.charAt(1));
	    				stog.add(stari);
	    				stog.add(novi);
	    				}
	    				else if(novo_stog.length() == 3) {
	    					stog.pop();
	    					String novi1 = Character.toString(novo_stog.charAt(0));
	    					String novi2 = Character.toString(novo_stog.charAt(1));
	    					String stari = Character.toString(novo_stog.charAt(2));
		    				stog.add(stari);
	    					stog.add(novi2);
	    					stog.add(novi1);
	    				}
	    				else if(novo_stog.length() == 4) {
	    					stog.pop();
	    					String novi1 = Character.toString(novo_stog.charAt(0));
	    					String novi2 = Character.toString(novo_stog.charAt(1));
	    					String novi3 = Character.toString(novo_stog.charAt(2));
	    					String stari = Character.toString(novo_stog.charAt(3));
		    				stog.add(stari);
	    					stog.add(novi3);
	    					stog.add(novi2);
	    					stog.add(novi1);
	    				}
	    				
	    				tren_stanje = novo_stanje;
	    				System.out.print(tren_stanje+"#"/*+stog.peek()+"|"*/);
		    			//iterator za ispis svakog elementa na stogu
		    			
		    			ListIterator<String> iterator = stog.listIterator(stog.size());
		    			while (iterator.hasPrevious()) {
		    			    String element = iterator.previous();
		    			    if(!element.equals("$")) {
		    			    System.out.print(element);}
		    			    if(stog.size()==1 && stog.peek().equals("$")) {
		    			    	System.out.print("$");
		    			    }
	    			
		    			}
		    			System.out.print("|");
	    			}
	    			else {
	    				//ako je treci arg jedan znak stoga
	    				stog.pop();
		    			stog.add(novo_stog);

		    			tren_stanje = novo_stanje;
		    		
		    			System.out.print(tren_stanje+"#"/*+stog.peek()+"|"*/);
		    			//iterator za ispis svakog elementa na stogu
		    			ListIterator<String> iterator = stog.listIterator(stog.size());
		    			while (iterator.hasPrevious()) {
		    			    String element = iterator.previous();
		    			    if(!element.equals("$")) {
		    			    System.out.print(element);}
		    			    if(stog.size()==1 && stog.peek().equals("$")) {
		    			    	System.out.print("$");
		    			    }
		    			}
		    			System.out.print("|");
		    			
	    			}
	    			//$ prijelazi
	    			if(novo_stog.equals("$")){
	    				stog.pop();
	    				if(stog.empty()) {
	    					System.out.println("fail|0");
		    				fail = true;
		    				break;
	    				}
	    			}
	    			/////////////////////////////////////
	    			//ako postoji epsilon prijelaz na kraju(rubni slucaj)
	    			if(i+1 == stanja.length) {
	    				if(prihvatljiva_stanja.contains(tren_stanje)) {break;}
	    				while(novo_stanje != null && novo_stog != null) {
	    					novo_stanje = null;
	    					novo_stog = null;
	    				
	    				if(prijelazi.get(tren_stanje) != null
		    					&& prijelazi.get(tren_stanje).get("$") != null
		    					&& prijelazi.get(tren_stanje).get("$").get(stog.peek()) != null
		    					&& prijelazi.get(tren_stanje).get("$").get(stog.peek()).get(0) != null
		    					) {
	    					novo_stanje = prijelazi.get(tren_stanje).get("$").get(stog.peek()).get(0);
	    				}
	    				if(prijelazi.get(tren_stanje) != null
		    					&& prijelazi.get(tren_stanje).get("$") != null
		    					&& prijelazi.get(tren_stanje).get("$").get(stog.peek()) != null
		    					&& prijelazi.get(tren_stanje).get("$").get(stog.peek()).get(1) != null
		    					) {
	    					novo_stog = prijelazi.get(tren_stanje).get("$").get(stog.peek()).get(1);
	    					
	    				}
	    				
	    				if(novo_stanje == null || novo_stog == null) {
		    				break;
		    			}
		    			//ako se na stogu dodaje znak
		    			else if(novo_stog.length() > 1) {
		    				String novi = Character.toString(novo_stog.charAt(0));
		    				stog.add(novi);
		    				
		    				tren_stanje = novo_stanje;
		    				System.out.print(tren_stanje+"#"/*+stog.peek()+"|"*/);
			    			//iterator za ispis svakog elementa na stogu
			    			
			    			ListIterator<String> iterator = stog.listIterator(stog.size());
			    			while (iterator.hasPrevious()) {
			    			    String element = iterator.previous();
			    			    if(!element.equals("$")) {
				    			    System.out.print(element);}
			    			    if(stog.size()==1 && stog.peek().equals("$")) {
			    			    	System.out.print("$");
			    			    }
			    			    
			    			}
			    			System.out.print("|");
		    			}
		    			else {
		    				//ako je treci arg jedan znak stoga
		    				stog.pop();
			    			stog.add(novo_stog);

			    			tren_stanje = novo_stanje;
			    		
			    			System.out.print(tren_stanje+"#"/*+stog.peek()+"|"*/);
			    			//iterator za ispis svakog elementa na stogu
			    			ListIterator<String> iterator = stog.listIterator(stog.size());
			    			while (iterator.hasPrevious()) {
			    			    String element = iterator.previous();
			    			    if(!element.equals("$")) {
			    			    System.out.print(element);}
			    			    if(stog.size()==1 && stog.peek().equals("$")) {
			    			    	System.out.print("$");
			    			    }
			    			}
			    			System.out.print("|");
			    			
		    			}
	    				if(prihvatljiva_stanja.contains(novo_stanje)) {break;}
	    				}
	    			}
	    			///////////////////////////////////////////////////////
	    			
	    			
	    			
	    			
	    		//kraj petlje
	    		}
	    		if(fail == false) {
	    			if(prihvatljiva_stanja.contains(tren_stanje)) {
		    			System.out.println("1");
		    		}
		    		else {
		    			System.out.println("0");
		    		}
	    		}
	    		
	    	}
	}


		static HashMap<String, HashMap<String, HashMap<String, List<String>>>> parsiranje(List<String> list) {
	    	//trenutnoStanje,ulazniZnak,znakStoga->novoStanje,nizZnakovaStoga

	    	HashMap<String, HashMap<String, HashMap<String, List<String>>>> prijelazi = new HashMap<String, HashMap<String, HashMap<String, List<String>>>>();
	    	//HashMap<String,HashMap<String,List<String>>> mapa2 = new HashMap<String,HashMap<String,List<String>>>();
	    	//HashMap<String,List<String>> mapa1 = new HashMap<String, List<String>>();
	    	
	    	
	        for(String linija : list) {
	        	String[] zarezi = linija.split(",");
	            String trenutnoStanje = zarezi[0];
	            String ulazniZnak = zarezi[1];
	            String znakStoga = zarezi[2].substring(zarezi[2].indexOf(',')+1, zarezi[2].indexOf('-'));
	            String skupIducihStanja = zarezi[2].substring(zarezi[2].indexOf('>')+1) ;

	            List<String> stanja = new ArrayList<String>();
	            stanja.add(skupIducihStanja);
	            stanja.add(zarezi[3]);
	            
	            prijelazi.putIfAbsent(trenutnoStanje, new HashMap<>());
	            prijelazi.get(trenutnoStanje).putIfAbsent(ulazniZnak, new HashMap<>());
	            prijelazi.get(trenutnoStanje).get(ulazniZnak).put(znakStoga, stanja);
	            
	        }

	        return prijelazi;
	    }

}
