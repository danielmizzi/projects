/*
 * priorityqueue.c
 *
 *  Created on: Jan 1, 2015
 *      Author: Daniel Mizzi
 */

#include <stdio.h>
#include <string.h>
#include "priorityqueue.h"

PriorityQueue *create_q(int max_size) {
	PriorityQueue *queue = (PriorityQueue *) malloc(sizeof(PriorityQueue));
	// allocate memory for the priority queue
	if (queue != NULL) {
		LinkedList *list = create(); // initialize the queue's list
		if (list != NULL) {
			queue->list = list; // set the list
			queue->size = max_size; // set the list's max size
			return queue;
		}
	}
	return NULL;
}

void enqueue(PriorityQueue *q, Item x, int p) {
	if (p < LOW_PRIORITY || p > HIGH_PRIORITY || (size(q) + 1) >= (q->size)) {
		// do not execute if priority is invalid, or if queue is full
		return;
	}
	insertNode(q->list, p, x); // insert the node in the list
}

void dequeue(PriorityQueue *q) {
	if (size(q) > 0) { // only execute if there's at least one element in the queue
		Node *high = getNode(q->list, HIGH_PRIORITY); // get the node with high priority
		Node *mid = getNode(q->list, MID_PRIORITY); // get the node with mid priority
		Node *low = getNode(q->list, LOW_PRIORITY); // get the node with low priority
		// choose which node to dequeue, starting from highest priority
		Node *toDequeue = (high == NULL ? (mid == NULL ? (low == NULL ? NULL : low) : mid) : high);
		int priorityToRemove = toDequeue->priority; // get the priority to remove
		int i;
		for (i = 0; i < getSize(q->list); i ++) { // loop through elements
			Node *temp = getNodeAtIndex(q->list, i); // get a reference
			if (temp->priority == priorityToRemove) { // if the priority is the one we are looking for
				removeNodeAtIndex(q->list, i); // then we remove the node
				break;
			}
		}
	}
}

Node *peek(PriorityQueue *q) {
	Node *high = getNode(q->list, HIGH_PRIORITY); // reference to node with high priority
	Node *mid = getNode(q->list, MID_PRIORITY); // reference to node with mid priority
	Node *low = getNode(q->list, LOW_PRIORITY); // reference to node with low priority
	// get the first element available, starting from the highest priority
	return (high == NULL ? (mid == NULL ? (low == NULL ? NULL : low) : mid) : high);
}

bool is_empty(PriorityQueue *q) {
	// check whether the queue's size is 0
	return getSize(q->list) == 0;
}

int size(PriorityQueue *q) {
	// get the number of elements in the list
	return getSize(q->list);
}

PriorityQueue *merge(PriorityQueue *q, PriorityQueue *r) {
	int new_size = q->size + r->size; // make the new size equal to the addition of both queues
	PriorityQueue *new = create_q(new_size); // allocate memory for the new queue
	int i;
	for (i = 0; i < getSize(q->list); i ++) { // loop through nodes of the first list
		Node *node = getNodeAtIndex(q->list, i); // get the node at the current index
		enqueue(new, node->data, node->priority); // add element to new queue
	}
	for (i = 0; i < getSize(r->list); i ++) { // loop through nodes of the second list
		Node *node = getNodeAtIndex(r->list, i); // get the node at the current index
		enqueue(new, node->data, node->priority); // add element to new queue
	}
	return new;
}

void clear(PriorityQueue *q) {
	clearList(q->list); // remove all the elements from the list
}

bool store(PriorityQueue *q) {
	LinkedList *list = q->list;
	int i;
	FILE *File = fopen(q->name, "w"); // open the file with the argument 'w' to write
	int size = q->size; // get the max size from the queue
	fwrite(&size, sizeof(int), 1, File); // write the queue's size to the file
	for (i = 0; i < getSize(list); i ++) { // loop through all the nodes
		Node *node = getNodeAtIndex(list, i);
		if (!fwrite(node, sizeof(Node), 1, File) > 0) { // attempt to write node to file
			return false;
		}
	}
	fclose(File); // close the stream
	return true;
}

bool load(PriorityQueue * *q, FILE *File, char *name) {
	Node node;
	rewind(File); // set pointer to beginning of file
	int size;
	fread(&size, sizeof(int), 1, File); // read the size of the queue from the file
	*q = create_q(size); // create a queue with the size read
	strcpy((*q)->name, name); // store the name for the new queue
	while (fread(&node, sizeof(Node), 1, File) == 1) { // keep looping as long as we've found a node
		enqueue(*q, node.data, node.priority); // add the read element
	}
	fclose(File); // close the stream
	return true;
}

void output(PriorityQueue *q) {
	printf("---- PRINTING QUEUE ----\n");
	printList(q->list); // output the list
	printf("----  END OF PRINT  ----\n");
}
