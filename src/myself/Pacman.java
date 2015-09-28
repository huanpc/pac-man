package myself;

import myself.Graphic;

import myself.Map;

public class Pacman {
	public static int life = 3; // so mang cua pacman
	final static int cycle = 4; // chu ki mo mieng
	final static int statecount = 4; // co 4 trang thai cua pacman
	 static int speed = 5;
	static int x, y, dx, dy; // toa do va buoc di chuyen
	static int pressX, pressY; // buoc di chuyen theo button va buoc di chuyen
								// pacman
	static int viewdx, viewdy;
	static int state = 0; // xac dinh trang thai pacman
	static int statedir = 1; // do tang giam trang thai
	static int delay = cycle; // bien dem nguoc chu ki
	static int pacleft; // mang con lai cua pacman trong tung level
	static int score = 0;
	static boolean dying = false;
	static boolean ingame = false;
	static boolean gameover = false;
	static boolean wingame = false;
	// DINH DANH
	final static int LEFTBLOCK = 1;
	final static int RIGHTBLOCK = 4;
	final static int UPBLOCK = 2;
	final static int DOWN = 8;
	final static int DOT = 16;
	final static int BIGDOT = 32;
	final static int LEFTMOVE = -1;
	final static int RIGHTMOVE = 1;
	final static int UPMOVE = -1;
	final static int DOWNMOVE = 1;
	final static int NOMOVE = 0;

	/*
	 * Thay doi trang thai cua Pacman Co 4 trang thai: ban dau, ngam mieng, mo
	 * mieng, nhai. Thoi gian giua cac lan thay doi trang thai la cycle
	 */
	public static void changeState() {
		delay--;
		if (delay <= 0) {
			delay = cycle;
			state += statedir;
			if (state == (statecount - 1) || state == 0)
				statedir = -statedir;
		}
	}

	// Di chuyen pacman dua theo nut huong duoc nhan
	// Neu di chuyen den noi co diem, tang diem dat duoc va loai khoi ban do
	// diem do
	public static void move() {
		int position;
		int ch;
		if (pressX == -dx && pressY == -dy) {
			dx = pressX;
			dy = pressY;
			viewdx = dx;
			viewdy = dy;
		}
		if (x % Map.blocksize == 0 && y % Map.blocksize == 0) {
			position = x / Map.blocksize + Map.cols * (int) (y / Map.blocksize);
			ch = Graphic.currentMap[position];
			// Di chuyen den noi co diem ?
			if ((ch & DOT) != 0) {
				Graphic.currentMap[position] = (short) (ch & 15);
				score++;
				// An diem
			}
			if ((ch & BIGDOT) != 0) {
				Graphic.currentMap[position] = (short) (ch & (BIGDOT-1));
				score += 50;
				// An diem
			}
			// Truong hop pacman gap tuong va nguoi choi nhan doi huong
			if (pressX != 0 || pressY != 0)
				if (!((pressX == LEFTMOVE && pressY == NOMOVE && (ch & LEFTBLOCK) != 0)
						|| (pressX == RIGHTMOVE && pressY == NOMOVE && (ch & RIGHTBLOCK) != 0)
						|| (pressX == NOMOVE && pressY == UPMOVE && (ch & UPBLOCK) != 0) || (pressX == NOMOVE
						&& pressY == DOWNMOVE && (ch & DOWN) != 0))) {
					dx = pressX;
					dy = pressY;
					viewdx = dx;
					viewdy = dy;
				}
			// Truong hop pacman gap tuong va nguoi choi k doi huong
			// Pacman se dung tai cho
			if ((dx == LEFTMOVE && dy == NOMOVE && (ch & LEFTBLOCK) != 0)
					|| (dx == RIGHTMOVE && dy == NOMOVE && (ch & RIGHTBLOCK) != 0)
					|| (dx == NOMOVE && dy == UPMOVE && (ch & UPBLOCK) != 0)
					|| (dx == NOMOVE && dy == DOWNMOVE && (ch & DOWN) != 0)) {
				dx = 0;
				dy = 0;
			}
		}
		x += speed * dx;
		y += speed * dy;
	}

	public static void isDeath() {
		pacleft--;
		if (pacleft == 0) {
			ingame = false;
			gameover = true;
		}
		Graphic.continueLevel();
	}

}
