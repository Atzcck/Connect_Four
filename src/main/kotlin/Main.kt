var firstWins = false
var secondWins = false
var check = true
var incorrect = false
var draw = false
var turn = 0
fun main() {
    //TAKING INPUTS
    var firstScore = 0
    var secondScore = 0
    var gameCount = ""
    var a = 0
    var b = 0
    println("Connect Four")
    println("First player's name:")
    val firstPlayer = readln()
    println("Second player's name:")
    val secondPlayer = readln()
    println("Set the board dimensions (Rows x Columns)")
    println("Press Enter for default (6 x 7)")
    while (true) {
        try {
            var boardDimension = readln().lowercase()

            var boardDimensionList = boardDimension.split("x")

            if (boardDimension.isEmpty()) {
                gameCount = gameCount()
                println("$firstPlayer VS $secondPlayer")
                println("6 X 7 board")
                if (gameCount == "1") println("Single Game") else println("Total $gameCount games")
                a = 6
                b = 7
                break
            }

            if (boardDimensionList[0] == "" || boardDimensionList[1] == "") {
                println(
                    "Invalid input\n" + "Set the board dimensions (Rows x Columns)\n" + "Press Enter for default (6 x 7)"
                )
            } else {
                a = boardDimensionList[0].trim().toInt()
                b = boardDimensionList[1].trim().toInt()
                val regex = Regex("\\s*$a?\\s*x\\s*$b?\\s*")
                val regex2 = Regex("\\s*$a?\\s*X\\s*$b?\\s*")
                if ((boardDimension.matches(regex) || boardDimension.matches(regex2)) && (a !in 5..9 || b !in 5..9)) {
                    if (a !in 5..9 && b !in 5..9) {
                        println("Board rows should be from 5 to 9")
                        println("Board columns should be from 5 to 9")
                    } else if (a !in 5..9) {
                        println("Board rows should be from 5 to 9")
                    } else {
                        println("Board columns should be from 5 to 9")
                    }
                    println("Set the board dimensions (Rows x Columns)")
                    println("Press Enter for default (6 x 7)")

                } else if (boardDimension.matches(regex) || boardDimension.matches(regex2)) {
                    gameCount = gameCount()
                    println("$firstPlayer VS $secondPlayer")
                    println("$a X $b board")
                    if (gameCount == "1") println("Single Game") else println("Total $gameCount games")
                    break
                } else {
                    println(
                        "Invalid input\n" + "Set the board dimensions (Rows x Columns)\n" + "Press Enter for default (6 x 7)"
                    )
                }
            }

        } catch (E: Exception) {
            println(
                "Invalid input\n" + "Set the board dimensions (Rows x Columns)\n" + "Press Enter for default (6 x 7)"
            )
        }
    }
    if (gameCount.toInt() > 1) {
        for (i in 1..gameCount.toInt()) {
            if (i % 2 == 0) turn = 1
            if (i % 2 == 1) turn = 0
            println("Game #$i")
            game(a, b, firstPlayer, secondPlayer, boardDraw(a, b))
            if (firstWins) println("Player $firstPlayer won")
            else if (secondWins) println("Player $secondPlayer won")
            else if (draw) println("It is a draw")
            if (firstWins) firstScore += 2
            if (secondWins) secondScore += 2
            if (draw) {
                firstScore += 1
                secondScore += 1
            }
            println("Score")
            println("$firstPlayer: $firstScore $secondPlayer: $secondScore")
        }
    } else {
        game(a, b, firstPlayer, secondPlayer, boardDraw(a, b))
        if (firstWins) println("Player $firstPlayer won")
        else if (secondWins) println("Player $secondPlayer won")
        else if (draw) println("It is a draw")
    }
    println("Game over!")
}

fun boardDraw(coloumb: Int, row: Int): MutableList<MutableList<String>> {
//    val coloumbList = MutableList(coloumb) { " " }
    val boardList: MutableList<MutableList<String>> = MutableList(row) { MutableList(coloumb) { " " } }
//    for (i in 0..row) boardList.add(coloumbList)

    for (i in 1..row) {
        print(" $i")
    }
    for (i in 0 until coloumb) {
        print("\n")
        for (x in 0 until row) {
            print("|${boardList[x][i]}")
        }
        print("|")
    }
    print("\n")
    repeat(2 * row + 1) {
        print("=")
    }
    print("\n")
    return boardList
}

fun game(coloumb: Int, row: Int, P1: String, P2: String, board: MutableList<MutableList<String>>) {
    var regex = Regex("\\d+")
    var point = ""
    check = true
    while (check) {
        incorrect = false
        if (turn % 2 == 0) {
            println("$P1's turn:")
            point = readln()
            if (point == "end") {
                break
            } else if (!point.matches(regex)) {
                println("Incorrect column number")
                turn += 1
                incorrect = true
            } else if (point.matches(regex) && point.toInt() !in 1..row) {
                println("The column number is out of range (1 - $row)")
                turn += 1
                incorrect = true
            } else if (point.matches(regex) && point.toInt() in 1..row) {
                if (board[point.toInt() - 1][0] != " ") {
                    println("Column ${point.toInt()} is full")
                    incorrect = true
                    turn += 1
                } else {
                    for (i in 0 until coloumb)
                        if (board[point.toInt() - 1][board[0].lastIndex - i] == " ") {
                            board[point.toInt() - 1][board[0].lastIndex - i] = "o"
                            break
                        }
                }
            }
            if (!incorrect) {
                printBoard(board)
                check = winCheck(board)
            }
        } else if (turn % 2 == 1) {
            println("$P2's turn:")
            point = readln()
            if (point == "end") {
                break
            } else if (!point.matches(regex)) {
                println("Incorrect column number")
                turn += 1
                incorrect = true
            } else if (point.matches(regex) && point.toInt() !in 1..row) {
                println("The column number is out of range (1 - $row)")
                turn += 1
                incorrect = true
            } else if (point.matches(regex) && point.toInt() in 1..row) {
                if (board[point.toInt() - 1][0] != " ") {
                    println("Column ${point.toInt()} is full")
                    incorrect = true
                    turn += 1
                } else {
                    for (i in 0 until coloumb)
                        if (board[point.toInt() - 1][board[0].lastIndex - i] == " ") {
                            board[point.toInt() - 1][board[0].lastIndex - i] = "*"
                            break
                        }
                }
            }
            if (!incorrect) {
                printBoard(board)
                check = winCheck(board)
            }
        }
        turn += 1
    }
}

fun printBoard(boardList: MutableList<MutableList<String>>) {
    for (i in 1..boardList.size) {
        print(" $i")
    }
    for (i in 0 until boardList[0].size) {
        print("\n")
        for (x in 0 until boardList.size) {
            print("|${boardList[x][i]}")
        }
        print("|")
    }
    print("\n")
    repeat(2 * boardList.size + 1) {
        print("=")
    }
    print("\n")
}

fun winCheck(board: MutableList<MutableList<String>>): Boolean {
    var count = 0
    var regex2 = Regex("[*, o]+")
    firstWins = false
    secondWins = false
    draw = false
    // VERTICAL "*"
    for (x in 0..board.size - 1) {
        for (y in 0..board[x].size - 4) {
            if (board[x][y] == "*" && board[x][y + 1] == "*" && board[x][y + 2] == "*" && board[x][y + 3] == "*") {
                secondWins = true
                return false
            }
        }
    }
    //HORIZONTAL "*"
    for (x in 0..board[0].size - 1) {
        for (y in 0..board.size - 4) {
            if (board[y][x] == "*" && board[y + 1][x] == "*" && board[y + 2][x] == "*" && board[y + 3][x] == "*") {
                secondWins = true
                return false
            }
        }
    }
    //CROSS "*"
    for (x in 0..board.size - 4) {
        for (y in 0..board[0].size - 4) {
            if (board[x][y] == "*" && board[x + 1][y + 1] == "*" && board[x + 2][y + 2] == "*" && board[x + 3][y + 3] == "*") {
                secondWins = true
                return false
            }
        }
    }
    //BACK CROSS "*"
    for (x in 0..board.size - 4) {
        for (y in 0..board[0].size - 4) {
            if (board[x + 3][y] == "*" && board[x + 2][y + 1] == "*" && board[x + 1][y + 2] == "*" && board[x][y + 3] == "*") {
                secondWins = true
                return false
            }
        }
    }
    // VERTICAL "o"
    for (x in 0..board.size - 1) {
        for (y in 0..board[x].size - 4) {
            if (board[x][y] == "o" && board[x][y + 1] == "o" && board[x][y + 2] == "o" && board[x][y + 3] == "o") {
                firstWins = true
                return false
            }
        }
    }
    //HORIZONTAL "o"
    for (x in 0..board[0].size - 1) {
        for (y in 0..board.size - 4) {
            if (board[y][x] == "o" && board[y + 1][x] == "o" && board[y + 2][x] == "o" && board[y + 3][x] == "o") {
                firstWins = true
                return false
            }
        }
    }
    //CROSS "o"
    for (x in 0..board.size - 4) {
        for (y in 0..board[0].size - 4) {
            if (board[x][y] == "o" && board[x + 1][y + 1] == "o" && board[x + 2][y + 2] == "o" && board[x + 3][y + 3] == "o") {
                firstWins = true
                return false
            }
        }
    }
    //BACK CROSS "o"
    for (x in 0..board.size - 4) {
        for (y in 0..board[0].size - 4) {
            if (board[x + 3][y] == "o" && board[x + 2][y + 1] == "o" && board[x + 1][y + 2] == "o" && board[x][y + 3] == "o") {
                firstWins = true
                return false
            }
        }
    }
    //DRAW
    for (x in 0..board.size - 1) {
        if (board[x][0] != " ") {
            count += 1
        }
        if (count == board.size) {
            draw = true
            return false
        }
    }
    return true
}

fun gameCount(): String {
    val regex3 = Regex("\\d+")
    while (true) {
        println(
            "Do you want to play single or multiple games?\n" +
                    "For a single game, input 1 or press Enter\n" +
                    "Input a number of games:"
        )
        var playCount = readln()
        if (playCount.matches(regex3) && (playCount == "" || playCount == "1") || playCount.isEmpty()) {
            playCount = "1"
            return playCount
        } else if (playCount.matches(regex3) && playCount != "0") {
            return playCount
        } else if (playCount == "0") {
            println("Invalid input")
        } else {
            println("Invalid input")
        }
    }
}

