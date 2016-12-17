/*
 * menu.h
 *
 *  Created on: Jan 2, 2015
 *      Author: Daniel Mizzi
 */

#ifndef SRC_MENU_MENU_H_
#define SRC_MENU_MENU_H_

/*
 * Operation:		Prints the menu to the user
 */
void showMenu(void);

/*
 * Operation: 		Reads the user's choice
 * Postcondition: 	Returns the character inputted by the user
 */
char getChoice(void);

/*
 * Operation:		Creates a new PriorityQueue according to inputted data
 * Precondition:	hospitals points to an object array of type PriorityQueue
 */
void loadQueue(PriorityQueue *hospitals[]);

/*
 * Operation:		Saves a PriorityQueue's elements in a file
 * Precondition:	hospitals points to an object array of type PriorityQueue
 */
void saveQueue(PriorityQueue *hospitals[]);

/*
 * Operation:		Adds a PriorityQueue to the given array of PriorityQueues
 * Precondition:	hospitals points to an object array of type PriorityQueue
 */
void addHospital(PriorityQueue *hospitals[]);

/*
 * Operation:		Outputs the given PriorityQueue's elements
 * Precondition:	hospitals points to an object array of type PriorityQueue, loadedAmount is a value of type int
 */
void outputHospital(PriorityQueue *hospitals[], int loadedAmount);

/*
 * Operation:		Merges the PriorityQueues decided by the user
 * Precondition:	hospitals points to an object array of type PriorityQueue
 */
void mergeHospitals(PriorityQueue *hospitals[], int loadedAmount);

/*
 * Operation:		Removes a PriorityQueue according to the user's choice
 * Precondition:	hospitals points to an object array of type PriorityQueue
 */
void removeHospital(PriorityQueue *hospitals[], int loadedAmount);

/*
 * Operation:		Adds an element to the PriorityQueue chosen by the user
 * Precondition:	hospitals points to an object array of type PriorityQueue
 */
void addPatient(PriorityQueue *hospitals[], int loadedAmount);

/*
 * Operation:		Gets the first element in the PriorityQueue chosen by the user
 * Precondition:	hospitals points to an object array of type PriorityQueue, loadedAmount is a value of type int
 */
void peakPatient(PriorityQueue *hospitals[], int loadedAmount);

/*
 * Operation:		Removes an element from the PriorityQueue chosen by the user
 * Precondition:	hospitals points to an object array of type PriorityQueue
 */
void removePatient(PriorityQueue *hospitals[], int loadedAmount);

/*
 * Operation:		Reads a string inputted by the user and stores it in a character array
 * Precondition:	name points to an character array, length is of type int
 */
void getString(char *name, int length);

/*
 * Operation:		Reads an integer inputted by the user with the given boundaries
 * Precondition:	lowerBound and higherBound are both of type int
 * Postcondition:	Returns an integer inputted by the user with the given boundaries
 */
int readInt(int lowerBound, int higherBound);

#endif /* SRC_MENU_MENU_H_ */
