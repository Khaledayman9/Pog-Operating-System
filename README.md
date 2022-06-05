# Pog-Operating-System
Pog OS is a simulation of real operating systems implemented in Java for executing a bunch of processes using Round Robin scheduling algorithm, mutexes to ensure Mutual exclusion. Also swapping processes when executing from memory into disk and vice versa.

The main memory is a static memory that consists only of 40 words and is divided in to two blocks, each block contains 20 words. Thus when a process is created or is about to execute, it will be inserted in one of these 2 blocks.

The process contains a PCB, instructions and the variables assigned from the execution phase. Thus the size of a process is equivalent to the PCB attributes which are 5(procces id, proccess state, PC, first location in memory, last location in memory) and number of variables assigned (in our OS each process takes 3 variables) and the number of instructions for each program.

Our swapping algorithm work as follow, If a process is created or needs to execute and its not in the memory yet:
we have three cases: 
“*” 1st case -> The system will check if the first block in the memory is empty, if its empty, we will insert the process in the first block.
“*”2nd case -> The system checked and found out the first block is occupied, then the system will check in the second block, if its empty, we will insert the process in the second block.
“*”3rd case -> If both blocks are occupied, then the system will swap the process in the second block with the process that is newly created or needs to execute.

We have 3 mutexes in our program (UserInputMutex,UserOutputMutex,FileMutex)

Here is an example when running the Simulation, having the 3 Programs (Program_1.txt,Program_2.txt,Program_3.txt) that will be turned into 3 Processes when they are created and inserted into the main memory and ready queue:

![s1](https://user-images.githubusercontent.com/105018459/171850792-79847a43-c307-4ff9-9e78-1f4bb89d80de.PNG)



And here is an example of blocked queues along side with the main memory:

![s2](https://user-images.githubusercontent.com/105018459/171852185-c07f5f82-3657-4ed6-baa1-54df79a64e9e.PNG)




If a process is bigger in size than the size of one block and less than the size of the memory, then it will occupy all the two blocks of the memory as shown here:
  
![s3](https://user-images.githubusercontent.com/105018459/171852555-d15ae973-47cf-4695-a34a-9d04bfa2f470.PNG)



And if a process is bigger in size than the whole memory(Thus takes more than 40 words) then it won't be inserted in the main memory and it won't execute.

# Technologies
Project is created with:

Java SE-15
Eclipse IDE


# Set up

To run this project, just import the project as an existing project in Eclipse
