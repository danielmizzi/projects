/*
 * buffer.h
 *
 *  Created on: Dec 27, 2014
 *      Author: Daniel Mizzi
 */

#ifndef BUFFER_H_
#define BUFFER_H_

#include <stdbool.h>

struct user {
	char name[15];
	char surname[15];
	int id;
};

typedef struct user Item;

struct node {
	Item data;
	int priority;
	bool free;
};

typedef struct node Node;

struct circularbuffer {
	Node *head;
	Node *tail;
	Node *oldest;
	Node *free;
	Node *latest;
	int maxSize;
	int size;
	bool full;
};

typedef struct circularbuffer CircularBuffer;

/*
 * Operation:		Creates a new CircularBuffer with the given size
 * Precondition:	length is a valid value of type int
 * Postcondition:	Returns a new CircularBuffer with the given size
 */
CircularBuffer* create(int length);

/*
 * Operation:		Destroys the given instance of CircularBuffer
 * Precondition:	buffer points to an object of type CircularBuffer
 */
void destroy(CircularBuffer *buffer);

/*
 * Operation:		Clears all the elements from the given CircularBuffer
 * Precondition:	buffer points to an object of type CircularBuffer
 */
void clearBuffer(CircularBuffer *buffer);

/*
 * Operation:		Adds the given item to the given CircularBuffer with the given priority
 * Precondition:	buffer points to an object of type CircularBuffer, item is a struct, priority is a value of type int
 */
void addNode(CircularBuffer *buffer, Item item, int priority);

/*
 * Operation:		Removes the element with the highest priority from the given CircularBuffer
 * Precondition:	buffer points to an object of type CircularBuffer
 */
void removeNode(CircularBuffer *buffer);

/*
 * Operation:		Gets the element at a particular index from the given CircularBuffer
 * Precondition:	buffer points to an object of type CircularBuffer, index is of type int
 * Postcondition:	Returns the element at a particular index from the given CircularBuffer
 */
Node* getNode(CircularBuffer *buffer, int index);

/*
 * Operation: 		Prints the given Node
 * Precondition:	node points to an object of type Node
 */
void printNode(Node *node);

/*
 * Operation: 		Prints all the elements in the given CircularBuffer
 * Precondition:	buffer points to an object of type CircularBuffer
 */
void printBuffer(CircularBuffer *buffer);

/*
 * Operation:		Checks whether the given CircularBuffer is full
 * Precondition:	buffer points to an object of type CircularBuffer
 * Postcondition:	Returns true if the given CircularBuffer is full; otherwise false
 */
bool isFull(CircularBuffer *buffer);

/*
 * Operation:		Checks whether the given CircularBuffer is empty
 * Precondition:	buffer points to an object of type CircularBuffer
 * Postcondition:	Returns true if the given CircularBuffer is empty; otherwise false
 */
bool isEmpty(CircularBuffer *buffer);

/*
 * Operation:		Gets the number of elements in the given CircularBuffer
 * Precondition:	buffer points to an object of type CircularBuffer
 * Postcondition:	Returns the number of elements in the given CircularBuffer
 */
int getSize(CircularBuffer *buffer);

#endif /* BUFFER_H_ */
