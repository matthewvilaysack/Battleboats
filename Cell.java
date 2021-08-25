public class Cell {
  private int row;
  private int col;
  private char status = '-';

  public char get_status() {
    return this.status;
  }

  public void set_status(char c) {
    this.status = c;
  }

  public Cell(int row, int col) {
    this.row = row;
    this.col = col;
  }

// '-' not guessed, no boat present
// 'B' not guessed, boat present
// 'H' guessed, boat present
// 'M' guessed, no boat present

}