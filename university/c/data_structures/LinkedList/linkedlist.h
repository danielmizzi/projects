/*
 * linkedlist.h
 *
 *  Created on: Dec 27, 2014
 *      Author: Daniel Mizzi
 */

#ifndef LINKEDLIST_H_
#define LINKEDLIST_H_

#define LOW_PRIORITY 0
#define MID_PRIORITY 1
#define HIGH_PRIORITY 2

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
	struct node *next;
};

typedef struct node Node;

struct list {
	Node *head;
	int size;
	int max_size;
};

typedef struct list LinkedList;

/*
 * Operation:		Creates a new LinkedList
 * Postcondition:	Returns a new LinkedList
 */
LinkedList* create(void);

/*
 * Operation:		Inserts an item with the given priority in the given LinkedList
 * Precondition:	list points to an object of type LinkedList, priority is a value of type int, item is a struct
 */
void insertNode(LinkedList *list, int priority, Item item);

/*
 * Operation:		Inserts an item with the given priority in the given LinkedList at a particular index
 * Precondition:	list points to an object of type LinkedList, priority and index are values of type int, item is a struct
 */
void insertNodeAtIndex(LinkedList *list, int priority, Item item, int index);

/*
 * Operation:		Removes the node with the highest priority from the given LinkedList
 * Precondition:	list points to an object of type LinkedList
 */
void removeNode(LinkedList *list);

/*
 * Operation:		Removes the node at the given index from the given LinkedList
 * Precondition:	list points to an object of type LinkedList, index is a value of type int
 */
void removeNodeAtIndex(LinkedList *list, int index);

/*
 * Operation:		Gets the node with the given priority from the given LinkedList
 * Precondition:	list points to an object of type LinkedList, priority is a value of type int
 * Postcondition:	Returns the node with the given priority from the given LinkedList
 */
Node *getNode(LinkedList *list, int priority);

/*
 * Operation:		Gets the node at the given index from the given LinkedList
 * Precondition:	list points to an object of type LinkedList, index is a value of type int
 * Postcondition:	Returns the node at the given index from the given LinkedList
 */
Node *getNodeAtIndex(LinkedList *list, int index);

/*
 * Operation:		Gets the number of elements in the given LinkedList
 * Precondition:	list points to an object of type LinkedList
 * Postcondition:	Returns the number of elements in the given LinkedList
 */
int getSize(LinkedList *list);

/*
 * Operation: 		Prints the given Node
 * Precondition:	node points to an object of type Node
 */
void printNode(Node *node);

/*
 * Operation:		Prints all the nodes in the given LinkedList
 * Precondition:	list points to an object of type LinkedList
 */
void printList(LinkedList *list);

#endif /* LINKEDLIST_H_ */
