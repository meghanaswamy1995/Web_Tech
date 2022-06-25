package com.example.artistsearch.ui.main;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.artistsearch.FragmentArtwork;
import com.example.artistsearch.FragmentDetails;
import com.example.artistsearch.R;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;
import android.text.SpannableStringBuilder;
import android.text.style.DynamicDrawableSpan;
import android.text.Spanned;
import com.google.android.material.tabs.TabLayout;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[] {R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;
    private int[] tabIcons = {
            R.drawable.icons8_info_50,
            R.drawable.icons8_art_64};

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
//        return PlaceholderFragment.newInstance(position + 1);
        Fragment fragment =null;
        switch (position){
            case 0:
                fragment = new FragmentDetails();
                break;
            case 1:
                fragment = new FragmentArtwork();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

       // tabLayout = (TabLayout) findViewById(R.id.tabs);


        Drawable myDrawable=null;
        String title="";
        switch (position) {
            case 0:

                myDrawable = mContext.getResources().getDrawable(R.drawable.alert_circle_outline);
//                myDrawable = mContext.getResources().getDrawable(R.drawable.icons8_info_50);
                title = "\nDETAILS";
                break;
            case 1:
                myDrawable = mContext.getResources().getDrawable(R.drawable.palette_outline);
                title = "\nARTWORK";
                break;
        }
        SpannableStringBuilder sb = new SpannableStringBuilder("   " + title); // space added before text for convenience
        try {
            myDrawable.setBounds(0, 0, 70,70);
            ImageSpan span = new ImageSpan(myDrawable, DynamicDrawableSpan.ALIGN_BASELINE);
            sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sb;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}