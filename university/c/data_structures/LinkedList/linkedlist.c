/*
 * linkedlist.c
 *
 *  Created on: Dec 27, 2014
 *      Author: user
 */

#include "linkedlist.h"
#include <stdio.h>
#include <stdlib.h>

LinkedList *create() {
	LinkedList *list = (LinkedList *) malloc(sizeof(LinkedList)); // alloate memory for the linked list
	list->head = NULL; // set the head to null
	list->size = 0; // set the initial number of elements to be 0
	return list;
}

void insertNode(LinkedList *list, int priority, Item item) {
	insertNodeAtIndex(list, priority, item, getSize(list)); // call the higher function
}

void insertNodeAtIndex(LinkedList *list, int priority, Item item, int index) {
	if (index < 0 || (getSize(list) > 0 && index > getSize(list))) {
		// do not execute method if the index is invalid
		return;
	}
	Node *element = (Node *) malloc(sizeof(Node)); // allocate memory for the node
	element->data = item; // store data in the new node
	element->priority = priority; // store priority in the new node
	if (element == NULL) { // stop method if we failed to allocate enough memory
		return;
	}
	Node *head = list->head; // get a reference to the head of the list
	if (head == NULL) { // make the new node the head
		element->next = NULL; // make the node point to nothing
		list->head = element; // make the element the head
	} else {
		if (index > 0) {
			int i;
			for (i = 0; i < (index-1); i ++) { // keep looping until we find the desired index
				head = head->next;
			}
			// make new node point to the element pointed by the replaced node
			Node *prevPointer = head->next;
			element->next = prevPointer;
			head->next = element;
		} else {
			element->next = head;
			list->head = element;
		}
	}
	(list->size)++; // increment the number of elements in the list
}

void removeNode(LinkedList *list) {
	removeNodeAtIndex(list, getSize(list) - 1); // call higher function
}

void removeNodeAtIndex(LinkedList *list, int index) {
	if (index < 0 || index >= getSize(list)) {
		// do not execute method if the index is an invalid value
		return;
	}
	if (index > 0) {
		Node *curr = getNodeAtIndex(list, index); // get the node which is at the current index
		Node *prev = getNodeAtIndex(list, index - 1); // get the node before the current index
		Node *after = getNodeAtIndex(list, index + 1); // get the node after the current index
		if (after == NULL) { // the node to remove is the last node in the list
			prev->next = NULL; // therefore the previous node must now point to nothing
		} else {
			prev->next = after; // the previous node must point to the node after the removed one
		}
		free(curr); // free the memory occupied by the removed node
		(list->size)--; // decrement the number of elements in the list
	} else if (index == 0) { // removing the head
		Node *after = getNodeAtIndex(list, 1); // check the node after the head
		list->head = NULL; // set the head to null
		if (after != NULL) {
			list->head = after; // make the second node the new head if it is not null
		}
		(list->size)--; // decrement the number of elements in the list
	}


}

Node *getNode(LinkedList *list, int priority) {
	if (list != NULL) { // only execute method if the list is not null
		Node *temp = list->head; // point to the head of the list
		while (temp != NULL) { // keep looping until we find the desired priority
			if (temp->priority == priority) { // found the priority, so we may return the node
				return temp;
			}
			temp = temp->next; // get the next node
		}
	}
	return NULL;
}

Node *getNodeAtIndex(LinkedList *list, int index) {
	if (index < 0 || index >= getSize(list)) {
		// return null if the index is invalid
		return NULL;
	}
	Node *curr = list->head; // point to the head of the list
	int i;
	for (i = 0; i < index; i ++) { // keep looping until the desired index
		curr = curr->next;
	}
	return curr;
}

int getSize(LinkedList *list) {
	// access the variable size from the list to get the number of elements
	return list->size;
}

void clearList(LinkedList *list) {
	while (!getSize(list) != 0) { // keep removing the first node until the size is 0
		removeNode(list);
	}
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

void printList(LinkedList *list) {
	Node *temp = list->head; // point to head of the list
	while (temp != NULL) { // keep looping as long as the node is not null
		// print the node's data
		printf("{%s, %s, %d}, ", (temp->data).name, (temp->data).surname, (temp->data).id);
		temp = temp->next; // get the next node
	}
}
