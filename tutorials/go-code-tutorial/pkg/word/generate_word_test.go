package word

import (
	"fmt"
	"testing"
)

func TestGenerateWord(t *testing.T) {
	result := GenerateWord()
	fmt.Println("HERE")
	fmt.Println(result)
	if result != "" {
		t.Error("got an empty string")
	}
}
