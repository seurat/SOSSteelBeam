package com.solutions.secondorder.SOSSteelBeamLite;

import android.util.Log;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Rani on 3/12/2016.
 */
public class Keys {
    String TAG = "Keys";
    private static Keys ourInstance = new Keys();

    public static Keys getInstance() {
        return ourInstance;
    }

    private Keys() {
    }

    //Note: data in this file pertains to UI asset management; do not use methods for data representation.

    //any numbers regarding measurements such as lookup range in inches
    double lookupRange = .125;

    String[] arrayDatabases = {"AISC14.1 (2013)","AISC13 (2005)","ASD9 (1989)","Historical (1873-1952)",
            "ASD5 (1962)", "LRFD1 (1986)", "ASD6 (1964)",
            "LRFD2 (1994)", "ASD7 (1970)", "LRFD3 (2001)",
            "ASD8 (1980)"};

    /**
     * takes labels from DatabaseActivity screen and returns file name of corresponding database.
     * @return
     */
    public String convertRecordNameToDBName(String label) {
        switch(label) {
            case "AISC14.1 (2013)":
                return "AISC14.1";
            case "AISC13 (2005)":
                return "AISC13";
            case "ASD9 (1989)":
                return "ASD9";
            case "Historical (1873-1952)":
                return "Historical";
            case "ASD5 (1962)":
                return "ASD5";
            case "LRFD1 (1986)":
                return "LRFD1";
            case "ASD6 (1964)":
                return "ASD6";
            case "LRFD2 (1994)":
                return "LRFD2";
            case "ASD7 (1970)":
                return "ASD7";
            case "LRFD3 (2001)":
                return "LRFD3";
            case "ASD8 (1980)":
                return "ASD8";
            default:
                return "";
        }
    }

    //Stringified versions of same
    String lookupRangeSQL = String.valueOf(lookupRange);

    //intent keys for different extra values in Android intents.
    String intent_shapes = "shapes";
    String intent_sections = "sections";
    //section_type is the 'real' set of families for sections, containing 7, not 5, types.
    String intent_dimensions = "dimensions";
    String intent_dimensions_sqr_flag = "dimensions hss_sqr flag";
    String intent_properties = "properties";
    String intent_databases = "database";
    //Shape Family Names
    String i_shape = "i";
    String tube_shape = "tube";
    String pipe_shape = "pipe";
    String c_shape = "c";
    String t_shape = "t";
    String l_shape = "l";
    String two_l_shape = "2l";

    //make sure everything follows same casing convention; whether conforming the model to the controller,
    //or the controller to the model.
    //hard coded section names for specific sections (most others we'll leave alone due to them being
    //intuitive/single letter.
    String pipe_section = "pipe";
    String two_l_section = "2l";
    String hss_rect_section = "hssr";
    //TODO in earlier version, both hss_rect_section and hss_sqr_section use same code hssr. Fix this later.
    String hss_sqr_section = "hss_sqr";
    //for the moment I am using the code "HSSC" while keeping the variable name hss_round.
    String hss_round_section = "hssc";


    //Shape pictures families
    List<String> pipe_sections = Arrays.asList(hss_round_section, pipe_section);
    List<String> i_sections = Arrays.asList("w", "m", "s", "hp");
    List<String> l_sections = Arrays.asList(two_l_section, "l");
    List<String> tube_sections = Arrays.asList(hss_rect_section, hss_sqr_section);
    List<String> c_sections = Arrays.asList("c", "mc");
    List<String> t_sections = Arrays.asList("wt", "mt", "st", two_l_section);

    //determines the section family of the given section for the purposes of selecting graphics
    // for their dimensions pages.
    //To make sure a pipe isn't confused with a tube, always ask if it's a pipe before asking if it's a tube.
    protected String shapeInterpreter(String section) {
        String sectionLowerCase = section.toLowerCase();
        if(two_l_shape.equalsIgnoreCase(sectionLowerCase))
            return two_l_shape;
        if(pipe_sections.contains(sectionLowerCase))
            return pipe_shape;
        if(i_sections.contains(sectionLowerCase))
            return i_shape;
        if(tube_sections.contains(sectionLowerCase))
            return tube_shape;
        if(c_sections.contains(sectionLowerCase))
            return c_shape;
        if(t_sections.contains(sectionLowerCase))
            return t_shape;
        if(l_sections.contains(sectionLowerCase))
            return l_shape;
        else
            return "";
    }

    protected void setDetailFromShape(ImageView detail, String section) {
        String shape = shapeInterpreter(section);
        switch (shape) {
            case "i":
                //i
                //sections_table = (TableLayout) findViewById(R.id.sections_table);
                detail.setImageResource(R.drawable.image_i_detail);
                break;
            case "l":
                //l
                detail.setImageResource(R.drawable.image_l_detail);
                break;
            case "tube":
                //tube
                if(section.equalsIgnoreCase(hss_sqr_section))
                    detail.setImageResource(R.drawable.image_tube_square_detail);
                if(section.equalsIgnoreCase(hss_rect_section))
                    detail.setImageResource(R.drawable.image_tube_detail);
                break;
            case "c":
                //c
                detail.setImageResource(R.drawable.image_c_detail);
                break;
            case "t":
                //t
                detail.setImageResource(R.drawable.image_t_detail);
                break;
            case "pipe":
                //pipe subsection in tube
                detail.setImageResource(R.drawable.image_pipe_detail);
                break;
            case "2l":
                //two l subsection in t
                detail.setImageResource(R.drawable.image_two_l_detail);
                break;
            default:
                Log.e(TAG, "Could not recognize shape" + shape);
                break;
        }
    }

    //constructs queries for record containing var value of val

    //returns section specifier component of SQL query
    public String specifySectionString(String section) {
        String ifHSSRCompareBHt = "";
        Log.e(TAG, "Section is "+section);
        if(hss_sqr_section.equalsIgnoreCase(section)) {
            Log.e(TAG, "Square found");
            ifHSSRCompareBHt = " AND \"ht\"=\"b\" AND NOT ht= \"–\"";
            return " WHERE Shape LIKE '" + hss_rect_section + "' " + ifHSSRCompareBHt;
        }
        if(hss_rect_section.equalsIgnoreCase(section)) {
            ifHSSRCompareBHt = " AND NOT \"ht\"=\"b\" AND NOT ht= \"–\"";
            return " WHERE Shape LIKE '" + section + "' " + ifHSSRCompareBHt;
        }
        else
            //left ifHSSRCompareBHT var in here just for consistency's sake.
            return " WHERE Shape LIKE '" + section + "' " + ifHSSRCompareBHt;
    }

    public String specifyDimRangeString(String dim, String param) {
        return " AND " + dim+" BETWEEN "+param+"-"+lookupRangeSQL+" AND "+param+"+"+lookupRangeSQL;
    }

    public String queryByBothString(String var1, String val1, String var2, String val2) {
        return specifyDimRangeString(var1, val1) + " " + specifyDimRangeString(var2, val2);
    }

}
