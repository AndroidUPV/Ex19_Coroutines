# Ex18_Coroutines
Lecture 05 - Development of Graphical User Interfaces (GUI)

The app displays a series of buttons that launch different coroutines.
The delay() suspend function is used to emulate long runing operations.
Information about the execution is logged (can be seen through Logcat).
- A single coroutine is launched in the scope of the ViewModel. Logcat will display "Hello" (logged by the coroutine), "World!" (logged by the main thread). The coroutine is executed in the main thread.
- A coroutine launched in the scope of the ViewModel launches several suspend functions. Logcat will display "Hello" (logged by the coroutine), "World 1" (logged by the second suspend function), "World 2" (logged by the first suspend function), "Done" (logged by the corutine). All coroutines are executed in the main thread.
- A coroutine launched in the scope of the ViewModel asynchronously launches two suspend functions that return an integer number. The coroutine awaits for their result before computing the addition. Logcat will display "Start" (logged by the coroutine), "<Int>" (logged by one of the async suspend functions), "<Int>" (logged by the other async suspend function), "The sum is <Int>" (logged by the coroutine once both results are available). All coroutines are executed in the main thread.
- A coroutine launched in the scope of the ViewModel launches different main-safe suspend functions. Each one of them is executed in a different thread (IO, CPU, main). 
