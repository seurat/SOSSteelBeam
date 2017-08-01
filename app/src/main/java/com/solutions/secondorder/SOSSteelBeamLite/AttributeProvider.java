package com.solutions.secondorder.SOSSteelBeamLite;

/**
 * Created by Rani on 3/13/2016.
 */
public interface AttributeProvider {
    //returns section corresponding to selection
    public String getAttributeFromActivity();
    //decides what information is passed to dimensions activity
    //screenDispatch has multiple different impacts then, I guess, and it's contract is about how activity reacts to button presses.
    public void screenDispatch(int button);
    //flag; true if section is hss_sqr.
    public boolean isHSSSqr();
    public void goBack();
    public String getDatabaseName();
    public String getShape();
}
