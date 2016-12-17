/*
 * main.c
 *
 *  Created on: Dec 30, 2014
 *      Author: Daniel Mizzi
 */

#include "queue/priorityqueue.h"
#include "menu/menu.h"

#define MAX_HOSPITALS 5

PriorityQueue *hospitals[MAX_HOSPITALS] = {NULL, NULL, NULL, NULL, NULL};

int getLoadedHospitals(void);

int main(void) {
	setvbuf(stdout, NULL, _IONBF, 0); // alter how the stream should be buffered
	showMenu(); // show the menu to the user
	bool exit = false; // boolean to decide whether to exit the program
	while(!exit) {
		switch (getChoice()) {
			case 'l':
				loadQueue(hospitals); // load a priority queue from a particular file
			break;
			case 's':
				saveQueue(hospitals); // save a priority queue to a particular file
			break;
			case '1':
				if (getLoadedHospitals() == MAX_HOSPITALS) {
					// cannot add more queues as the limit was reached, alert the user
					printf("You currently have the maximum number of hospitals added!\n");
				} else {
					// add a new queue, ask user for info
					addHospital(hospitals);
				}
			break;
			case '2':
				if (getLoadedHospitals() == 0) {
					// cannot output a queue if there are no valid ones loaded
					printf("You currently have no loaded hospitals!\n\n");
				} else {
					// ask the user which queue he wants to output
					outputHospital(hospitals, getLoadedHospitals());
				}
			break;
			case '3':
				if (getLoadedHospitals() == 0) {
					// cannot remove a queue if there are no valid ones loaded
					printf("You currently have no loaded hospitals!\n\n");
				} else {
					// ask the user which queue he wants to remove
					removeHospital(hospitals, getLoadedHospitals());
				}
			break;
			case '4':
				if (getLoadedHospitals() == 0) {
					// cannot add an element if no queues are loaded
					printf("You currently have no loaded hospitals!\n\n");
				} else {
					// call the function to add a new queue
					addPatient(hospitals, getLoadedHospitals());
				}
			break;
			case '5':
				if (getLoadedHospitals() == 0) {
					// cannot get the first patient if no queues are available
					printf("You currently have no loaded hospitals!\n\n");
				} else {
					// call the function to get the first element
					peakPatient(hospitals, getLoadedHospitals());
				}
			break;
			case '6':
				if (getLoadedHospitals() == 0) {
					// cannot remove a patient if no queues exist
					printf("You currently have no loaded hospitals!\n\n");
				} else {
					// call the function to remove a queue
					removePatient(hospitals, getLoadedHospitals());
				}
			break;
			case '7':
				// call the function to merge two queues
				mergeHospitals(hospitals, getLoadedHospitals());
			break;
			case '8':
				// set the boolean to true
				exit = true;
				goto l; // jump to label to avoid unnecessary input
			break;
			default: // user did not input one of the options above
				printf("Please enter a valid option\n");
			break;
		}
		char ch;
		printf("Press any key to continue...\n");
		ch = getchar(); // get a character to allow the user to read console properly
		showMenu(); // re-show menu once the user presses keyboard
		l:; // label to jump to
	}
	return 0;
}

int getLoadedHospitals() {
	int i;
	int count = 0; // set counter to 0
	for (i = 0; i < MAX_HOSPITALS; i ++) { // loop through amount of queues
		if (hospitals[i] != NULL) { // increment counter if queue is not null
			count += 1;
		}
	}
	return count;
}
