package com.example.my2048.modules.mainactivity;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.my2048.helpers.CustomGestureDetector;
import com.example.my2048.helpers.CustomIntLiveData;
import com.example.my2048.helpers.MathUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainActivityViewModel extends ViewModel implements CustomGestureDetector.ActionListener {
    private static final int MAX_CELL_IN_ROW = 4;
    public CustomIntLiveData[][] cellItemsLD = new CustomIntLiveData[MAX_CELL_IN_ROW][MAX_CELL_IN_ROW];
    public MutableLiveData<Long> sumOfNumLD = new MutableLiveData<>();
    public MutableLiveData<Integer> gameFinishLD = new MutableLiveData<>();

    public MainActivityViewModel() {
        for (int i = 0; i < MAX_CELL_IN_ROW; i++) {
            for (int j = 0; j < MAX_CELL_IN_ROW; j++) {
                cellItemsLD[i][j] = new CustomIntLiveData();
            }
        }
        sumOfNumLD.setValue(0L);
        startGame();
    }

    public void restartGame() {
        for (CustomIntLiveData[] cellItemLiveData : cellItemsLD) {
            for (CustomIntLiveData cellItem : cellItemLiveData) {
                cellItem.setValue(0);
            }
        }
        startGame();
    }

    @Override
    public void onLeftSwipe() {
        for (int i = 0; i < MAX_CELL_IN_ROW; i++) {
            CustomIntLiveData[] linecellItemsLD = this.cellItemsLD[i];
            onSwipeALine(linecellItemsLD);
        }
        onSwipe(SwipeType.LEFT);
    }

    @Override
    public void onRightSwipe() {
        for (int i = 0; i < MAX_CELL_IN_ROW; i++) {
            CustomIntLiveData[] linecellItemsLD = {this.cellItemsLD[i][3], this.cellItemsLD[i][2], this.cellItemsLD[i][1], this.cellItemsLD[i][0]};
            onSwipeALine(linecellItemsLD);
        }
        onSwipe(SwipeType.RIGHT);
    }

    @Override
    public void onTopSwipe() {
        for (int i = 0; i < MAX_CELL_IN_ROW; i++) {
            CustomIntLiveData[] linecellItemsLD = {this.cellItemsLD[0][i], this.cellItemsLD[1][i], this.cellItemsLD[2][i], this.cellItemsLD[3][i]};
            onSwipeALine(linecellItemsLD);
        }
        onSwipe(SwipeType.TOP);
    }

    @Override
    public void onDownSwipe() {
        for (int i = 0; i < MAX_CELL_IN_ROW; i++) {
            CustomIntLiveData[] linecellItemsLD = {this.cellItemsLD[3][i], this.cellItemsLD[2][i], this.cellItemsLD[1][i], this.cellItemsLD[0][i]};
            onSwipeALine(linecellItemsLD);
        }
        onSwipe(SwipeType.DOWN);
    }

    private void onSwipe(SwipeType swipeType) {
        boolean isGameFinished = isGameFinished();
        if (isGameFinished) {
            onGameFinished();
        } else {
            addNewNum(swipeType);
        }
    }

    private void onGameFinished() {
        gameFinishLD.setValue(View.VISIBLE);
    }

    private boolean isGameFinished() {
        for (int i = 0; i < MAX_CELL_IN_ROW; i++) {
            for (int j = 0; j < MAX_CELL_IN_ROW; j++) {
                int cellValue = cellItemsLD[i][j].getValue();
                if (cellValue == 0) {
                    return false;
                }
                for (int x = i - 1; x < i + 2; x++) {
                    if (x < 0 || x > MAX_CELL_IN_ROW || x == i) {
                        break;
                    }
                    int verticalCellValue = cellItemsLD[x][j].getValue();
                    if (cellValue == verticalCellValue) {
                        return false;
                    }
                }
                for (int y = j - 1; y < j + 2; y++) {
                    if (y < 0 || y > MAX_CELL_IN_ROW || y == j) {
                        break;
                    }
                    int horizontalCellValue = cellItemsLD[i][y].getValue();
                    if (cellValue == horizontalCellValue) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void startGame() {
        gameFinishLD.setValue(View.INVISIBLE);
        addNewNum(SwipeType.TOP);
        addNewNum(SwipeType.LEFT);
    }

    private void addNewNum(SwipeType swipeType) {
        List<CustomIntLiveData> listNewNumberLiveData = new ArrayList<>();
        switch (swipeType) {
            case RIGHT:
                for (int i = 0; i < MAX_CELL_IN_ROW; i++) {
                    CustomIntLiveData customIntLiveData = cellItemsLD[i][0];
                    if (customIntLiveData.getValue() == 0)
                        listNewNumberLiveData.add(customIntLiveData);
                }
                break;
            case LEFT:
                for (int i = 0; i < MAX_CELL_IN_ROW; i++) {
                    CustomIntLiveData customIntLiveData = cellItemsLD[i][3];
                    if (customIntLiveData.getValue() == 0)
                        listNewNumberLiveData.add(customIntLiveData);
                }
                break;
            case TOP:
                for (int i = 0; i < MAX_CELL_IN_ROW; i++) {
                    CustomIntLiveData customIntLiveData = cellItemsLD[3][i];
                    if (customIntLiveData.getValue() == 0)
                        listNewNumberLiveData.add(customIntLiveData);
                }
                break;
            case DOWN:
                for (int i = 0; i < MAX_CELL_IN_ROW; i++) {
                    CustomIntLiveData customIntLiveData = cellItemsLD[0][i];
                    if (customIntLiveData.getValue() == 0)
                        listNewNumberLiveData.add(customIntLiveData);
                }
                break;
        }
        if (listNewNumberLiveData.size() != 0) {
            CustomIntLiveData newNumberLiveData = listNewNumberLiveData.get(MathUtils.getInstance().getRoundInt(listNewNumberLiveData.size() - 1));
            int randPercent = 8;
            int rand = MathUtils.getInstance().getRoundInt(randPercent);
            if (rand % randPercent == 0)
                newNumberLiveData.setValue(MAX_CELL_IN_ROW);
            else
                newNumberLiveData.setValue(2);
        }
    }

    private void onSwipeALine(CustomIntLiveData[] linecellItemsLD) {
        for (int j = 0; j < 3; j++) {
            if (linecellItemsLD[j].getValue() == 0) continue;
            for (int k = j + 1; k < MAX_CELL_IN_ROW; k++) {
                if (linecellItemsLD[k].getValue() == 0) continue;
                Boolean b = sumViews(linecellItemsLD[j], linecellItemsLD[k]);
                if (b != null && !b) k = MAX_CELL_IN_ROW;
            }
        }
        for (int v = 0; v < 3; v++) {
            int lastEmpty = linecellItemsLD[0].getValue() == 0 ? 0 : -1;
            for (int j = 1; j < MAX_CELL_IN_ROW; j++) {
                lastEmpty = checkChange(linecellItemsLD, lastEmpty, j);
            }
        }
    }

    private Boolean sumViews(CustomIntLiveData first, CustomIntLiveData second) {
        if (first.getValue() == 0 || second.getValue() == 0) return null;
        if (first.getValue().equals(second.getValue())) {
            first.setValue(first.getValue() * 2);
            second.setValue(0);
            sumOfNumLD.setValue(Optional.ofNullable(sumOfNumLD.getValue()).orElse(0L) + first.getValue());
            return true;
        }
        return false;
    }

    private int checkChange(CustomIntLiveData[] cellItemsLD, int lastEmpty, int j) {
        if (cellItemsLD[j].getValue() == 0) {
            if (lastEmpty == -1) lastEmpty = j;
            return lastEmpty;
        }
        if (lastEmpty != -1) {
            changeViewsText(cellItemsLD[lastEmpty], cellItemsLD[j]);
            lastEmpty = j;
        }
        return lastEmpty;
    }

    private void changeViewsText(CustomIntLiveData first, CustomIntLiveData second) {
        int num = first.getValue();
        first.setValue(second.getValue());
        second.setValue(num);
    }

    enum SwipeType {
        RIGHT,
        LEFT,
        TOP,
        DOWN,
    }
}
