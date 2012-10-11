jfastparser
===========

Very fast parsing of doubles and ints from a string

Usage
=====

    String input = "123 56.4 12:3";
    Parser parser = new Parser(input);
    int v1 = parser.eatInt();
    parser.eatWhitespace();
    double v2 = parser.eatDouble();
    parser.eatWhitespace();
    double v3 = parser.eatDouble();

Concept
=======

Instantiate a Parser object with a string, call eat methods to progressively 'eat' the 
string.

Methods
=======

    parser.eatInt(); // eats an integer from the string, or throws 
                     // RuntimeException
    parser.eatDouble(); // eats double from the string, or throws 
                        // RuntimeException
    parser.eatChar(somechar); // eats somechar from the string, or throws 
                              // RuntimeException
    parser.eatWhitespace(); // eats any whitespace from the string from the 
                            // current position
    parser.more(); // is there anything else to read?

Performance
===========

Test: parse a string of doubles, created like this:

    StringBuilder builder = new StringBuilder();
    builder.append(0);
    int N = 1000000;
    for( int i = 1; i < N; i++ ) {
    builder.append(" " + ( i / 10.0 ) );
    }
    String line = builder.toString();

Code:

Scanner:

	byte[] bytes = line.getBytes();
	ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
	Scanner scanner = new Scanner(byteArrayInputStream);
    while(scanner.hasNext()) {
	    double value =  scanner.nextDouble();
	    values[n] = value;
	    n++;
    }		

Split:

    String[] splitline = line.split(" " );
    for( String token : splitline ) {
	    double value =  Double.parseDouble(token);
	    values[n] = value;
	    n++;
    }

StringTokenizer:

    StringTokenizer stringTokenizer = new StringTokenizer(line);
    while( stringTokenizer.hasMoreTokens()) {
	    double value = Double.parseDouble( stringTokenizer.nextToken() );
	    values[n] = value;
	    n++;
    }		

JFastParser:

    parser = new Parser(line);
    while(parser.more()) {
	    double value =  parser.eatDouble();
	    parser.eatWhitespace();
	    values[n] = value;
	    n++;
    }		


Timings:

    Scanner: 10642 ms
    split: 715 ms
    StringTokenizer: 544ms
    JFastParser: 290ms

