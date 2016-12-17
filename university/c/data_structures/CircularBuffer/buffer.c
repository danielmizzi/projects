/*
 * buffer.c
 *
 *  Created on: Oct 17, 2014
 *      Author: Daniel Mizzi
 */

#include "buffer.h"
#include <stdio.h>
#include <stdlib.h>

CircularBuffer *create(int length) {
	CircularBuffer *buffer = (CircularBuffer *) malloc(sizeof(CircularBuffer));
	// allocate memory to the circular buffer
	Node *node = (Node *) malloc(length * sizeof(Node));
	// allocate memory to the nodes in the buffer
	if (buffer != NULL && node != NULL) { // set data if memory was allocated
		int i;
		Node *temp = node;
		for (i = 0; i < length; i ++) { // loop through nodes
			temp->data = (struct user) {}; // initialize empty data in the nodes
			temp->free = true; // set the node as free, meaning that it does not contain actual data
			temp++; // increment pointer
		}

		buffer->head = node; // set the buffer's head to the first node
		buffer->tail = node + length - 1; // set the buffer's tail to the last allocated node
		buffer->free = node; // set the free node to the first node
		buffer->oldest = node; // set the oldest node to the first node
		buffer->maxSize = length; // set the max size of the buffer
		buffer->size = 0; // set the current number of elements in the buffer to 0
		return buffer;
	}
	return NULL;
}

void destroy(CircularBuffer *buffer) {
	Node *temp = buffer->head; // start from the first node
	do {
		free(temp); // free the memory occupied by the node
	} while (temp++ != buffer->tail); // keep looping until we reach the tail
	free(buffer); // free the memory occuped by the buffer
}

void clearBuffer(CircularBuffer *buffer) {
	int i;
	Node *temp = buffer->head; // point to the first node
	for (i = 0; i < buffer->maxSize; i ++) { // loop through all the nodes
		temp->data = (struct user) {}; // empty the data
		temp++; // increment the pointer
	}
	buffer->free = temp; // reset the free node
	buffer->oldest = temp; // reset the oldest node
}

void addNode(CircularBuffer *buffer, Item item, int priority) {
	Node *toWriteAt; // pointer to the location to write at
	if (isFull(buffer)) { // buffer is full, so we overwrite by definition of a CircularBuffer
		toWriteAt = buffer->oldest; // point to the oldest node
		if (buffer->oldest == buffer->tail) {
			buffer->oldest = buffer->head; // point to the head if the previous node was the tail
		} else {
			(buffer->oldest)++; // increment pointer if the node is not the tail
		}
	} else {
		toWriteAt = buffer->free;
		if (buffer->size < buffer->maxSize) {
			(buffer->size)+=1; // increase the number of elements if it's not full
		}
		if (isFull(buffer)) {
			buffer->free = NULL; // no available free elements
		} else {
			if (buffer->free == buffer->tail) {
				buffer->free = buffer->head; // set free element to be the head
			} else {
				(buffer->free)++; // increment the free pointer
			}
		}
	}
	toWriteAt->priority = priority; // set priority
	toWriteAt->data = item; // store the data
	toWriteAt->free = false; // set to false, as it contains valid data
	buffer->latest = toWriteAt; // insert in the buffer
}

void removeNode(CircularBuffer *buffer) {
	if (buffer-> size > 0) { // only remove if we have at least one element
		buffer->free = buffer->oldest;
		Node *oldest = buffer->oldest;
		Node node;
		node.data = (Item) {}; // make the node have empty data
		oldest->data = node.data;
		oldest->free = true; // the previous node does not contain valid data anymore
		if (buffer->oldest == buffer->tail) {
			if (buffer->size > 1) {
				buffer->oldest = buffer->head; // make the oldest node point to the head
			}
		} else {
			(buffer->oldest)++; // increment the pointer of the oldest element
		}
		(buffer->size)--; // decrease number of elements stored in the buffer
	}
}

Node *getNode(CircularBuffer *buffer, int index) {
	Node *temp = buffer->head; // point to the head of the buffer
	int i;
	for (i = 0; i < index; i ++) { // loop through elements
		temp++; // increment pointer to reach desired index
	}
	return temp;
}

void printNode(Node *node) {
	puts("-----------------------------");
	// print the node that was  added to the buffer
	printf("Name: %s\n", (node->data).name);
	printf("Surname: %s\n", (node->data).surname);
	printf("ID: %d\n", (node->data).id);
	printf("Priority: %d\n", (node->priority));
	puts("-----------------------------\n");
}

void printBuffer(CircularBuffer *buffer) {
	Node *temp = buffer->head; // make a pointer to the head
	int totalPrinted = 0; // total amount of nodes printed
	do {
		if (temp->free) { // skip rest of loop if the node does not have valid data (to not clutter console)
			continue;
		}
		printf("{%s, %s, %d, p:%d}, ", ((temp->data).name), ((temp->data).surname), ((temp->data).id), temp->priority);
		totalPrinted++;
		// increase number of nodes printed
	} while (temp++ != buffer->tail); // keep looping until we reach buffer's tail
	if (totalPrinted > 0) {
		// skip line only if we printed nodes, for better readability
		printf("\n");
	}
}

bool isFull(CircularBuffer *buffer) {
	// full if the number of the elements is equal to the maximum size of the buffer
	return buffer->size == buffer->maxSize;
}

bool isEmpty(CircularBuffer *buffer) {
	// empty if the number of elements is 0
	return buffer->size == 0;
}

int getSize(CircularBuffer *buffer) {
	// access the variable size to get the number of elements
	return buffer->size;
}
