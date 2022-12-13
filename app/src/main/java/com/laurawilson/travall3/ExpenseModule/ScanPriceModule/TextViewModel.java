package com.laurawilson.travall3.ExpenseModule.ScanPriceModule;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class TextViewModel extends ViewModel
{

    private ArrayList<String> textList;
    private int returnAction;

    public void setTextList(ArrayList<String> textList)
    {
        this.textList = new ArrayList<>(textList);
    }

    public ArrayList<String> getTextList()
    {
        return this.textList;
    }

    public void setReturnAction(int returnAction)
    {
        this.returnAction = returnAction;
    }
    public int getReturnAction()
    {
        return returnAction;
    }

}
