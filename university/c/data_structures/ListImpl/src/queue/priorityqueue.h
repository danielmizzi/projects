/*
 * priorityqueue.h
 *
 *  Created on: Jan 1, 2015
 *      Author: Daniel Mizzi
 */

#ifndef QUEUE_PRIORITYQUEUE_H_
#define QUEUE_PRIORITYQUEUE_H_

#include <stdio.h>
#include <stdbool.h>
#include "../linkedlist.h"

struct priorityqueue {
	LinkedList *list;
	int size;
	bool full;
	char name[15];
};

typedef struct priorityqueue PriorityQueue;

/*
 * Operation:		Creates a priority queue with the given maximum size
 * Precondition:	max_size is an integer number
 * Postcondition:	Returns the created priority queue with the given maximum size
 */
PriorityQueue *create_q(int max_size);

/*
 * Operation:		Adds the given item with a certain priority to the priority queue
 * Precondition:	q points to an object of type PriorityQueue, x is an item and p is a value
 * Postcondition:
 */
void enqueue(PriorityQueue *q, Item x, int p);

/*
 * Operation:		Removes the first item with the highest priority from the priority queue
 * Precondition:	q points to an object of type PriorityQueue
 */
void dequeue(PriorityQueue *q);

/*
 * Operation:		Gets the first element with the highest priority from the priority queue
 * Precondition:	q points to an object of type PriorityQueue
 * Postcondition:	Returns the first element with the highest priority
 */
Node *peek(PriorityQueue *q);

/*
 * Operation:		Checks whether the given priority queue is empty or not
 * Precondition:	q points to an object of type PriorityQueue
 * Postcondition:	Returns true if the priority queue is empty; otherwise false
 */
bool is_empty(PriorityQueue *q);

/*
 * Operation:		Gets the number of elements in the priority queue
 * Precondition:	q points to an object of type PriorityQueue
 * Postcondition:	Returns the number of elements in the priority queue
 */
int size(PriorityQueue *q);

/*
 * Operation:		Merges the two priority queues given, keeping order constant
 * Precondition:	q and r point to objects of type PriorityQueue
 * Postcondition:	Returns a new queue with the elements combined from the given priority queues
 */
PriorityQueue *merge(PriorityQueue *q, PriorityQueue *r);

/*
 * Operation:		Removes all the elements from the given PriorityQueue
 * Precondition:	q points to an object of type PriorityQueue
 */
void clear(PriorityQueue *q);

/*
 * Operation:		Store the given priority queue in a file
 * Precondition:	q points to an object of type PriorityQueue
 * Postcondition:	Returns true if the priority queue was stored; otherwise false
 */
bool store(PriorityQueue *q);

/*
 * Operation:		Loads the elements found in the given file in the given priority queue
 * Precondition:	q points to a pointer to an object of type PriorityQueue, File points to an object of type FILE, name points to an object of type char
 * Postcondition:	Returns true if the priority queue was loaded; otherwise false
 */
bool load(PriorityQueue * *q, FILE *File, char *name);

/*
 * Operation:		Outputs all of the elements in the given priority queue
 * Precondition:	q points to an object of type PriorityQueue
 */
void output(PriorityQueue *q);

#endif /* QUEUE_PRIORITYQUEUE_H_ */
