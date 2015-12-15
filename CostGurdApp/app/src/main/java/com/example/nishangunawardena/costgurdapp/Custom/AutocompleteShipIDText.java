package com.example.nishangunawardena.costgurdapp.Custom;

import android.content.Context;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;

/**
 * Created by Nishan Gunawardena on 12/15/2015.
 */
public class AutocompleteShipIDText extends AppCompatAutoCompleteTextView {
    public AutocompleteShipIDText(Context context) {
        super(context);
    }


    public AutocompleteShipIDText(Context context, AttributeSet attrs)
    {
        super(context,attrs);

    }
    public AutocompleteShipIDText(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context,attrs,defStyleAttr);

    }

    @Override
    public Editable getText()
    {
        try
        {
            Editable text = super.getText();
            String strText = text.toString().trim();



                if (strText.length() > 5 && strText.substring(0, "IMULA".length()).equals("IMULA"))
                    return super.getText();
                else {
                    strText = "IMULA" + strText;

                    return new SpannableStringBuilder(strText);
                }

        }
        catch (Exception ex)
        {
            System.out.println("Exception in custom textbox "+ex.getMessage());
            return null;
        }


    }
}
