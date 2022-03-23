### Amdahl's law describes the theoretical limit at best a program can achieve by using additional computing resources: 

# S(n) = 1 / (1 - P) + P/n

S(n) is the speedup achieved by using n cores or threads.
P is the fraction of the program that is parallelizable.
(1-P) is the fraction of the program that must be executed serially.
