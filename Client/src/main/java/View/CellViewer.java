package View;

import Model.Cell;
import javafx.fxml.FXML;

public class CellViewer {
    private Cell cell;

    @FXML
    void clicked() {
        MainView.getMV().getGameBoardViewer().shootCell(cell);
    }

    public void setCell(Cell cell){
        this.cell = cell;
    }
}
