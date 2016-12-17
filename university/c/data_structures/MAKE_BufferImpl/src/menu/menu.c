/*
 * menu.c
 *
 *  Created on: Jan 6, 2015
 *      Author: Daniel Mizzi
 */

#include "../queue/priorityqueue.h"
#include <ctype.h>
#include <stdio.h>
#include <string.h>

void showMenu() {
	fflush(stdout);
	// flush the buffer for any trailing new lines
	// print the choices for the user
	printf("Choose an option:\n");
	printf("\tl. Load\n");
	printf("\ts. Save\n");
	printf("\t1. Add a hospital location\n");
	printf("\t2. Output a hospital\n");
	printf("\t3. Remove a hospital location\n");
	printf("\t4. Add a patient\n");
	printf("\t5. Get first patient\n");
	printf("\t6. Remove a patient\n");
	printf("\t7. Merge two hospitals\n");
	printf("\t8. Quit\n");
}

char getChoice() {
	char c = getchar(); // get a character and assign it
	if (c == '\n') {
		// re-assign character if previous assignment was a new-line character
		c = getchar();
	}
	while (getchar() != '\n') {
		// keep looping until we reach a new line
		continue;
	}
	return c;
}

void loadQueue(PriorityQueue *hospitals[]) {
	printf("\n");
	printf("--- LOAD A HOSPITAL ---\n");
	printf("\tPlease enter the file name: ");
	char name[15];
	getString(name, 15);
	FILE *File = fopen(name, "r"); // open file with queue's name, using 'r' for reading
	if (File == NULL) { // unable to open file
		printf("\nFile not found, or an error has occurred while trying to open it!");
	} else {
		int i;
		for (i = 0; i < 5; i ++) { // loop through all priority queues
			if (hospitals[i] == NULL) { // find the first null queue in the array
				load(hospitals + i, File, name); // load the queue
				break; // exit out of loop since we found the first null element
			}
		}
	}
	printf("\n"); // put new line for better readability
}

void saveQueue(PriorityQueue *hospitals[]) {
	int i;
	for (i = 0; i < 5; i ++) { // loop through all priority queues
		if (hospitals[i] != NULL) { // only store the queue if it's not null
			store(hospitals[i]); // store the queue
		}
	}
}

void addHospital(PriorityQueue *hospitals[]) {
	int i;
	// add a priority queue to the first null element, so have to find the index
	for (i = 0; i < 5; i ++) { // loop through all priority queues
		if (hospitals[i] == NULL) {
			// found the index, so break out of loop
			break;
		}
	}
	hospitals[i] = create_q(20); // initalize the queue
	printf("Please enter the hospital name:\n");
	getString(hospitals[i]->name, 15); // store the name of the queue inputted by the user
}

void outputHospital(PriorityQueue *hospitals[], int loadedAmount) {
	int count = 1;
	int i;
	printf("- Printing loaded hospitals:\n");
	PriorityQueue *temp[loadedAmount]; // keep a copy of the pointer to the array
	for (i = 0; i < 5; i ++) { // loop through array of priority queues
		if (hospitals[i] != NULL) { // print the queue's name to the user
			printf("\t%d. %s\n", count, hospitals[i]->name);
			temp[count - 1] = hospitals[i]; // re-arrange the non-null elements in the new array
			count+=1;
		}
	}
	printf("\nEnter the number of the hospital you wish to output: ");
	int input = readInteger(1, loadedAmount); // read the index of the chosen hospital, with the set bounds
	printf("\nName of hospital: %s\n", temp[input - 1]->name);
	if (size(temp[input - 1]) > 0) { // check if queue is empty
		output(temp[input - 1]); // output elements since queue is not empty
	} else {
		printf("... hospital is empty! Please add some patients!\n"); // tell user that the queue is empty
	}
}

void mergeHospitals(PriorityQueue *hospitals[], int loadedAmount) {
	if (loadedAmount < 2) {
		// we can only merge if there are at least two queues available
		printf("There are not enough hospitals to merge one another!\n");
	} else {
		int count = 1;
		int i;
		printf("- Printing loaded hospitals:\n");
		PriorityQueue * * temp[loadedAmount];
		for (i = 0; i < 5; i ++) { // loop through array of priority queues
			if (hospitals[i] != NULL) {
				printf("\t%d. %s\n", count, hospitals[i]->name);
				*temp = hospitals + i; // make pointer point to element
				(*temp)++; // increment pointer
				count+=1; // increase counter
			}
		}
		(*temp) -= (count - 1); // reset the pointer
		printf("\nEnter the number of the first hospital you wish to merge: ");
		int input_1 = readInteger(1, loadedAmount); // read the index from the user
		printf("Enter the number of the second hospital you wish to merge: ");
		int input_2 = readIntegerExcept(1, loadedAmount, input_1);
		// read index again, making sure it's not the previous one
		// use pointers to update the actual data
		PriorityQueue *new = merge(*((*temp) + (input_1 - 1)), *((*temp) + (input_2 - 1)));
		*((*temp) + (input_2 - 1)) = NULL; // set the second queue to null since it was merged
		*((*temp) + (input_1 - 1)) = new; // replace the first queue with the new one
		printf("Enter the name of the new hospital: "); // ask for the new queue's name
		getString(new->name, 15); // set the new queue's name
	}
}

void removeHospital(PriorityQueue *hospitals[], int loadedAmount) {
	int count = 1;
	int i;
	printf("- Printing loaded hospitals:\n");
	PriorityQueue * * temp[loadedAmount];
	for (i = 0; i < 5; i ++) { // loop through array of priority queues
		if (hospitals[i] != NULL) {
			printf("\t%d. %s\n", count, hospitals[i]->name);
			*temp = hospitals + i; // update temp pointer
			(*temp)++; // increment pointer
			count+=1;
		}
	}
	(*temp) -= (count - 1); // reset pointer
	printf("\nEnter the number of the hospital you wish to remove: ");
	int input = readInteger(1, loadedAmount); // read index
	*((*temp) + (input - 1)) = NULL; // make the pointer point to null
}

void addPatient(PriorityQueue *hospitals[], int loadedAmount) {
	int count = 1;
	int i;
	printf("- Printing loaded hospitals:\n");
	PriorityQueue *temp[loadedAmount]; // store a copy of the priority queues
	for (i = 0; i < 5; i ++) { // loop through the array
		if (hospitals[i] != NULL) { // queue is not null so we operate on it
			printf("\t%d. %s\n", count, hospitals[i]->name);
			temp[count - 1] = hospitals[i];
			count+=1;
		}
	}
	printf("\nEnter the number of the hospital you wish to add to: ");
	int input = readInteger(1, loadedAmount); // read the index
	if (getSize(temp[input - 1]->buffer) == temp[input - 1]->size) {
		// the queue is full so we cannot add more elements, notify user
		printf("\nThis hospital is currently full! Please wait for the patients to be ready or try another hospital!");
	} else {
		// the queue is not full so we are able to add more elements
		printf("\nAdding patient to hospital: %s\n", temp[input - 1]->name);
		struct user item = {}; // make an empty struct
		printf("Please enter the patient's first name:\n ");
		getString(item.name, 15); // read the string into the struct
		printf("Please enter the patient's surname:\n ");
		getString(item.surname, 15); // read the string into the struct
		printf("Please enter the patient's ID number:\n ");
		item.id = readInteger(100000, 999999); // accept a 6-figure digit
		printf("Please enter the patient's priority (0 to 2, 2 being highest)\n");
		int p = readInteger(0, 2);
		while (isFull(temp[input - 1]->buffer[p])) { // keep asking for a different priority
			printf("... we currently have no space to assign that kind of priority. Please assign a different priority!\n");
			p = readInteger(0, 2);
		}
		enqueue(temp[input - 1], item, p); // add the element
	}
}

void peakPatient(PriorityQueue *hospitals[], int loadedAmount) {
	if (loadedAmount == 0) {
		printf("There are currently no hospitals, please add one!\n");
		return;
	}
	int count = 1;
	int i;
	printf("- Printing loaded hospitals:\n");
	PriorityQueue *temp[loadedAmount]; // store a copy of the priority queues
	for (i = 0; i < 5; i ++) { // loop through the array
		if (hospitals[i] != NULL) { // queue is not null so we operate on it
			printf("\t%d. %s\n", count, hospitals[i]->name);
			temp[count - 1] = hospitals[i];
			count+=1;
		}
	}
	printf("\nEnter the number of the hospital you wish to access: ");
	int input = readInteger(1, loadedAmount); // read the index
	if (is_empty(temp[input - 1])) { // no elements in the queue
		printf("\nThere are currently no patients in this hospital!\n");
	} else {
		printNode(peek(temp[input - 1])); // print the peek node
	}
}

void removePatient(PriorityQueue *hospitals[], int loadedAmount) {
	int count = 1;
	int i;
	printf("- Printing loaded hospitals:\n");
	PriorityQueue *temp[loadedAmount];
	for (i = 0; i < 5; i ++) { // loop through priority queues
		if (hospitals[i] != NULL) { // print queue if it's not null
			printf("\t%d. %s\n", count, hospitals[i]->name);
			temp[count - 1] = hospitals[i];
			count+=1;
		}
	}
	printf("\nEnter the number of the hospital you wish to remove from: ");
	int input = readInteger(1, loadedAmount); // read the index
	if (is_empty(temp[input - 1])) { // queue is empty
		printf("\nThere are no patients to remove, please try another hospital!\n");
	} else {
		// queue is not empty, so we can dequeue it
		dequeue(temp[input - 1]);
	}
}

void getString(char *name, int length) {
	fgets(name, length, stdin);
	char * c = strchr(name, '\n');
	if (c != NULL) {
		*c = '\0';
	} else {
		// flush the buffer if it overflowed in the previous fgets
		int ch;
		while ((ch=getchar()) != '\n' && ch != EOF); // keep looping until we meet the condition
	}
}

int readInteger(int lowerBound, int higherBound) {
	int value;
	fflush(stdout); // flush the buffer
	while (scanf("%d", &value) != 1 || value < lowerBound || value > higherBound)  {
		// keep looping while the value is not valid
		clearInput();
		printf("Invalid input, please try again: ");
		fflush(stdout);
	}
	clearInput();
	return value;
}

int readIntegerExcept(int lowerBound, int higherBound, int except) {
	int value;
	fflush(stdout); // flush the buffer
	while (scanf("%d", &value) != 1 || value < lowerBound || value > higherBound || value == except)  {
		// keep looping while the value is not valid
		clearInput();
		printf("Invalid input, please try again: ");
		fflush(stdout);
	}
	clearInput();
	return value;
}

void clearInput() {
	int ch;
	clearerr(stdin); // clear the end-of-file and error indicators for the input stream
	do {
		ch = getc(stdin); // get character and assign it
	} while (ch != '\n' && ch != EOF); // keep looping while the character is not a new-line and end-of-file
	clearerr(stdin); // clears the end-of-file and error indicators for the input stream
}
