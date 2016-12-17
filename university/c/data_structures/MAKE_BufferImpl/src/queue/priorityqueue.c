/*
 * priorityqueue.c
 *
 *  Created on: Dec 30, 2014
 *      Author: Daniel Mizzi
 */

#include <stdio.h>
#include "priorityqueue.h"

PriorityQueue *create_q(int max_size) {
	PriorityQueue *queue = (PriorityQueue *) malloc(sizeof(PriorityQueue));
	// allocate memory for the priority queue
	if (queue != NULL) {
		CircularBuffer *low = create(max_size); // create a buffer to hold low priority elements
		CircularBuffer *mid = create(max_size); // create a buffer to hold mid priority elements
		CircularBuffer *high = create(max_size); // create a buffer to hold high priority elements
		if (low != NULL && mid != NULL && high != NULL) { // succesffully allocated memory
			// assign the buffers to the queue
			queue->buffer[0] = low;
			queue->buffer[1] = mid;
			queue->buffer[2] = high;
			queue->size = max_size; // set the queue's max size
			return queue;
		}
	}
	return NULL;
}

void enqueue(PriorityQueue *q, Item x, int p) {
	if (p < 0 || p > HIGH_PRIORITY) { // only accept valid priority
		return;
	}
	addNode((q->buffer)[p], x, p); // add the element to the buffer
}

void dequeue(PriorityQueue *q) {
	int i;
	for (i = 2; i >= 0; i --) { // search through the buffers, starting from the highest priority
		if (getSize(q->buffer[i]) > 0) { // buffer has an element so we can dequeue
			removeNode((q->buffer[i])); // remove the element
			break; // break out of loop since we have removed an element
		}
	}
}

Node *peek(PriorityQueue *q) {
	int i;
	for (i = 2; i >= 0; i --) { // search through the buffers, starting from the highest priority
		if (getSize(q->buffer[i]) > 0) { // buffer has an element so we can return element
			return (q->buffer[i])->oldest;
		}
	}
	return NULL;
}

bool is_empty(PriorityQueue *q) {
	int i;
	for (i = 0; i < 3; i ++) { // loop through buffers
		if (!isEmpty((q->buffer)[i])) { // return false if at least one buffer is not empty
			return false;
		}
	}
	return true;
}

int size(PriorityQueue *q) {
	int size = 0;
	int i;
	for (i = 0; i < 3; i ++) { // loop through buffers
		size += getSize(q->buffer[i]); // add each buffer's size to the total
	}
	return size;
}

PriorityQueue *merge(PriorityQueue *q, PriorityQueue *r) {
	int new_size = size(q) + size(r); // make the new size equal to the addition of both queues
	PriorityQueue *new = create_q(new_size); // allocate memory for the new queue
	int i;
	for (i = 0; i < 3; i ++) { // loop through each priority
		int o;
		int first_size = q->buffer[i]->maxSize; // the size of the first queue's buffer
		int second_size = r->buffer[i]->maxSize; // the size of the second queue's buffer
		for (o = 0; o < first_size; o ++) { // loop through first buffer's elements
			Node *node = getNode(q->buffer[i], o);
			if (!node->free) { // check if node has valid data
				addNode((new->buffer[i]), node->data, i); // add element to new queue
			}
		}
		for (o = 0; o < second_size; o ++) { // loop through second buffer's elements
			Node *node = getNode(r->buffer[i], o);
			if (!node->free) { // check if node has valid data
				addNode((new->buffer[i]), node->data, i); // add element to new queue
			}
		}
	}
	return new;
}

void clear(PriorityQueue *q) {
	clearBuffer(q->buffer[0]); // clear lowest priority buffer
	clearBuffer(q->buffer[1]); // clear mid priority buffer
	clearBuffer(q->buffer[2]); // clear highest priority buffer
}

bool store(PriorityQueue *q) {
	int i;
	int o;
	FILE *File = fopen(q->name, "w"); // open the file with the argument 'w' to write
	int size = q->size; // get the max size from the queue
	fwrite(&size, sizeof(int), 1, File); // write the queue's size to the file
	for (i = 0; i < 3; i ++) { // loop through all the buffers
		CircularBuffer *curr = q->buffer[i]; // make a reference to the buffer
		for (o = 0; o < size; o ++) { // loop through elements of the buffer
			Node *node = getNode(curr, o); // make a reference to the node at the curent index
			if (node->free) { // do not write if the node does not have valid data
				continue;
			}
			if (!fwrite(node, sizeof(Node), 1, File)) { // attempt to write the node to the file
				return false;
			}
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
	printBuffer(q->buffer[0]); // output the lowest priority buffer
	printBuffer(q->buffer[1]); // output the mid priority buffer
	printBuffer(q->buffer[2]); // output the highest priority buffer
	printf("----  END OF PRINT  ----\n");
}
