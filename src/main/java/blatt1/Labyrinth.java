package blatt1;

public class Labyrinth {

  private static boolean[][] maze;
  private static boolean[][] solution;
  private static int width, height;


  public static void main(String[] args) {
    width = 20;
    height = 20;
    maze = Maze.generateMaze(width, height);
    solution = new boolean[width][height];
    draw(new int[]{1, 0});
    walk(1, 0, 2);
  }

  /*
   * direction vector:
   *   | 0 |
   * ----------
   * 3 |   | 1
   * ----------
   *   | 2 |
   */
  public static void walk(int x, int y, int direction) {
    int[] pos = new int[]{x, y};
    int[] newPos;
    while (true) {
      draw(pos);
      if (pos[0] == width - 1 && pos[1] == height - 2) {
        break;
      }
      if (!hasWall(right(pos, direction))) {
        direction = turnRight(direction);
      }
      newPos = ahead(pos, direction);
      while (hasWall(newPos)) {
        direction = turnLeft(direction);
        newPos = ahead(pos, direction);
      }
      pos = newPos;
      if (pos[0] == 1 && pos[1] == 0) {
        System.err.println("ERROR: The exit is unreachable by the RHR!");
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        return;
      }
    }
    System.out.println("DONE The Right Hand Rule has terminated!");
  }

  private static void draw(int[] pos) {
    solution[pos[0]][pos[1]] = true;
    Maze.draw(pos[0], pos[1], maze, solution);
    try {
      Thread.sleep(20);
    } catch (InterruptedException e) {
    }
  }


  private static int turnRight(int direction) {
    return (direction + 1) % 4;
  }

  private static int turnLeft(int direction) {
    return (direction + 3) % 4;
  }

  private static boolean hasWall(int[] pos) {
    if (pos[0] < 0 || pos[0] >= width) {
      return true;
    }
    if (pos[1] < 0 || pos[1] >= width) {
      return true;
    }
    return maze[pos[0]][pos[1]];
  }

  private static int[] right(int[] pos, int direction) {
    switch (direction) {
      case 0:
        return new int[]{pos[0] + 1, pos[1]};
      case 1:
        return new int[]{pos[0], pos[1] + 1};
      case 2:
        return new int[]{pos[0] - 1, pos[1]};
      case 3:
        return new int[]{pos[0], pos[1] - 1};
      default:
        return new int[]{pos[0], pos[1]};
    }
  }

  private static int[] ahead(int[] pos, int direction) {
    switch (direction) {
      case 0:
        return new int[]{pos[0], pos[1] - 1};
      case 1:
        return new int[]{pos[0] + 1, pos[1]};
      case 2:
        return new int[]{pos[0], pos[1] + 1};
      case 3:
        return new int[]{pos[0] - 1, pos[1]};
      default:
        return new int[]{pos[0], pos[1]};
    }
  }

}
