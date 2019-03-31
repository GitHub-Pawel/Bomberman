package bomberman.observers;

import bomberman.component.Board;

public interface BoardObserver {
    void boardUpdate(Board board);
}
