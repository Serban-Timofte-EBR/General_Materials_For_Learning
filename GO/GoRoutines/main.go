package main

import (
	"fmt"
	"sync"
	"time"
)

// Example 1:
// Simpler GoRoutine

func example1() {
	// Launch using go
	go func() {
		fmt.Println("Hello from inside GoRoutine")
	}()

	// Need to wait to prevent finising the main to finish earlier
	// Leave time for the GoRoutine to print
	time.Sleep(time.Second)
	fmt.Println("End of Example 1")
}

// Example 2:
// Communication between GoRoutines using channels
func example2() {
	// Creating a channel of strings
	// A channel is a pipeline to commmunicate between GoRoutines
	messageChannel := make(chan string)

	go func() {
		messageChannel <- "Hello from inside GoRoutine (Example 2)"
	}()

	// Waiting to receive the message
	message := <-messageChannel
	fmt.Println(message)
}

// Channel with buffer
func example3() {
	// Channel with buffer of two - can store 2 messages without blocking
	bufferedChannel := make(chan string, 2)

	// Add 2 messages without blocking
	bufferedChannel <- "Hello from inside GoRoutine (Example 3)"
	bufferedChannel <- "Second hello from inside GoRoutine (Example 3)"

	// Reading the messages
	fmt.Println(<-bufferedChannel)
	fmt.Println(<-bufferedChannel)
}

// Multiple GoRoutines
func example4() {
	numberChannel := make(chan int)

	// Launch 3 GoRoutines to send numbers
	for i := 0; i < 3; i++ {
		go func(id int) {
			numberChannel <- id * id
		}(i)
	}

	// Print. There is not mandatory to see 4, 1, 0 because the thread can finish earlier
	for i := 0; i < 3; i++ {
		fmt.Println("Number (Example 4): ", <-numberChannel)
	}
}

// Example 5:
// WaitGroup - waiting multiple GoRoutines
func example5() {
	var wg sync.WaitGroup // WaitGroup helps to wait all GoRoutines

	// Launch 3 GoRoutines
	for i := 0; i < 3; i++ {
		wg.Add(1) // Add 1 to counter
		go func(id int) {
			defer wg.Done() // Decrease 1 from counter when we finish
			fmt.Println("GoRoutine %d working ...", id)
			time.Sleep(time.Duration(id) * time.Second)
			fmt.Println("GoRoutine %d done!", id)
		}(i)
	}

	wg.Wait()
	fmt.Println("End of Example 5")
}

func main() {
	// If we run only this print and example1 the second thread does not have time to run and  come back into main
	fmt.Println("\\n=== Exemple 1: Simple Goroutine ===")
	example1()

	fmt.Println("\\n=== Exemple 2: Simple Channel ===")
	example2()

	fmt.Println("\\n=== Exemple 3: Buffered Channel ===")
	example3()

	fmt.Println("\\n=== Exemple 4: Multiple GoRoutines ===")
	example4()

	fmt.Println("\\n=== Exemple 5: WaitGroup GoRoutines ===")
	example5()
}
