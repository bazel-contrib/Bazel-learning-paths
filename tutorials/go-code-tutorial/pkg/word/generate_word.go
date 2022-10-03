package word

import (
	"fmt"

	"github.com/tjarratt/babble"
)

func GenerateWord() string {
	fmt.Println("GenerateWord called")
	return babble.NewBabbler().Babble()
}
