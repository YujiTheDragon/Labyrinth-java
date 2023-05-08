import java.util.Random;
import java.util.Scanner;

public class LabyrinthGeneration {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_YELLOW = "\033[0;33m";
	public static int Width, Height;
	public static boolean Placement = false;
	public static char[][] LabCords;
	public static int PlayerX, PlayerY;
	public static int returnPointCounter;
	public static int foundReturnPoint1;
	public static int foundReturnPoint2;

	static void Init(int sizeX, int sizeY) {
		LabCords = new char[sizeX][sizeY];
		Random rd = new Random();

		int ExitIntLine = rd.nextInt(sizeX), ExitIntCol = rd.nextInt(sizeY), EntranceIntLine = rd.nextInt(sizeX),
				EntranceIntCol = rd.nextInt(sizeY);

		System.out.println("\nLABYRINTH!\n");
		for (int i = 0; i < sizeX; i = i + 1) {

			for (int j = 0; j < sizeY; j = j + 1) {
				if (ExitIntLine == i && ExitIntCol == j && Placement == false) {

					LabCords[i][j] = 'X';

					System.out.print(ANSI_GREEN + "X" + ANSI_RESET);
				} else if (EntranceIntLine == i && EntranceIntCol == j && Placement == false) {

					LabCords[i][j] = 'Y';
					PlayerX = i;
					PlayerY = j;
					System.out.print(ANSI_RED + "Y" + ANSI_RESET);
				} else if (i == 0 || i == sizeX - 1 || j == 0 || j == sizeY - 1 || rd.nextBoolean()) {
					if (j == 0) {
						if (i >= 10)
							System.out.print(i + " ");
						else
							System.out.print(i + "  ");
					}
					LabCords[i][j] = '#';
					System.out.print("#");

				}

				else {
					LabCords[i][j] = ' ';
					System.out.print(" ");
				}

			}

			System.out.println();
		}
		/*
		 * TODO FIX OUT OF BOUND EXCEPTION WHEN ENTERING A HIGH OR NEGATIVE VALUE
		 */
		if (Placement == true) {
			Scanner Input = new Scanner(System.in);
			int line = 0, line1 = 0, column = 0, column1 = 0;
			char tempchar;
			boolean Done = false;
			do {
				System.out.print("Enter line for the exit:");
				line = Input.nextInt();
				System.out.print("Enter column for the exit:");
				column = Input.nextInt();
				tempchar = LabCords[line][column];
				LabCords[line][column] = 'X';
				System.out.print("Enter line for the player:");
				line1 = Input.nextInt();
				System.out.print("Enter column for the player:");
				column1 = Input.nextInt();
				if (LabCords[line1][column1] == 'X') {
					System.out.println("PLAYER CANNOT BE PLACED ON TOP OF THE EXIT:");
					LabCords[line][column] = tempchar;
				}

				else {
					Done = true;
					LabCords[line1][column1] = 'Y';
					PlayerX = line1;
					PlayerY = column1;
				}

			} while (!Done);
			PrintLabyrinth();
		}

		for (

				int i = 0; i < sizeX; i = i + 1) {

			for (int j = 0; j < sizeY; j = j + 1) {
				if (LabCords[i][j] == 'X')
					System.out.println("X= " + i + "\n" + "Y= " + j);
				if (LabCords[i][j] == 'Y')
					System.out.println("X= " + i + "\n" + "Y= " + j);

			}
		}

		System.out.print(ANSI_RESET);

	}

	static void PrintLabyrinth() {
		for (int i = 0; i < Width; i++) {
			for (int j = 0; j < Height; j++) {
				if (j == 0) {
					if (i >= 10)
						System.out.print(i + " ");
					else
						System.out.print(i + "  ");
				}
				if (LabCords[i][j] == 'X')
					System.out.print(ANSI_GREEN + LabCords[i][j] + ANSI_RESET);
				else if (LabCords[i][j] == 'Y')
					System.out.print(ANSI_RED + LabCords[i][j] + ANSI_RESET);
				else
					System.out.print(LabCords[i][j]);

			}
			System.out.println();
		}

	}

	public static void clearScreen() {
		System.out.print("\u001B[H\u001B[2J");
		System.out.flush();
	}

	public static void printSearch(char[][] VisitedLabCords) {
		for (int i = 0; i < Width; i++) {
			for (int j = 0; j < Height; j++) {
				if (j == 0) {
					if (i >= 10)
						System.out.print(i + " ");
					else
						System.out.print(i + "  ");
				}
				if (VisitedLabCords[i][j] == 'X')
					System.out.print(ANSI_GREEN + VisitedLabCords[i][j] + ANSI_RESET);
				else if (VisitedLabCords[i][j] == 'Y')
					System.out.print(ANSI_RED + VisitedLabCords[i][j] + ANSI_RESET);
				else if (VisitedLabCords[i][j] == '0')
					System.out.print(ANSI_YELLOW + VisitedLabCords[i][j] + ANSI_RESET);
				else
					System.out.print(VisitedLabCords[i][j]);

			}
			System.out.println();
		}
	}

	public static int LookAround(char[][] VisitedLabCords, int StartX, int StartY) {

		if (StartX - 1 > 0 && VisitedLabCords[StartX - 1][StartY] == ' ')
			return 1;
		else if (StartY + 1 < Height && VisitedLabCords[StartX][StartY + 1] == ' ')
			return 2;
		else if (StartX + 1 < Width && VisitedLabCords[StartX + 1][StartY] == ' ')
			return 3;
		else if (StartY - 1 > 0 && VisitedLabCords[StartX][StartY - 1] == ' ')
			return 4;

		return 0;
	}

	public static boolean LookAroundExit(char[][] VisitedLabCords, int StartX, int StartY) {

		if (StartX - 1 > 0 && VisitedLabCords[StartX - 1][StartY] == 'X')
			return true;
		else if (StartY + 1 < Height && VisitedLabCords[StartX][StartY + 1] == 'X')
			return true;
		else if (StartX + 1 < Width && VisitedLabCords[StartX + 1][StartY] == 'X')
			return true;
		else if (StartY - 1 > 0 && VisitedLabCords[StartX][StartY - 1] == 'X')
			return true;

		return false;
	}

	public static int returnPointsCount(char[][] VisitedLabCords, int StartX, int StartY) {
		returnPointCounter = 0;
		if (StartX + 1 < Width && VisitedLabCords[StartX + 1][StartY] == ' ')
			returnPointCounter++;
		if (StartX - 1 > 0 && VisitedLabCords[StartX - 1][StartY] == ' ')
			returnPointCounter++;
		if (StartY + 1 < Height && VisitedLabCords[StartX][StartY + 1] == ' ')
			returnPointCounter++;
		if (StartY - 1 > 0 && VisitedLabCords[StartX][StartY - 1] == ' ')
			returnPointCounter++;
		return returnPointCounter;
	}

	public static boolean returnPointsCheck(int Width, int Height, int[][] returnPoints) {
		for (int i = 0; i < Width; i++) {
			for (int j = 0; j < Height; j++) {
				if (returnPoints[i][j] == 1 || returnPoints[i][j] == 2 || returnPoints[i][j] == 3) {
					foundReturnPoint1 = i;
					foundReturnPoint2 = j;
					return true;
				}
			}
		}
		return false;
	}

	public static void SearchAlg() {
		char[][] VisitedLabCords = new char[Width][Height];
		int StartX = 0;
		int StartY = 0;
		for (int i = 0; i < Width; i++) {
			for (int j = 0; j < Height; j++) {
				VisitedLabCords[i][j] = LabCords[i][j];
				if (LabCords[i][j] == 'Y') {
					StartX = i;
					StartY = j;
					System.out.println("FOUND Y");
				}
			}
		}
		printSearch(VisitedLabCords);
		boolean solved = false;
		boolean notfound = false;
		int[][] returnPoints = new int[Width][Height];
		while (solved == false) {
			System.out.println("LOOP");
			if (LookAroundExit(VisitedLabCords, StartX, StartY) == true) {
				solved = true;
				notfound = false;
				break;
			}
			if (LookAround(VisitedLabCords, StartX, StartY) == 0
					&& returnPointsCheck(Width, Height, returnPoints) == false && solved == false) {
				solved = true;
				notfound = true;
			}
			if (LookAround(VisitedLabCords, StartX, StartY) == 0) {
				VisitedLabCords[StartX][StartY] = '0';
				StartX = foundReturnPoint1;
				StartY = foundReturnPoint2;
				VisitedLabCords[StartX][StartY] = 'Y';
				// returnPoints[StartX][StartY] = returnPoints[StartX][StartY] - 1;

			}
			returnPoints[StartX][StartY] = returnPointsCount(VisitedLabCords, StartX, StartY);
			switch (LookAround(VisitedLabCords, StartX, StartY)) {
			case 1:
				System.out.println("up");
				VisitedLabCords[StartX - 1][StartY] = 'Y';
				VisitedLabCords[StartX][StartY] = (char) returnPoints[StartX][StartY];
				StartX = StartX - 1;
				break;
			case 3:
				System.out.println("down");
				VisitedLabCords[StartX + 1][StartY] = 'Y';
				VisitedLabCords[StartX][StartY] = (char) returnPoints[StartX][StartY];
				StartX = StartX + 1;
				break;
			case 2:
				System.out.println("right");
				VisitedLabCords[StartX][StartY + 1] = 'Y';
				VisitedLabCords[StartX][StartY] = (char) returnPoints[StartX][StartY];
				StartY = StartY + 1;
				break;
			case 4:
				System.out.println("left");
				VisitedLabCords[StartX][StartY - 1] = 'Y';
				VisitedLabCords[StartX][StartY] = (char) returnPoints[StartX][StartY];
				StartY = StartY - 1;
				break;
			}
			printSearch(VisitedLabCords);
		}

		/* CHECKS AFTER LOOP */
		if (notfound == true) {
			System.out.println("Exit not found!");
			solved = false;
		}

		else if (solved == true)
			System.out.println("Found the exit!");

	}

	static void Menu() {
		int answer = 0;
		Scanner Input = new Scanner(System.in);
		while (answer != 1 && answer != 2 && answer != 3) {
			clearScreen();
			System.out.println("WELCOME TO THE LABYRINTH!");
			System.out.println("1.Generate a labyrinth randomly by declared size");
			System.out.println("2.Generate a labyrinth and choose a start and end point");
			System.out.println("3.Exit");
			System.out.print("Your choice:");
			answer = Input.nextInt();
			switch (answer) {
			case 1:
			case 2:
				if (answer == 1)
					Placement = false;
				else
					Placement = true;
				do {
					System.out.println("Enter a number between 5 and 100");
					System.out.print("Enter X: ");

					Width = Input.nextInt();

					System.out.print("Enter Y: ");
					Height = Input.nextInt();
				} while (Width > 100 || Width < 5 || Height > 100 || Height < 5);

				break;
			case 3:
				System.exit(0);
				break;
			}

		}
	}

	public static void PlayerController() {
		int Key;
		boolean ExitReached = false;
		char tempchar = ' ';
		Scanner Input = new Scanner(System.in);
		while (!ExitReached) {
			PrintLabyrinth();
			System.out.println("INSTRUCTIONS:\n To go up press 1" + "\n To go right press 2" + "\n To go down press 3"
					+ "\n To go left press 4" + "\n To try the Search Algorithm press 5");
			Key = Input.nextInt();
			switch (Key) {
			case 1:
				if (LabCords[PlayerX - 1][PlayerY] == '#')
					break;
				else if (LabCords[PlayerX - 1][PlayerY] == 'X') {
					ExitReached = true;
					System.out.println("NICE YOU FOUND THE EXIT!");
					break;
				} else {
					LabCords[PlayerX][PlayerY] = tempchar;
					tempchar = LabCords[PlayerX][PlayerY];
					LabCords[PlayerX - 1][PlayerY] = 'Y';

					PlayerX -= 1;

					break;
				}

			case 2:
				if (LabCords[PlayerX][PlayerY + 1] == '#')
					break;
				else if (LabCords[PlayerX][PlayerY + 1] == 'X') {
					ExitReached = true;
					System.out.println("NICE YOU FOUND THE EXIT!");
					break;
				} else {
					LabCords[PlayerX][PlayerY] = tempchar;
					tempchar = LabCords[PlayerX][PlayerY];
					LabCords[PlayerX][PlayerY + 1] = 'Y';

					PlayerY += 1;

					break;
				}

			case 3:
				if (LabCords[PlayerX + 1][PlayerY] == '#')
					break;
				else if (LabCords[PlayerX + 1][PlayerY] == 'X') {
					ExitReached = true;
					System.out.println("NICE YOU FOUND THE EXIT!");
					break;
				} else {
					LabCords[PlayerX][PlayerY] = tempchar;
					tempchar = LabCords[PlayerX][PlayerY];
					LabCords[PlayerX + 1][PlayerY] = 'Y';

					PlayerX += 1;

					break;
				}
			case 4:
				if (LabCords[PlayerX][PlayerY - 1] == '#')
					break;
				else if (LabCords[PlayerX][PlayerY - 1] == 'X') {
					ExitReached = true;
					System.out.println("NICE YOU FOUND THE EXIT!");
					break;
				} else {
					LabCords[PlayerX][PlayerY] = tempchar;
					tempchar = LabCords[PlayerX][PlayerY];
					LabCords[PlayerX][PlayerY - 1] = 'Y';

					PlayerY -= 1;

					break;
				}
			case 5:
				SearchAlg();
				break;

			}
		}
	}

	public static void main(String[] args) {

		Menu();
		Init(Width, Height);
		PlayerController();
	}
}
