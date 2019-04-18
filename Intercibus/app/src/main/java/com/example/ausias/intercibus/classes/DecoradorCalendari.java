package com.example.ausias.intercibus.classes;

import android.graphics.Color;
import android.os.Parcel;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.HashSet;

/**
 * Aquesta classe serveix per personalitzar el calendari
 * @author Antonio de Dios Durán
 * @version 1.0
 */

public class DecoradorCalendari implements DayViewDecorator {

    int color;
    HashSet<CalendarDay> dates;

    /**
     * Constructor per el decorador calendari
     * @param color Paràmetre que defineix el color del dia seleccionat al calendari
     * @param dates Paràmetre que defineix els dies del calendari
     */
    public DecoradorCalendari(int color, HashSet<CalendarDay> dates) {
        this.color = color;
        this.dates = dates;
    }

    /**
     * Mètode que comprova si el dia seleccionat  es troba a la llista dels dies a decorar
     * @param day Pàmetre que defineix el dia seleccionat al calendari
     * @return Un booleà depenent de si el dia es troba a la llista o no
     */
    @Override
    public boolean shouldDecorate(CalendarDay day) {

        return dates.contains(day);
    }

    /**
     * Mètode que defineix el color amb el qual s'ha de pintar el dia
     * @param view Paràmetre que defineix la vista
     */
    @Override
    public void decorate(DayViewFacade view) {

        if (color == 1) {
            Log.d("DECORAR", "magenta :D");
            view.addSpan(new DotSpan(25, Color.parseColor("#FFFFBB33")));
        }
        else if (color == 2) {
            Log.d("DECORAR", "oliva :D");
            view.addSpan(new DotSpan(25, Color.parseColor("olive")));
        }
        else {
            view.addSpan(new ForegroundColorSpan((Color.RED)));
        }
    }
}
