package com.example.my2048.modules.mainactivity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.my2048.helpers.CustomGestureDetector;
import com.example.my2048.helpers.CustomIntLiveData;
import com.example.my2048.helpers.MathUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainActivityViewModel extends ViewModel implements CustomGestureDetector.ActionListener {
    public CustomIntLiveData[][] cellItemsLiveData = new CustomIntLiveData[4][4];
    public MutableLiveData<Long> sumOfNum = new MutableLiveData<>();

    public MainActivityViewModel() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                cellItemsLiveData[i][j] = new CustomIntLiveData();
            }
        }
        sumOfNum.setValue(0L);
        startGame();
    }

    public void restartGame() {
        for (CustomIntLiveData[] cellItemLiveData : cellItemsLiveData) {
            for (CustomIntLiveData cellItem : cellItemLiveData) {
                cellItem.setValue(0);
            }
        }
        startGame();
    }

    @Override
    public void onLeftSwipe() {
        for (int i = 0; i < 4; i++) {
            CustomIntLiveData[] lineCellItemsLiveData = this.cellItemsLiveData[i];
            onSwipeALine(lineCellItemsLiveData);
        }
        addNewNum(SwipeType.LEFT);
    }

    @Override
    public void onRightSwipe() {
        for (int i = 0; i < 4; i++) {
            CustomIntLiveData[] lineCellItemsLiveData = {this.cellItemsLiveData[i][3], this.cellItemsLiveData[i][2], this.cellItemsLiveData[i][1], this.cellItemsLiveData[i][0]};
            onSwipeALine(lineCellItemsLiveData);
        }
        addNewNum(SwipeType.RIGHT);
    }

    @Override
    public void onTopSwipe() {
        for (int i = 0; i < 4; i++) {
            CustomIntLiveData[] lineCellItemsLiveData = {this.cellItemsLiveData[0][i], this.cellItemsLiveData[1][i], this.cellItemsLiveData[2][i], this.cellItemsLiveData[3][i]};
            onSwipeALine(lineCellItemsLiveData);
        }
        addNewNum(SwipeType.TOP);
    }

    @Override
    public void onDownSwipe() {
        for (int i = 0; i < 4; i++) {
            CustomIntLiveData[] lineCellItemsLiveData = {this.cellItemsLiveData[3][i], this.cellItemsLiveData[2][i], this.cellItemsLiveData[1][i], this.cellItemsLiveData[0][i]};
            onSwipeALine(lineCellItemsLiveData);
        }
        addNewNum(SwipeType.DOWN);
    }

    private void startGame() {
        addNewNum(SwipeType.TOP);
        addNewNum(SwipeType.LEFT);
    }

    private void addNewNum(SwipeType swipeType) {
        List<CustomIntLiveData> listNewNumberLiveData = new ArrayList<>();
        switch (swipeType) {
            case RIGHT:
                for (int i = 0; i < 4; i++) {
                    CustomIntLiveData customIntLiveData = cellItemsLiveData[i][0];
                    if (customIntLiveData.getValue() == 0)
                        listNewNumberLiveData.add(customIntLiveData);
                }
                break;
            case LEFT:
                for (int i = 0; i < 4; i++) {
                    CustomIntLiveData customIntLiveData = cellItemsLiveData[i][3];
                    if (customIntLiveData.getValue() == 0)
                        listNewNumberLiveData.add(customIntLiveData);
                }
                break;
            case TOP:
                for (int i = 0; i < 4; i++) {
                    CustomIntLiveData customIntLiveData = cellItemsLiveData[3][i];
                    if (customIntLiveData.getValue() == 0)
                        listNewNumberLiveData.add(customIntLiveData);
                }
                break;
            case DOWN:
                for (int i = 0; i < 4; i++) {
                    CustomIntLiveData customIntLiveData = cellItemsLiveData[0][i];
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
                newNumberLiveData.setValue(4);
            else
                newNumberLiveData.setValue(2);
        }
    }

    private void onSwipeALine(CustomIntLiveData[] lineCellItemsLiveData) {
        for (int j = 0; j < 3; j++) {
            if (lineCellItemsLiveData[j].getValue() == 0) continue;
            for (int k = j + 1; k < 4; k++) {
                if (lineCellItemsLiveData[k].getValue() == 0) continue;
                Boolean b = sumViews(lineCellItemsLiveData[j], lineCellItemsLiveData[k]);
                if (b != null && !b) k = 4;
            }
        }
        for (int v = 0; v < 3; v++) {
            int lastEmpty = lineCellItemsLiveData[0].getValue() == 0 ? 0 : -1;
            for (int j = 1; j < 4; j++) {
                lastEmpty = checkChange(lineCellItemsLiveData, lastEmpty, j);
            }
        }
    }

    private Boolean sumViews(CustomIntLiveData first, CustomIntLiveData second) {
        if (first.getValue() == 0 || second.getValue() == 0) return null;
        if (first.getValue().equals(second.getValue())) {
            first.setValue(first.getValue() * 2);
            second.setValue(0);
            sumOfNum.setValue(Optional.ofNullable(sumOfNum.getValue()).orElse(0L) + first.getValue());
            return true;
        }
        return false;
    }

    private int checkChange(CustomIntLiveData[] cellItemsLiveData, int lastEmpty, int j) {
        if (cellItemsLiveData[j].getValue() == 0) {
            if (lastEmpty == -1) lastEmpty = j;
            return lastEmpty;
        }
        if (lastEmpty != -1) {
            changeViewsText(cellItemsLiveData[lastEmpty], cellItemsLiveData[j]);
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
