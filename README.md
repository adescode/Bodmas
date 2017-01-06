## Bodmas
##calculator
The calculator application is a simple terminal (command line) based application that allows a user perform simple calculations, define arithmetic formula and use them.
The program allows a user enter input text and respond based on what the user enters. The following actions should be possible:

1. Simple arithmetic expressions e.g. 2 * 5 - (3 + 2)
	The calculation should be done based on BODMAS and the result displayed.
	The supported operators would be (), *, /, +, - and ^
  
2. When the input is an expression of the form:
	fn = expr where fn is a string denoting a formula name and expr is an arithmetic expression that may contain variables (parameters of the formula), the formula should be saved to storage in such a way that it can be retrieved using the formula name.
  
3. When the input is a simple string e.g. fn, the program should check if there exists a formula with the given name and display the formula. If not display an error message.

4. To use a formula, an expression of the form fn(a,b,c) should be entered by the user; where fn is the formula name and a, b and c are parameters in the formula.

5. a, b, or c in the expression above could themselves contain nested formula and expression e.g. fn(a, b, fn(c, d, e))

The program should run (request input and provide output) indefinitely until a chosen string is entered as input which would signal to the program to terminate.
Invalid input (not matching any expected format) should attempt to provide a meaning error message to the user.
###Adescode

