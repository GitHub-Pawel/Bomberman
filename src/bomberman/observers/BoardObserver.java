package bomberman.observers;

//import bomberman.component.Board;
import bomberman.component.BoardForward;

public interface BoardObserver {
    void boardUpdate(BoardForward boardForward);   //
}
