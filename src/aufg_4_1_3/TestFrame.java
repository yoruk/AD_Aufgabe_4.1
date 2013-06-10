package aufg_4_1_3;

public class TestFrame {
	public static void main(String[] args) {
		System.out.println("Testframe");
		
		BDBList<String> list = new BDBListImpl<String>();
		
		System.out.println("::Eingabe::");
		list.addEnd("a");
		list.addEnd("b");
		list.addEnd("c");
		list.addEnd("d");
		list.addEnd("e");
		list.addEnd("f");
		list.addEnd("g");
		list.addEnd("h");
		list.addEnd("i");
		list.addEnd("j");
				
		System.out.println("::Ausgabe::");
		System.out.println(list.get(0));
		System.out.println(list.get(1));
		System.out.println(list.get(2));
		System.out.println(list.get(3));
		System.out.println(list.get(4));
		System.out.println(list.get(5));
		System.out.println(list.get(6));
		System.out.println(list.get(7));
		System.out.println(list.get(8));
		System.out.println(list.get(9));
		
		System.out.println("::Eingabe::");
		list.addBeginning("a");
		list.addBeginning("b");
		list.addBeginning("c");
		list.addBeginning("d");
		list.addBeginning("e");
		list.addBeginning("f");
		list.addBeginning("g");
		list.addBeginning("h");
		list.addBeginning("i");
		list.addBeginning("j");
		
		System.out.println("::Ausgabe::");
		System.out.println(list.get(0));
		System.out.println(list.get(1));
		System.out.println(list.get(2));
		System.out.println(list.get(3));
		System.out.println(list.get(4));
		System.out.println(list.get(5));
		System.out.println(list.get(6));
		System.out.println(list.get(7));
		System.out.println(list.get(8));
		System.out.println(list.get(9));
       
		System.out.println("::Eingabe::");
		list.addBeginning("a");
		list.addBeginning("b");
		list.addBeginning("c");
		list.addBeginning("d");
		list.addEnd("e");
		list.addEnd("f");
		list.addEnd("g");
		list.addEnd("h");
		list.addBeginning("i");
		list.addEnd("j");
		
		System.out.println("::Ausgabe::");
		System.out.println(list.get(0));
		System.out.println(list.get(1));
		System.out.println(list.get(2));
		System.out.println(list.get(3));
		System.out.println(list.get(4));
		System.out.println(list.get(5));
		System.out.println(list.get(6));
		System.out.println(list.get(7));
		System.out.println(list.get(8));
		System.out.println(list.get(9));
	}
}

/*

0 1 2 3 4 5 6 7
1 2 3 4 5 6 7 8

lenge = 8 	mid 5
			
				
0 1 2 3 4 5 6 7	
a b c d r f g h				
				
									4r
								-2c		2g		
							-1b		1d-1f	1h
						0,5a
						
1 2 3 4 5 6 7 8
0 1 2 3 4 5 6 7									
a b c d r f g h					
										5
										r
									
								-3				3
								b				h
							-2		2		-2			2
							null	d 	   f			null
						-1		1 -1 1   -1	1		-1		1
						null	a  c r	  r	g		null	null
						

						
a b c 4 5 6 7 8 9 10 11 12 13 14 15 16
0 1 2 3 4 5 6 7 8 9  10 11 12 13 14 15                                                 09
										          09
									    -5                    +5
									    04                    14
								  -3          +3        -3          +3
								  01          07        11          XX
						      -2    +2    -2    +2    -2    +2    -2    +2
                              XX    03    05  	09    09	13	  15	XX
                            -1  +1-1  +1-1  +1-1  +1-1  +1-1  +1-1  +1-1  +1
							XX  XX02  0404  0608  1008  1012  1414  16XX  XX
							
							                      09
										          09-1
									    -5                    +5
									    04-1                    14
								  -3          +3        -3          +3
								  01-1          07        11          XX
						      -2    +2    -2    +2    -2    +2    -2    +2
                              XX    03-1    05  	09    09	13	  15	XX
                            -1  +1-1  +1-1  +1-1  +1-1  +1-1  +1-1  +1-1  +1
							XX  XX02-1  0404  0608  1008  1012  1414  16XX  XX
						
0 1 2 3 4 5 6
1 2 3 4 5 6 7

länge = 7 mid 4

			4
	1		35
		2		6	

 7 1 2 3 4 5 6
 
 

leere list

					1
					
baum mit 4

1 2 3 4									3
a b	c d									c
									-2     2
									 a     N
								  -1  1  -1  1
								   N  b   d  N

*/