package myself;

public class Ghost {
	public// static int ghosts=10;
	static int nofghosts;// =ghosts;
	final static int maxghosts = 20;
	static int[] dx, dy;
	static int[] ghostx, ghosty, ghostdx, ghostdy, speed;

	public Ghost() {
		ghostx = new int[maxghosts];
		ghostdx = new int[maxghosts];
		ghosty = new int[maxghosts];
		ghostdy = new int[maxghosts];
		speed = new int[maxghosts];
		dx = new int[4];
		dy = new int[4];
	}

	public static void move() {
		short i;
		int pos;
		int count;
		int blocksize = Map.blocksize;
		for (i = 0; i < Ghost.nofghosts; i++) {
			// Kiem tra su di chuyen cua ghost
			if (Ghost.ghostx[i] % blocksize == 0
					&& Ghost.ghosty[i] % blocksize == 0) {
				// Xac dinh vi tri cua ghost
				pos = Ghost.ghostx[i] / blocksize + Map.cols
						* (int) (Ghost.ghosty[i] / blocksize);
				count = 0;

				if (Ghost.ghostx[i] < Pacman.x && Ghost.ghosty[i] < Pacman.y) // phai
																				// duoi
				{
					count = 0;
					if ((Graphic.currentMap[pos] & 4) == 0
							&& Ghost.ghostdx[i] != -1) {
						Ghost.dx[count] = 1;
						Ghost.dy[count] = 0;
						count++;
					}
					if ((Graphic.currentMap[pos] & 8) == 0
							&& Ghost.ghostdy[i] != -1) {
						Ghost.dx[count] = 0;
						Ghost.dy[count] = 1;
						count++;
					}
					if ((Graphic.currentMap[pos] & 1) == 0
							&& Ghost.ghostdx[i] != 1) {
						Ghost.dx[count] = -1;
						Ghost.dy[count] = 0;
						count++;
					}
					if ((Graphic.currentMap[pos] & 2) == 0
							&& Ghost.ghostdy[i] != 1) {
						Ghost.dx[count] = 0;
						Ghost.dy[count] = -1;
						count++;
					}
					if (count < 3) {
						Ghost.ghostdx[i] = Ghost.dx[0];
						Ghost.ghostdy[i] = Ghost.dy[0];
					} else {
						count = (int) (Math.random() * count);
						if (count > 3)
							count = 3;

						Ghost.ghostdx[i] = Ghost.dx[count];
						Ghost.ghostdy[i] = Ghost.dy[count];
					}

				} else if (Ghost.ghostx[i] < Pacman.x
						&& Ghost.ghosty[i] > Pacman.y) // phai tren
				{
					count = 0;
					if ((Graphic.currentMap[pos] & 4) == 0
							&& Ghost.ghostdx[i] != -1) {
						Ghost.dx[count] = 1;
						Ghost.dy[count] = 0;
						count++;
					}
					if ((Graphic.currentMap[pos] & 2) == 0
							&& Ghost.ghostdy[i] != 1) {
						Ghost.dx[count] = 0;
						Ghost.dy[count] = -1;
						count++;
					}
					if ((Graphic.currentMap[pos] & 1) == 0
							&& Ghost.ghostdx[i] != 1) {
						Ghost.dx[count] = -1;
						Ghost.dy[count] = 0;
						count++;
					}
					if ((Graphic.currentMap[pos] & 8) == 0
							&& Ghost.ghostdy[i] != -1) {
						Ghost.dx[count] = 0;
						Ghost.dy[count] = 1;
						count++;
					}
					if (count < 3) {
						Ghost.ghostdx[i] = Ghost.dx[0];
						Ghost.ghostdy[i] = Ghost.dy[0];
					} else {
						count = (int) (Math.random() * count);
						if (count > 3)
							count = 3;

						Ghost.ghostdx[i] = Ghost.dx[count];
						Ghost.ghostdy[i] = Ghost.dy[count];
					}

				} else if (Ghost.ghostx[i] > Pacman.x
						&& Ghost.ghosty[i] < Pacman.y) // trai duoi
				{
					count = 0;
					if ((Graphic.currentMap[pos] & 1) == 0
							&& Ghost.ghostdx[i] != 1) {
						Ghost.dx[count] = -1;
						Ghost.dy[count] = 0;
						count++;
					}
					if ((Graphic.currentMap[pos] & 8) == 0
							&& Ghost.ghostdy[i] != -1) {
						Ghost.dx[count] = 0;
						Ghost.dy[count] = 1;
						count++;
					}
					if ((Graphic.currentMap[pos] & 4) == 0
							&& Ghost.ghostdx[i] != -1) {
						Ghost.dx[count] = 1;
						Ghost.dy[count] = 0;
						count++;
					}
					if ((Graphic.currentMap[pos] & 2) == 0
							&& Ghost.ghostdy[i] != 1) {
						Ghost.dx[count] = 0;
						Ghost.dy[count] = -1;
						count++;
					}
					if (count < 3) {
						Ghost.ghostdx[i] = Ghost.dx[0];
						Ghost.ghostdy[i] = Ghost.dy[0];
					} else {
						count = (int) (Math.random() * count);
						if (count > 3)
							count = 3;

						Ghost.ghostdx[i] = Ghost.dx[count];
						Ghost.ghostdy[i] = Ghost.dy[count];
					}

				} else if (Ghost.ghostx[i] > Pacman.x
						&& Ghost.ghosty[i] > Pacman.y) // trai tren
				{
					count = 0;
					if ((Graphic.currentMap[pos] & 1) == 0
							&& Ghost.ghostdx[i] != 1) {
						Ghost.dx[count] = -1;
						Ghost.dy[count] = 0;
						count++;
					}
					if ((Graphic.currentMap[pos] & 2) == 0
							&& Ghost.ghostdy[i] != 1) {
						Ghost.dx[count] = 0;
						Ghost.dy[count] = -1;
						count++;
					}
					if ((Graphic.currentMap[pos] & 4) == 0
							&& Ghost.ghostdx[i] != -1) {
						Ghost.dx[count] = 1;
						Ghost.dy[count] = 0;
						count++;
					}
					if ((Graphic.currentMap[pos] & 8) == 0
							&& Ghost.ghostdy[i] != -1) {
						Ghost.dx[count] = 0;
						Ghost.dy[count] = 1;
						count++;
					}

					if (count < 3) {
						Ghost.ghostdx[i] = Ghost.dx[0];
						Ghost.ghostdy[i] = Ghost.dy[0];
					} else {
						count = (int) (Math.random() * count);
						if (count > 3)
							count = 3;

						Ghost.ghostdx[i] = Ghost.dx[count];
						Ghost.ghostdy[i] = Ghost.dy[count];
					}

				} else

				if (Ghost.ghostx[i] < Pacman.x && Ghost.ghosty[i] == Pacman.y)// phai
				{
					// Di sang phai
					if ((Graphic.currentMap[pos] & 4) == 0
							&& Ghost.ghostdx[i] != -1) {

						Ghost.ghostdx[i] = 1;
						Ghost.ghostdy[i] = 0;
					} else
					// Di len tren
					if ((Graphic.currentMap[pos] & 2) == 0
							&& Ghost.ghostdy[i] != 1) {

						Ghost.ghostdx[i] = 0;
						Ghost.ghostdy[i] = -1;
					} else
					// Di xuong duoi
					if ((Graphic.currentMap[pos] & 8) == 0
							&& Ghost.ghostdy[i] != -1) {

						Ghost.ghostdx[i] = 0;
						Ghost.ghostdy[i] = 1;
					} else
					// Di sang trai
					if ((Graphic.currentMap[pos] & 1) == 0
							&& Ghost.ghostdx[i] != 1) {
						Ghost.ghostdx[i] = -1;
						Ghost.ghostdy[i] = 0;
					}

				} else if (Ghost.ghostx[i] > Pacman.x
						&& Ghost.ghosty[i] == Pacman.y)// trai
				{
					if ((Graphic.currentMap[pos] & 1) == 0
							&& Ghost.ghostdx[i] != 1) {

						Ghost.ghostdx[i] = -1;
						Ghost.ghostdy[i] = 0;
					} else
					// Di len tren
					if ((Graphic.currentMap[pos] & 2) == 0
							&& Ghost.ghostdy[i] != 1) {

						Ghost.ghostdx[i] = 0;
						Ghost.ghostdy[i] = -1;
					} else
					// Di xuong duoi
					if ((Graphic.currentMap[pos] & 8) == 0
							&& Ghost.ghostdy[i] != -1) {

						Ghost.ghostdx[i] = 0;
						Ghost.ghostdy[i] = 1;
					} else

					if ((Graphic.currentMap[pos] & 4) == 0
							&& Ghost.ghostdx[i] != -1) {
						Ghost.ghostdx[i] = 1;
						Ghost.ghostdy[i] = 0;
					}
				} else if (Ghost.ghostx[i] == Pacman.x
						&& Ghost.ghosty[i] > Pacman.y)// tren
				{

					// Di len tren
					if ((Graphic.currentMap[pos] & 2) == 0
							&& Ghost.ghostdy[i] != 1) {

						Ghost.ghostdx[i] = 0;
						Ghost.ghostdy[i] = -1;
					} else if ((Graphic.currentMap[pos] & 4) == 0
							&& Ghost.ghostdx[i] != -1) {

						Ghost.ghostdx[i] = 1;
						Ghost.ghostdy[i] = 0;
					} else
					// Di sang trai
					if ((Graphic.currentMap[pos] & 1) == 0
							&& Ghost.ghostdx[i] != 1) {
						Ghost.ghostdx[i] = -1;
						Ghost.ghostdy[i] = 0;
					} else
					// Di xuong duoi
					if ((Graphic.currentMap[pos] & 8) == 0
							&& Ghost.ghostdy[i] != -1) {

						Ghost.ghostdx[i] = 0;
						Ghost.ghostdy[i] = 1;
					}

				} else if (Ghost.ghostx[i] == Pacman.x
						&& Ghost.ghosty[i] < Pacman.y)// duoi
				{

					if ((Graphic.currentMap[pos] & 8) == 0
							&& Ghost.ghostdy[i] != -1) {

						Ghost.ghostdx[i] = 0;
						Ghost.ghostdy[i] = 1;
					} else if ((Graphic.currentMap[pos] & 4) == 0
							&& Ghost.ghostdx[i] != -1) {
						Ghost.ghostdx[i] = 1;
						Ghost.ghostdy[i] = 0;
					} else

					if ((Graphic.currentMap[pos] & 1) == 0
							&& Ghost.ghostdx[i] != 1) {
						Ghost.ghostdx[i] = -1;
						Ghost.ghostdy[i] = 0;
					} else

					if ((Graphic.currentMap[pos] & 2) == 0
							&& Ghost.ghostdy[i] != 1) {
						Ghost.ghostdx[i] = 0;
						Ghost.ghostdy[i] = -1;
					}
				}
			}
			Ghost.ghostx[i] = Ghost.ghostx[i]
					+ (Ghost.ghostdx[i] * Ghost.speed[i]);
			Ghost.ghosty[i] = Ghost.ghosty[i]
					+ (Ghost.ghostdy[i] * Ghost.speed[i]);
			// Va cham voi PacMan
			if (Pacman.x > (Ghost.ghostx[i] - 12)
					&& Pacman.x < (Ghost.ghostx[i] + 12)
					&& Pacman.y > (Ghost.ghosty[i] - 12)
					&& Pacman.y < (Ghost.ghosty[i] + 12) && Pacman.ingame) {
				Pacman.dying = true;

			}
		}

	}

}
