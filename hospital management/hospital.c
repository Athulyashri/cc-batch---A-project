#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct Patient {
    int id;
    char name[50];
    int age;
    char disease[50];
    char bloodGroup[10];

    struct Patient *next;
};

struct Patient *head = NULL;

// Add Patient
void addPatient() {
    struct Patient *newPatient;

    newPatient = (struct Patient *)malloc(sizeof(struct Patient));

    printf("\nEnter Patient ID: ");
    scanf("%d", &newPatient->id);

    printf("Enter Name: ");
    scanf(" %[^\n]", newPatient->name);

    printf("Enter Age: ");
    scanf("%d", &newPatient->age);

    printf("Enter Disease: ");
    scanf(" %[^\n]", newPatient->disease);

    printf("Enter Blood Group: ");
    scanf("%s", newPatient->bloodGroup);

    newPatient->next = NULL;

    if (head == NULL) {
        head = newPatient;
    } else {
        struct Patient *temp = head;

        while (temp->next != NULL) {
            temp = temp->next;
        }

        temp->next = newPatient;
    }

    printf("\nPatient added successfully!\n");
}

// Display Patients
void displayPatients() {
    struct Patient *temp = head;

    if (head == NULL) {
        printf("\nNo patients found.\n");
        return;
    }

    printf("\n===== PATIENT LIST =====\n");

    while (temp != NULL) {
        printf("\nPatient ID   : %d", temp->id);
        printf("\nName         : %s", temp->name);
        printf("\nAge          : %d", temp->age);
        printf("\nDisease      : %s", temp->disease);
        printf("\nBlood Group  : %s\n", temp->bloodGroup);

        temp = temp->next;
    }
}

int main() {
    int choice;

    while (1) {
        printf("\n\n===== HOSPITAL MANAGEMENT SYSTEM =====");
        printf("\n1. Add Patient");
        printf("\n2. Display Patients");
        printf("\n3. Exit");

        printf("\nEnter your choice: ");
        scanf("%d", &choice);

        switch (choice) {
            case 1:
                addPatient();
                break;

            case 2:
                displayPatients();
                break;

            case 3:
                printf("\nThank you!\n");
                exit(0);

            default:
                printf("\nInvalid choice!");
        }
    }

    return 0;
}