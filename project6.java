import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.Scanner;


public class project6 {
	public static void main(String[] args) throws InterruptedException {
		ArrayList<String> seatsinfo = new ArrayList<String>();
		seatsinfo.add("A1");
		seatsinfo.add("A2");
		seatsinfo.add("A3");
		seatsinfo.add("B1");
		seatsinfo.add("B2");
		seatsinfo.add("B3");
		seatsinfo.add("C1");
		seatsinfo.add("C2");
		seatsinfo.add("C3");
		seatsinfo.add("D1"); 

                ArrayList<String> seatsinfo2 = new ArrayList<String>();
		seatsinfo2.add("A1");
		seatsinfo2.add("A2");
		seatsinfo2.add("A3");
		seatsinfo2.add("B1");
		seatsinfo2.add("B2");
		seatsinfo2.add("B3");
		seatsinfo2.add("C1");
		seatsinfo2.add("C2");
		seatsinfo2.add("C3");
		seatsinfo2.add("D1");

                 ArrayList<String> seatsinfo3 = new ArrayList<String>();
		seatsinfo3.add("A1");
		seatsinfo3.add("A2");
		seatsinfo3.add("A3");
		seatsinfo3.add("B1");
		seatsinfo3.add("B2");
		seatsinfo3.add("B3");
		seatsinfo3.add("C1");
		seatsinfo3.add("C2");
		seatsinfo3.add("C3");
		seatsinfo3.add("D1");



                System.out.println("Welcome to the ticket booking counter..");

                Scanner in = new Scanner(System.in);
                System.out.println("Enter 1 to see the movie lists\n");
                int input=in.nextInt();
                if(input==1)
                System.out.println("1.abc\n2.def\n3.ghi\n");
                
    
		movie movie1 = new movie("abc", 10, seatsinfo);
                movie movie2 = new movie("def", 10, seatsinfo2);
                movie movie3 = new movie("ghi", 10, seatsinfo3);

		
		ExecutorService service = Executors.newFixedThreadPool(3);
                 //in.nextLine();
                List<customer> customerlist = new ArrayList<>();
              
               // for(int i=1; i<=4; i++){
                while(true){
                String name;
                int tickets;
                int movienum;
            
                System.out.println("Enter your name");
                name=in.nextLine();
                in.nextLine();
                System.out.println("Enter number of seats");
                 tickets=in.nextInt();
                 in.nextLine();
                  System.out.println("Enter movie number");
                 movienum=in.nextInt();
                
                if(movienum==1){
                customerlist.add(new customer(name,tickets,movie1));}
                 
                else if(movienum==2){
                customerlist.add(new customer(name,tickets,movie2));}

                else{
                customerlist.add(new customer(name,tickets,movie3));
                }

                System.out.println("Do you want to continue?\n");
                  int input2=in.nextInt();
                  if(input2==0)
                  break;
	     }	
                service.invokeAll(customerlist);

		service.shutdown();
		while (!service.isTerminated());
			

	}
}



 class customer implements Callable<String> {
	String name;
	int seatsRequired;
	movie watch;

	public customer(String name, int seats, movie watch) {
		this.name = name;
		seatsRequired = seats;
		this.watch = watch;
	}

         private static Semaphore mutex = new Semaphore(1);
         Scanner object = new Scanner(System.in);
       
	public synchronized String call() {
               
                try{  
                    mutex.acquire();  
		if (watch.seats < seatsRequired) {
                    
			System.out.println("Sorry " + name + " only " + watch.seats + " seats available");
                          mutex.release();
			return "not";// kill thread here
		}
                 
                   
		ArrayList<String> seats_assingned = assign();
		watch.seats = watch.seats - seatsRequired;
		System.out.println("Hey " +name+ "!" + seatsRequired + " seats booked successfully! for " +watch.name);
                int price = seatsRequired*150;
                System.out.println("Booking price is Rupees" +price);
		for (String string : seats_assingned) {
			System.out.print(string + " ");
		}
		System.out.println();
                System.out.println("Do you want to rate our site?\n");
	        int input = object.nextInt();
                if(input==0)
                System.out.println("Thank you for visiting\n");
                else
                 rateOurSite();

                mutex.release();
                return "yes";
               }
          
              
            
         catch (InterruptedException e){
             System.out.println("Error\n");
               return "not";
              } 
      
	
       }
	public synchronized ArrayList<String> assign() {
		ArrayList<String> finalised_seats = new ArrayList<String>();
		
                      int i=0,count=0;
                       while(count<seatsRequired){
			finalised_seats.add(watch.seatsinfo.get(i));
			watch.seatsinfo.remove(i);
                        count++;}
		
		return finalised_seats;

	}

         public void rateOurSite(){
            System.out.println("Rate us on the scale of 5.\n 1.Very Bad\n2.Bad\n3.Neutral\n4.Good\n5.Very Good\n");
                     int input=object.nextInt();
                        if((input==1)||(input==2)){
                        System.out.println("Sorry for not a good experience:( We shall give a better service next time\n");
                        return;
                        } 
                        if((input==4)||(input==5)){
                        System.out.println("We are glad!:) We shall give still a better service next time\n");
                        return;
                        } 
                        if(input==3){
                        System.out.println("Sorry for inconvience caused :| We shall give a better service next time\n");
                        return;
                        }
                           
	}

}

 class movie {
	String name;
	int seats;
	 ArrayList<String> seatsinfo;
	movie(String name, int seats, ArrayList<String> seatsinfo) {
		this.name = name;
		this.seats = seats;
		this.seatsinfo = seatsinfo;
	}
}
