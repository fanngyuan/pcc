package main

import (
	"encoding/csv"
	"fmt"
	"log"
	"os"
	"strconv"

	"time"

	rng "github.com/leesper/go_rng"
)

var crng = rng.NewGaussianGenerator(time.Now().UnixNano())

func main() {
	file, err := os.Create("user.csv")
	checkError("Cannot create file", err)

	writer := csv.NewWriter(file)
	err = writer.Write([]string{"user_id", "user_name"})
	checkError("Cannot write to file", err)

	followFile, err := os.Create("follow.csv")
	checkError("Cannot create file", err)

	followWriter := csv.NewWriter(followFile)
	err = followWriter.Write([]string{"from_uid", "to_uid"})
	checkError("Cannot write to file", err)

	for i := 1; i < 10000001; i++ {
		err = writer.Write([]string{strconv.Itoa(i), fmt.Sprint("user", i)})
		writeUserRelationShip(i, followWriter)
		checkError("Cannot write to file", err)
	}

	defer func() {
		writer.Flush()
		file.Close()

		followWriter.Flush()
		followFile.Close()
	}()
}

func writeUserRelationShip(userId int, followWriter *csv.Writer) {
	followNum := int(crng.Gaussian(150, 140))
	if followNum < 0 || followNum > 1000 {
		followNum = int(crng.Gaussian(150, 100))
	}
	if followNum < 0 {
		return
	}
	if userId+followNum > 10000001 {
		for i := userId - 1; i > userId-followNum-1; i-- {
			err := followWriter.Write([]string{strconv.Itoa(userId), strconv.Itoa(i)})
			checkError("Cannot write to file", err)
		}
	} else {
		for i := userId + 1; i < userId+followNum+1; i++ {
			err := followWriter.Write([]string{strconv.Itoa(userId), strconv.Itoa(i)})
			checkError("Cannot write to file", err)
		}
	}
}

func checkError(message string, err error) {
	if err != nil {
		log.Fatal(message, err)
	}
}
