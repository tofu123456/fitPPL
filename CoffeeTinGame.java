

public class CoffeeTinGame {

    /* constant value for the green bean*/
    private static final char GREEN = 'G';
    /* constant value for the blue bean*/
    private static final char BLUE = 'B';
    /* constant for removed beans */
    private static final char REMOVED = '-';

    
    
        
    /**
     * Performs the coffee tin game to determine the color of the last bean
     * 
     * @requires tin neq null /\ tin.length >= 2
     * @modifies tin
     * @effects
     *      intitial last = REMOVED
     *      beansCount = tin.length 
     * 
     *      while beansCount >= 2 \/ (num =length{x| x in tin /\ x = REMOVED}) < tin.length - 2
     *         
     *          @link takeTwo(char[]): take two random beans neq REMOVED
     *         
     *          @link updateTin(int[], char[]) : update tin after comparing two beans
     * 
     *          beansCount = beansCount - 1
     * 
     *          @link printTin(char[]) print content of the tin currently
     *      
     *      foreach element e in tin
     *          if e neq REMOVED
     *              last = e
     *      
     *      return last
     */
    private static char tinGame(char[] tin) {
        char last = REMOVED;
        int beansCount = tin.length;

        //n = no of green beans /\ m = no of blue beans /\ n+m = beansCount
        while (beansCount >= 2) {  //(M1.1: 2 <= beansCount <= tin.length)
            
            // take two beans then compare its color
            int[] arr = takeTwo(tin); //M1.2 arr.length = 2 /\ arr[0] != arr[1] /\ 
                                                // arr[0] /\ arr[1] != REMOVED
            updateTin(arr, tin); // arr.length = 2 /\ tin[arr[0]] = REMOVED /\ 
                                        // tin[arr[1]] = BLUE \/ tin[arr[1]] GREEN

            beansCount--; // beansCount = beansCount - 1 /\ M1.1 /\ (n + m) = (n + m) - 1

            printTin(tin);
        }  // beansCount = 1 /\ n+m = 1 /\ |{x| x in tin[0 to j] /\ j = tin.length - 1 /\ x = REMOVED}| = tin.length -1

        for (char c : tin) {  //M1.3:  0<=i<=tin.length) /\ c = tin[i]
            if (c != REMOVED) {  // tin[i] != REMOVED /\ M1.3
                last = c;  // M1.4: last in tin /\ last != REMOVED
            }
        }
        // i = tin.length /\ M1.4
        return last; 
    }
    
    
    
    
    /**
     * Take two beans from tin randomly
     * 
     * @requires tin not null /\ tin.length >= 2 /\ 
     *          |{x|x in tin /\ x = REMOVED}| < tin.length - 2
     * @effect 
     *      initial a /\ a.lenth = 2
     *      repeatedly create the first interger and assign to a[0] until
     *          tin[a[0]] neq REMOVED then 
     *              repeatedly create the second interger and assign to a[1] until
     *                 a[1] neq a[0] /\ tin[a[1]] neq tin[a[0]
     *      
     *      return a
     * 
     */         
    private static int[] takeTwo(char[] tin) {
        int[] a = new int[2]; // a = [a[0], a[1] /\ a.length = 2 /\ tin_0 = tin_0
        boolean isDone1 = false, isDone2 = false;
        
        while (!isDone1) {  // isDone1 neq true
            a[0] = (int) (Math.random() * tin.length); //M2.1: isDone1 = false /\ 0 <= a[0] < tin.length
            if (tin[a[0]] != REMOVED) {  // tin[a[0]] neq REMOVED /\ M2.1
                isDone1 = true; // M2.2: isDone1 = true /\ tin[a[0]] neq REMOVED
                
                while (!isDone2) {  // M2.2 /\ isDone2 = false
                    a[1] = (int) (Math.random() * tin.length); // M2.3: M2.2 /\ isDone2 = false /\ 
                                                                // 0 <= a[1] < tin.length
                    if (a[0] != a[1]) { // M2.3 /\ a[0] neq a[1]
                        if (tin[a[1]] != REMOVED) { // // M2.3 /\ a[0] neq a[1] /\ tin[a[1]] neq REMOVED
                            isDone2 = true; // isDone2 = false /\ M2.2 /\ a[0] neq a[1] /\ tin[a[1]] neq REMOVED 
                        }
                    }
                }   //M2.4: isDone2 = true /\ isDone1 = true /\ a[0] neq a[1] /\
                                    // tin[a[0]] neq REMOVED /\ tin[a[1]] neq REMOVED
            }// M2.4
        }// M2.4
        
        return a;
    }

    
    
    
    
    
    /**
     * Update color of the two selected beans
     * 
     * @requires a neq null /\ a.length = 2 /\ a[0] neq a[1] 
     *           tin neq null /\ |{x|x in tin /\ x = REMOVED}| < tin.length - 2 /\
     *                      tin[a[0]] neq REMOVED /\ tin[a[1] neq REMOVED
     * @modifies tin
     * @effect 
     *      first = a[0]
     *      second = a[1]
     *      if two bean in tin at index of first and second are same color
     *          set the color of bean at index of first to REMOVED
     *          set the color of bean at index of second to BLUE
     *      else
     *          set the color of bean at index of first to REMOVED
     *          set the color of bean at index of second to GREEN
     */
    private static void updateTin(int[] a, char[] tin) {
        int first = a[0];   // first = a[0] /\ a.length = 2 /\ tin[first]_0 neq REMOVED
        int second = a[1];  // second = a[1]  /\ first = a[0] /\ first neq second /\ tin[second]_0 neq REMOVED
        
        if (tin[first] == tin[second]) {    // tin_0[first] = BLUE \/ tin_0[first] = GREEN /\ tin_0[first] = tin_0[second]
            tin[first] = REMOVED;   // tin_0[first] = REMOVED /\ (tin_0[second] = BLUE \/ tin_0[second] = GREEN)
            tin[second] = BLUE;     // tin_0[second] = BLUE /\ tin[first] = REMOVED
        } else {        // (tin_0[first] = BLUE /\ tin_0[second] = GREEN ) \/ (tin_0[first] = GREEN /\ tin_0[second] = BLUE)
            tin[first] = REMOVED;   // tin_0[first] = REMOVED /\ (tin_0[second] = BLUE \/ tin_0[second] = GREEN)
            tin[second] = GREEN;    // tin_0[second] = GREEN /\ tin[first] = REMOVED
        }
        // tin[first] = REMOVED /\ (tin[second] = BLUE \/ tin[second] = GREEN)
        // first = a[0] /\ second = a[1] /\ first neq second
    }



    
    /**
     * the main procedure
     *
     * @effects 
     * {@link createTin()initialise a coffee tin randomly
     * count green beans in tin
     * if number of green bean is odd, 
     *      expexted last bean color is green
     * else 
     *      last bean color must be blue
     * {@link printTin(char[])}:    print the tin content 
     * {@link tinGame(char[])}:     perform the coffee tin game on tin 
     * {@link printTin(char[])}:    print the tin content again 
     * if last bean is correct 
     *      {@link  System#out#printf(String, Object...)}: print its colour 
     * else 
     *      {@link System#out#printf(String, Object...)}: print an error message
     */
    public static void main(String[] args) {
        // initialise beans randomly
        char[] tin = createTin(); // 2 <= tin.length <= 11
        
        // count number of greens
        int greenCount = 0;
        // count the number of green beans in tin
        for (int i = 0; i < tin.length; i++) {
            // 0 <=i<tin.length 
            if (tin[i] == GREEN) { //M1: 0 <=i<tin.length /\ tin[i] = GREEN
                greenCount++;   // M1 /\ greenCount = greenCount + 1
            }
        }
        // i = tin.length /\ greenCount = length (|{x| x in tin[0 to i - 1] /\ x = GREEN}|)
        
        // determine the expected color of the last bean
        final char last = (greenCount % 2 == 1) ? GREEN : BLUE;
        // 2 <= tin.length <= 11 /\ (greens % 2 = 1 -> last = GREEN) /\ 
        //                   (greens % 2 = 0 -> last = BLUE)
        
        // print the content of tin before the game
        System.out.println("Tin before:");
        printTin(tin);
        
        // perform the game
        System.out.println("\nPlay Tin game:");
        char lastBean = tinGame(tin);
        // lastBean in tin /\ lastBean = last
        
        // print the content of tin and last bean
        System.out.println("\nTin after:");
        printTin(tin);

        if (lastBean == last) {
            System.out.println("\nLast bean: " + lastBean);
        } else {
            System.out.printf("Oops, wrong last bean: %c (expected: %c).%n", lastBean, last);
        }
    }
    


    
    /**
     * Create a tin randomly
     * @effect  
     *      intitial length of array len randomly /\ 2 <= len <= 11
     *      create tin /\ tin.length = len /\ 2 <= tin.length <= 11
     *      assign new value for each element e in tin /\ e = BLUE \/ e = GREEM
     *      return tin
     */
    private static char[] createTin() {
        int len = (int) (Math.random() * 10 + 2); // 2 <= len <= 11
        char[] tin = new char[len]; // 2 <= tin.length <= 11 /\ 2 <= len <= 12
        
        for (int i = 0; i < tin.length; i++) {
            // i <= 0 < tin.length
            int bean = (int) (Math.random() * 2); // bean = 0 \/ bean = 1

            tin[i] = (bean == 0) ? GREEN : BLUE; // (bean = 0 /\ tin[i] = GREEN) \/  (bean = 1 /\ tin[i] = BLUE)
        }
        // i = len /\ tin = {x| x0, x1,..xj /\ j = i - 1 /\ x = BLUE \/ x = GREEN}
        return tin; // i = len /\ tin = {x| x0, x1,..xj /\ j = i - 1 /\ x = BLUE \/ x = GREEN}
    }

 
    
    /**
     *  print the content of tin
     * @requires tin not null /\ tin.length>0 
     * @effect
     *      foreach element e in tin
     *          print e
     */
    private static void printTin(char[] tin) {
        
        for (char c : tin) {
            System.out.print(c + " ");
        }
        System.out.println();
    }


    
  
}
